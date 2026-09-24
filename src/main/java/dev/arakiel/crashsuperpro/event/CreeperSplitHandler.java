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
import dev.arakiel.crashsuperpro.mixin.CreeperInvoker;
import dev.arakiel.crashsuperpro.platform.DeferredActions;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: a creeper carrying the split tag spawns a small group of creepers when it dies. The
 * children are pushed right to the brink of detonation and never carry the tag themselves, so the
 * splitting cannot run away.
 */
public final class CreeperSplitHandler {
    private static final int SWELL_DIRECTION = 30;
    /** The vanilla creeper detonates once its swell counter reaches this. */
    private static final int MAX_SWELL = 30;

    private CreeperSplitHandler() {
    }

    /** Killed by a player or another mob. */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        try {
            handleLivingDeath(event);
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to handle a splitting creeper death.", exception);
        }
    }

    private static void handleLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Creeper creeper) {
            Level level = creeper.level();
            double x = creeper.getX();
            double y = creeper.getY();
            double z = creeper.getZ();
            float yRot = creeper.getYRot();
            DeferredActions.enqueue(() -> split(level, x, y, z, yRot, creeper));
        }
    }

    /**
     * Went off on its own. The detonation does not run through the normal death path, and spawning
     * from inside the explosion event is queued to the end of the tick.
     */
    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        try {
            handleExplosionStart(event);
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to handle a splitting creeper explosion.", exception);
        }
    }

    private static void handleExplosionStart(ExplosionEvent.Start event) {
        if (!event.getLevel().isClientSide() && event.getExplosion().getExploder() instanceof Creeper creeper) {
            double x = creeper.getX();
            double y = creeper.getY();
            double z = creeper.getZ();
            float yRot = creeper.getYRot();
            Level level = creeper.level();
            DeferredActions.enqueue(() -> split(level, x, y, z, yRot, creeper));
        }
    }

    private static void split(Level level, double x, double y, double z, float yRot, Creeper source) {
        if (level.isClientSide() || !CrashSuperProConfig.creeperSplitEnabled()) {
            return;
        }
        if (!source.getTags().contains(CrashSuperProConfig.creeperSplitTag())) {
            return;
        }

        int count = CrashSuperProConfig.creeperSplitCount();
        double spawnDistance = CrashSuperProConfig.creeperSplitSpawnRadius();
        int swellStart = MAX_SWELL - CrashSuperProConfig.creeperSplitDetonationTicks();
        double startAngle = level.random.nextDouble() * Math.PI * 2.0D;

        for (int i = 0; i < count; i++) {
            Creeper child = EntityType.CREEPER.create(level);
            if (child == null) {
                continue;
            }

            double angle = startAngle + (Math.PI * 2.0D / count) * i;
            double childX = x + Math.cos(angle) * spawnDistance;
            double childZ = z + Math.sin(angle) * spawnDistance;
            child.moveTo(childX, y, childZ, yRot, 0.0F);

            // Right at the edge of detonating, and explicitly without the split tag.
            child.setSwellDir(SWELL_DIRECTION);
            ((CreeperInvoker) child).setSwell(swellStart);
            level.addFreshEntity(child);
        }
    }
}
