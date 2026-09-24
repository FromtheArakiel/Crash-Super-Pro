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
import dev.arakiel.crashsuperpro.util.DeferredActions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: when a creeper starts to explode every other creeper in range is detonated right
 * away, which turns a group of creepers into a chain reaction.
 */
public final class CreeperChainHandler {
    private CreeperChainHandler() {
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        if (!CrashSuperProConfig.chainExplosionEnabled()) {
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide() || !(event.getExplosion().getExploder() instanceof Creeper source)) {
            return;
        }

        AABB area = source.getBoundingBox().inflate(CrashSuperProConfig.chainExplosionRadius());
        for (Creeper other : level.getEntitiesOfClass(Creeper.class, area,
                creeper -> creeper != source && creeper.isAlive())) {
            // Detonating right here would re-enter the explosion code from inside its own event, so
            // the follow up detonation runs at the end of the tick instead.
            DeferredActions.enqueue(() -> {
                if (other.isAlive() && !other.isRemoved()) {
                    // The invoker mixin opens the private detonation for exactly this.
                    ((CreeperInvoker) other).invokeExplodeCreeper();
                }
            });
        }
    }
}
