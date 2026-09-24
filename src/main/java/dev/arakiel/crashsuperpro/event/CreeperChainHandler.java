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
import dev.arakiel.crashsuperpro.platform.DamageImmunity;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: when a creeper starts to explode every other creeper in range is detonated right
 * away, which turns a group of creepers into a chain reaction.
 */
public final class CreeperChainHandler {
    /** Creepers this tick already pushed into a chain detonation. */
    private static final Set<UUID> TRIGGERED = new HashSet<>();

    private CreeperChainHandler() {
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !TRIGGERED.isEmpty()) {
            TRIGGERED.clear();
        }
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        try {
            handleExplosionStart(event);
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to chain a creeper explosion.", exception);
        }
    }

    private static void handleExplosionStart(ExplosionEvent.Start event) {
        if (!CrashSuperProConfig.chainExplosionEnabled()) {
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide() || !(event.getExplosion().getExploder() instanceof Creeper source)) {
            return;
        }

        AABB area = source.getBoundingBox().inflate(CrashSuperProConfig.chainExplosionRadius());
        List<Creeper> others = level.getEntitiesOfClass(Creeper.class, area,
                creeper -> creeper != source && creeper.isAlive());
        for (Creeper other : others) {
            // Its own blast still goes off, it is only the follow up detonations that are deduplicated
            // so the same creeper can never be blown up twice in one tick.
            if (!TRIGGERED.add(other.getUUID())) {
                continue;
            }
            // Detonating right here would re-enter the explosion code from inside its own event, so
            // the follow up detonation runs at the end of the tick instead.
            DeferredActions.enqueue(() -> {
                if (other.isAlive() && !other.isRemoved()) {
                    DamageImmunity.protect(other);
                    // The invoker mixin opens the private detonation for exactly this.
                    ((CreeperInvoker) other).invokeExplodeCreeper();
                }
            });
        }
    }
}
