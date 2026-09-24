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

package dev.arakiel.crashsuperpro.ai.goal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Remembers what an enderman picked up and turns the block it is carrying into dirt a few seconds
 * later, together with the teleport particles an enderman would normally leave behind.
 */
public final class EndermanTheftTracker {
    private static final List<Pending> PENDING = new ArrayList<>();

    private EndermanTheftTracker() {
    }

    public static void track(EnderMan enderman) {
        PENDING.add(new Pending(enderman, CrashSuperProConfig.endermanTheftDelay()));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || PENDING.isEmpty()) {
            return;
        }

        try {
            Iterator<Pending> iterator = PENDING.iterator();
            while (iterator.hasNext()) {
                Pending pending = iterator.next();
                if (--pending.ticks > 0) {
                    continue;
                }
                iterator.remove();
                pending.finish();
            }
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to finish an enderman theft.", exception);
        }
    }

    private static final class Pending {
        private final EnderMan enderman;
        private int ticks;

        private Pending(EnderMan enderman, int ticks) {
            this.enderman = enderman;
            this.ticks = ticks;
        }

        private void finish() {
            if (!this.enderman.isAlive() || this.enderman.isRemoved()) {
                return;
            }
            if (!(this.enderman.level() instanceof ServerLevel serverLevel)) {
                return;
            }

            // The block in its hands turns into dirt, the spot it was taken from stays empty.
            this.enderman.setCarriedBlock(Blocks.DIRT.defaultBlockState());
            serverLevel.sendParticles(ParticleTypes.PORTAL,
                    this.enderman.getX(), this.enderman.getY() + 1.5D, this.enderman.getZ(),
                    CrashSuperProConfig.endermanTheftParticleCount(), 0.5D, 0.7D, 0.5D, 0.2D);
        }
    }
}
