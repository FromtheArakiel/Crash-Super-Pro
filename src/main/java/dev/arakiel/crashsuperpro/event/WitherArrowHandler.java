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
import dev.arakiel.crashsuperpro.tags.RiderTags;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: an arrow shot by a wither skeleton that is a phantom rider sets its victim on fire
 * and applies wither and blindness. All three durations are configurable and default to five
 * seconds. A plain wither skeleton from the world is not affected.
 */
public final class WitherArrowHandler {
    private static final double TICKS_PER_SECOND = 20.0D;

    private WitherArrowHandler() {
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        try {
            handleProjectileImpact(event);
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to apply the wither skeleton arrow effects.", exception);
        }
    }

    private static void handleProjectileImpact(ProjectileImpactEvent event) {
        if (!CrashSuperProConfig.witherArrowsEnabled()) {
            return;
        }
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) {
            return;
        }
        if (!(event.getRayTraceResult() instanceof EntityHitResult hit)) {
            return;
        }
        if (!(hit.getEntity() instanceof LivingEntity target) || arrow.level().isClientSide()) {
            return;
        }
        if (!(arrow.getOwner() instanceof WitherSkeleton shooter)) {
            return;
        }
        if (CrashSuperProConfig.witherArrowsRiderOnly() && !RiderTags.isPhantomRider(shooter)) {
            return;
        }

        int witherTicks = toTicks(CrashSuperProConfig.witherArrowWitherSeconds());
        int blindnessTicks = toTicks(CrashSuperProConfig.witherArrowBlindnessSeconds());
        int fireSeconds = (int) Math.round(CrashSuperProConfig.witherArrowFireSeconds());
        int witherLevel = Math.max(0, CrashSuperProConfig.witherArrowWitherLevel() - 1);
        int blindnessLevel = Math.max(0, CrashSuperProConfig.witherArrowBlindnessLevel() - 1);

        if (witherTicks > 0) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, witherTicks, witherLevel), shooter);
        }
        if (blindnessTicks > 0) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, blindnessTicks, blindnessLevel), shooter);
        }
        if (fireSeconds > 0) {
            target.setSecondsOnFire(fireSeconds);
        }
    }

    private static int toTicks(double seconds) {
        return (int) Math.round(seconds * TICKS_PER_SECOND);
    }
}
