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

package dev.arakiel.crashsuperpro.event;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.util.BombTags;
import dev.arakiel.crashsuperpro.util.RiderTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: phantom riders and tags, and vindicators that spawn on a chicken.
 */
public final class MobSpawnHandler {
    private MobSpawnHandler() {
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        if (mob.level().isClientSide()) {
            return;
        }

        if (mob instanceof Phantom phantom) {
            if (CrashSuperProConfig.phantomNaturalOnly() && event.getSpawnType() != MobSpawnType.NATURAL) {
                return;
            }
            handlePhantom(event, phantom);
        } else if (mob instanceof Creeper creeper) {
            handleCreeper(event, creeper);
        } else if (mob instanceof Vindicator vindicator) {
            handleVindicator(event, vindicator);
        }
    }

    /**
     * Rolls the payload tag first and then one of the mutually exclusive spawn outcomes: a wither
     * skeleton with a bow, a plain skeleton, a pillager, an evoker, or nothing at all.
     */
    private static void handlePhantom(MobSpawnEvent.FinalizeSpawn event, Phantom phantom) {
        BombTags.roll(phantom, CrashSuperProConfig.phantomTntTagChance(),
                CrashSuperProConfig.phantomFireballTagChance());

        double roll = phantom.level().random.nextDouble();
        EntityType<? extends Mob> riderType = pickRiderType(roll);

        if (riderType == null) {
            return;
        }

        Mob rider = riderType.create(phantom.level());
        if (rider == null) {
            return;
        }

        rider.moveTo(phantom.getX(), phantom.getY(), phantom.getZ(), phantom.getYRot(), 0.0F);
        rider.finalizeSpawn(event.getLevel(), event.getDifficulty(), MobSpawnType.JOCKEY, null, null);

        if (rider instanceof AbstractSkeleton skeletonRider) {
            if (rider instanceof WitherSkeleton) {
                skeletonRider.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                skeletonRider.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
            }
            // Makes the skeleton swap its melee goal for the bow goal when it carries a bow.
            skeletonRider.reassessWeaponGoal();
        }

        if (CrashSuperProConfig.phantomRiderPersistenceRequired()) {
            rider.setPersistenceRequired();
        }
        RiderTags.markPhantomRider(rider);
        phantom.level().addFreshEntity(rider);
        rider.startRiding(phantom, true);
    }

    /**
     * New mechanic: a spawning creeper may be marked with the split tag, which makes it spawn a
     * small group of already igniting creepers once it dies.
     */
    private static void handleCreeper(MobSpawnEvent.FinalizeSpawn event, Creeper creeper) {
        if (!CrashSuperProConfig.creeperSplitEnabled()) {
            return;
        }
        if (CrashSuperProConfig.creeperSplitNaturalOnly() && event.getSpawnType() != MobSpawnType.NATURAL) {
            return;
        }

        double chance = CrashSuperProConfig.creeperSplitChance();
        if (chance > 0.0D && creeper.level().random.nextDouble() < chance) {
            creeper.addTag(CrashSuperProConfig.creeperSplitTag());
        }
    }

    /** The rider chances are rolled one after another, so at most one of them applies. */
    private static EntityType<? extends Mob> pickRiderType(double roll) {
        double threshold = CrashSuperProConfig.phantomWitherSkeletonChance();
        if (roll < threshold) {
            return EntityType.WITHER_SKELETON;
        }
        threshold += CrashSuperProConfig.phantomSkeletonChance();
        if (roll < threshold) {
            return EntityType.SKELETON;
        }
        threshold += CrashSuperProConfig.phantomPillagerChance();
        if (roll < threshold) {
            return EntityType.PILLAGER;
        }
        threshold += CrashSuperProConfig.phantomEvokerChance();
        if (roll < threshold) {
            return EntityType.EVOKER;
        }
        return null;
    }

    private static void handleVindicator(MobSpawnEvent.FinalizeSpawn event, Vindicator vindicator) {
        if (CrashSuperProConfig.vindicatorNaturalOnly() && event.getSpawnType() != MobSpawnType.NATURAL) {
            return;
        }

        double chance = CrashSuperProConfig.vindicatorChickenChance();
        if (chance <= 0.0D || vindicator.level().random.nextDouble() >= chance) {
            return;
        }

        Chicken chicken = EntityType.CHICKEN.create(vindicator.level());
        if (chicken == null) {
            return;
        }

        chicken.moveTo(vindicator.getX(), vindicator.getY(), vindicator.getZ(), vindicator.getYRot(), 0.0F);
        chicken.setPersistenceRequired();
        vindicator.level().addFreshEntity(chicken);
        vindicator.startRiding(chicken, true);
    }
}
