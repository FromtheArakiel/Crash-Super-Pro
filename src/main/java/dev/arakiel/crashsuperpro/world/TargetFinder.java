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

package dev.arakiel.crashsuperpro.world;

import java.util.List;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * The block entity search that the original mod used for creepers, extracted so phantoms can reuse
 * exactly the same notion of a "valuable target".
 */
public final class TargetFinder {
    private TargetFinder() {
    }

    /**
     * Scans the nine chunks around the hunter for the closest block entity that is not protected
     * against explosions.
     */
    public static BlockPos findNearestExplodable(Mob hunter, int radius, double maxResistance) {
        BlockPos hunterPos = hunter.blockPosition();
        Level level = hunter.level();
        ChunkPos chunkPos = level.getChunk(hunterPos).getPos();
        double minDistance = Double.MAX_VALUE;
        BlockPos nearestPos = null;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                ChunkAccess chunk = level.getChunk(chunkPos.x + dx, chunkPos.z + dz);
                for (BlockPos pos : chunk.getBlockEntitiesPos()) {
                    if (isValidTarget(hunter, pos, radius, maxResistance)) {
                        double distance = pos.distSqr(hunterPos);
                        if (distance < minDistance) {
                            minDistance = distance;
                            nearestPos = pos;
                        }
                    }
                }
            }
        }

        return nearestPos;
    }

    private static boolean isValidTarget(Mob hunter, BlockPos pos, int radius, double maxResistance) {
        return pos.distSqr(hunter.blockPosition()) <= (double) radius * (double) radius
                && hunter.level().getBlockState(pos).getExplosionResistance(hunter.level(), pos, null) < maxResistance;
    }

    /** True while at least one player is alive inside the radius. */
    public static boolean hasLivingPlayer(Mob hunter, double range) {
        List<Player> players = hunter.level().getEntitiesOfClass(Player.class,
                hunter.getBoundingBox().inflate(range), Player::isAlive);
        return !players.isEmpty();
    }

    /**
     * First stage target of the bomber AI: the closest real player, iron golem or villager inside
     * the hunt radius.
     */
    public static LivingEntity findHuntTarget(Mob hunter, double range) {
        List<LivingEntity> candidates = hunter.level().getEntitiesOfClass(LivingEntity.class,
                hunter.getBoundingBox().inflate(range), entity -> isValidPrey(hunter, entity));

        LivingEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates) {
            double distance = hunter.distanceToSqr(candidate);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = candidate;
            }
        }
        return nearest;
    }

    private static boolean isValidPrey(Mob hunter, LivingEntity entity) {
        if (entity == hunter || !entity.isAlive() || entity.isRemoved()) {
            return false;
        }
        if (entity instanceof Player player) {
            return CrashSuperProConfig.bombardmentHuntPlayers()
                    && !player.isCreative() && !player.isSpectator();
        }
        if (entity instanceof IronGolem) {
            return CrashSuperProConfig.bombardmentHuntIronGolems();
        }
        if (entity instanceof Villager) {
            return CrashSuperProConfig.bombardmentHuntVillagers();
        }
        return false;
    }

    /** Picks a random spot on the ground within the radius and around the hunter. */
    public static BlockPos randomSpot(Mob hunter, int radius, RandomSource random) {
        BlockPos origin = hunter.blockPosition();
        int dx = random.nextInt(radius * 2 + 1) - radius;
        int dz = random.nextInt(radius * 2 + 1) - radius;
        BlockPos horizontal = origin.offset(dx, 0, dz);
        return hunter.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, horizontal);
    }

    /** The closest player that is neither creative nor a spectator, the potion phantom only hunts those. */
    public static Player findNearestPlayer(Mob hunter, double range) {
        List<Player> players = hunter.level().getEntitiesOfClass(Player.class,
                hunter.getBoundingBox().inflate(range),
                player -> player.isAlive() && !player.isRemoved()
                        && !player.isCreative() && !player.isSpectator());

        Player nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Player player : players) {
            double distance = hunter.distanceToSqr(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = player;
            }
        }
        return nearest;
    }
}
