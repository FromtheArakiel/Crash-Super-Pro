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

package dev.arakiel.crashsuperpro.platform;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Damage caused by this mod never hurts the one who caused it. Explosion owners are collected right
 * before the explosion and dropped from its victim list, splash effects of a bomber are cancelled
 * when they would hit their own source.
 */
public final class DamageImmunity {
    private static final Set<UUID> EXPLOSION_OWNERS = new HashSet<>();
    private static final Set<UUID> POTION_OWNERS = new HashSet<>();

    private DamageImmunity() {
    }

    public static void protect(Entity owner) {
        if (owner == null || !CrashSuperProConfig.noSelfDamage()) {
            return;
        }
        EXPLOSION_OWNERS.add(owner.getUUID());
    }

    /** Called right before a bomber throws its splash potions at someone else. */
    public static void protectFromOwnPotions(Entity owner) {
        if (owner == null || !CrashSuperProConfig.noSelfDamage()) {
            return;
        }
        POTION_OWNERS.add(owner.getUUID());
    }

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (EXPLOSION_OWNERS.isEmpty()) {
            return;
        }
        try {
            event.getAffectedEntities().removeIf(entity -> EXPLOSION_OWNERS.contains(entity.getUUID()));
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to keep an explosion owner out of its own blast.", exception);
        }
    }

    @SubscribeEvent
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        if (!CrashSuperProConfig.noSelfDamage() || POTION_OWNERS.isEmpty()) {
            return;
        }
        try {
            if (POTION_OWNERS.contains(event.getEntity().getUUID())) {
                event.setResult(MobEffectEvent.Applicable.Result.DENY);
            }
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to keep a splash effect away from its own thrower.", exception);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (!EXPLOSION_OWNERS.isEmpty()) {
            EXPLOSION_OWNERS.clear();
        }
        if (!POTION_OWNERS.isEmpty()) {
            POTION_OWNERS.clear();
        }
    }
}
