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
import dev.arakiel.crashsuperpro.util.RiderTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * New mechanic: an arrow shot by a skeleton that is a phantom rider applies Instant Damage, level
 * two by default. Only the riders are affected, an ordinary skeleton keeps shooting plain arrows.
 */
public final class SkeletonArrowHandler {
    private SkeletonArrowHandler() {
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!CrashSuperProConfig.skeletonArrowsEnabled()) {
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
        if (!(arrow.getOwner() instanceof Skeleton shooter)) {
            return;
        }
        if (CrashSuperProConfig.skeletonArrowsRiderOnly() && !RiderTags.isPhantomRider(shooter)) {
            return;
        }

        // Instant damage effects are applied as soon as they are added, the duration is irrelevant.
        int amplifier = Math.max(0, CrashSuperProConfig.skeletonArrowInstantDamageLevel() - 1);
        target.addEffect(new MobEffectInstance(MobEffects.HARM, 1, amplifier), shooter);
    }
}
