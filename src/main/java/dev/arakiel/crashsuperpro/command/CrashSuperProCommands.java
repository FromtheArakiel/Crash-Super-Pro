/*
 * Crash Super Pro - monster overhaul, rewrite of the mod "Super-Creeper".
 * Copyright (C) 2026 FromtheArakiel
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * ---------------------------------------------------------------------------
 * Derived from the project "Super-Creeper" by First-sight (MIT licence).
 * SPDX-FileCopyrightText: First-sight
 * SPDX-License-Identifier: MIT
 * The original MIT notice is reproduced in full in the NOTICE file next to this repository.
 */

package dev.arakiel.crashsuperpro.command;

import java.util.List;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import dev.arakiel.crashsuperpro.tags.RiderTags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;

/**
 * /crashsuperpro summon &lt;variant&gt; - spawns any of the special monsters this mod adds, with all of
 * its tags, riders and equipment already in place.
 */
public final class CrashSuperProCommands {
    private static final List<String> VARIANTS = List.of(
            "phantom_tnt", "phantom_fireball", "phantom_potion",
            "phantom_wither_rider", "phantom_skeleton_rider", "phantom_pillager_rider",
            "phantom_evoker_rider", "phantom_explosive_rider", "phantom_lava_rider",
            "creeper_split", "skeleton_explosive", "skeleton_lava", "vindicator_chicken");

    private CrashSuperProCommands() {
    }

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("crashsuperpro")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("summon")
                        .then(Commands.argument("variant", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(VARIANTS, builder))
                                .executes(context -> summon(context,
                                        StringArgumentType.getString(context, "variant"))))));
    }

    private static int summon(CommandContext<CommandSourceStack> context, String variant) {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        Vec3 pos = source.getPosition();

        try {
            int spawned = switch (variant) {
                case "phantom_tnt" -> spawnPhantom(level, pos, CrashSuperProConfig.phantomTntTag(), null, false);
                case "phantom_fireball" -> spawnPhantom(level, pos, CrashSuperProConfig.phantomFireballTag(), null, false);
                case "phantom_potion" -> spawnPhantom(level, pos, CrashSuperProConfig.potionPhantomTag(), null, false);
                case "phantom_wither_rider" -> spawnPhantom(level, pos, null, EntityType.WITHER_SKELETON, false);
                case "phantom_skeleton_rider" -> spawnPhantom(level, pos, null, EntityType.SKELETON, false);
                case "phantom_pillager_rider" -> spawnPhantom(level, pos, null, EntityType.PILLAGER, false);
                case "phantom_evoker_rider" -> spawnPhantom(level, pos, null, EntityType.EVOKER, false);
                case "phantom_explosive_rider" -> spawnPhantom(level, pos, null, EntityType.SKELETON, true);
                case "phantom_lava_rider" -> spawnPhantom(level, pos, null, EntityType.SKELETON, true);
                case "creeper_split" -> spawnSplitCreeper(level, pos);
                case "skeleton_explosive" -> spawnArrowSkeleton(level, pos, CrashSuperProConfig.explosiveSkeletonTag());
                case "skeleton_lava" -> spawnArrowSkeleton(level, pos, CrashSuperProConfig.lavaSkeletonTag());
                case "vindicator_chicken" -> spawnChickenRider(level, pos);
                default -> 0;
            };

            if (spawned == 0) {
                source.sendFailure(Component.translatable("commands.crashsuperpro.unknown", variant));
            } else {
                source.sendSuccess(() -> Component.translatable("commands.crashsuperpro.summoned", variant), true);
            }
            return spawned;
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to summon " + variant, exception);
            return 0;
        }
    }

    private static int spawnPhantom(ServerLevel level, Vec3 pos, String payloadTag,
            EntityType<? extends Mob> riderType, boolean specialRider) {
        Phantom phantom = EntityType.PHANTOM.create(level);
        if (phantom == null) {
            return 0;
        }

        phantom.moveTo(pos.x, pos.y + 2.0D, pos.z, level.random.nextFloat() * 360.0F, 0.0F);
        if (payloadTag != null) {
            phantom.addTag(payloadTag);
        }
        level.addFreshEntity(phantom);

        if (riderType != null) {
            Mob rider = riderType.create(level);
            if (rider != null) {
                rider.moveTo(phantom.getX(), phantom.getY(), phantom.getZ(), phantom.getYRot(), 0.0F);
                rider.finalizeSpawn(level, level.getCurrentDifficultyAt(phantom.blockPosition()),
                        MobSpawnType.JOCKEY, null, null);

                if (specialRider && rider instanceof Skeleton skeleton) {
                    // The two special riders keep their own tag instead of the rider tag.
                    skeleton.addTag(level.random.nextBoolean() ? CrashSuperProConfig.explosiveSkeletonTag()
                            : CrashSuperProConfig.lavaSkeletonTag());
                } else {
                    RiderTags.markPhantomRider(rider);
                }

                if (rider instanceof WitherSkeleton skeletonRider) {
                    skeletonRider.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                    skeletonRider.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
                }
                rider.setPersistenceRequired();
                level.addFreshEntity(rider);
                rider.startRiding(phantom, true);
            }
        }
        return 1;
    }

    private static int spawnSplitCreeper(ServerLevel level, Vec3 pos) {
        Creeper creeper = EntityType.CREEPER.create(level);
        if (creeper == null) {
            return 0;
        }
        creeper.moveTo(pos.x, pos.y, pos.z, level.random.nextFloat() * 360.0F, 0.0F);
        creeper.addTag(CrashSuperProConfig.creeperSplitTag());
        level.addFreshEntity(creeper);
        return 1;
    }

    private static int spawnArrowSkeleton(ServerLevel level, Vec3 pos, String tag) {
        Skeleton skeleton = EntityType.SKELETON.create(level);
        if (skeleton == null) {
            return 0;
        }
        skeleton.moveTo(pos.x, pos.y, pos.z, level.random.nextFloat() * 360.0F, 0.0F);
        skeleton.addTag(tag);
        skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        skeleton.setPersistenceRequired();
        level.addFreshEntity(skeleton);
        return 1;
    }

    private static int spawnChickenRider(ServerLevel level, Vec3 pos) {
        Vindicator vindicator = EntityType.VINDICATOR.create(level);
        Chicken chicken = EntityType.CHICKEN.create(level);
        if (vindicator == null || chicken == null) {
            return 0;
        }

        chicken.moveTo(pos.x, pos.y, pos.z, level.random.nextFloat() * 360.0F, 0.0F);
        chicken.setPersistenceRequired();
        level.addFreshEntity(chicken);

        vindicator.moveTo(pos.x, pos.y, pos.z, chicken.getYRot(), 0.0F);
        vindicator.finalizeSpawn(level, level.getCurrentDifficultyAt(chicken.blockPosition()),
                MobSpawnType.JOCKEY, null, null);
        vindicator.setPersistenceRequired();
        level.addFreshEntity(vindicator);
        vindicator.startRiding(chicken, true);
        return 1;
    }
}
