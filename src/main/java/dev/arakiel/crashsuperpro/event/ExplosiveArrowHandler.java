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
import dev.arakiel.crashsuperpro.tags.BombTags;
import dev.arakiel.crashsuperpro.platform.DeferredActions;
import dev.arakiel.crashsuperpro.platform.DamageImmunity;
import dev.arakiel.crashsuperpro.world.LavaBurst;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Arrows of a tagged skeleton are special: one tag makes them explode on impact, the other leaves a
 * patch of lava behind. Both only ever apply to plain skeletons.
 */
public final class ExplosiveArrowHandler {
    private ExplosiveArrowHandler() {
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        try {
            handle(event);
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to turn an arrow impact into an effect.", exception);
        }
    }

    private static void handle(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) {
            return;
        }

        Level level = arrow.level();
        // WitherSkeleton is a sibling of Skeleton in 1.20.1, so this only matches plain skeletons.
        if (level.isClientSide() || !(arrow.getOwner() instanceof Skeleton shooter)) {
            return;
        }

        boolean explosive = CrashSuperProConfig.explosiveSkeletonEnabled()
                && BombTags.hasExplosiveArrows(shooter);
        boolean lava = CrashSuperProConfig.lavaSkeletonEnabled() && BombTags.hasLavaArrows(shooter);
        if (!explosive && !lava) {
            return;
        }

        Vec3 position = event.getRayTraceResult().getLocation();
        event.setCanceled(true);
        arrow.discard();

        // Going off straight from the impact event would run the explosion code re-entrantly - the
        // same trap the creeper chain reaction fell into - so the effect waits for the tick to end.
        if (explosive) {
            float power = (float) CrashSuperProConfig.explosiveSkeletonPower();
            DeferredActions.enqueue(() -> {
                // The shooter is never hurt by its own arrow.
                DamageImmunity.protect(shooter);
                level.explode(shooter, position.x, position.y, position.z, power, Level.ExplosionInteraction.MOB);
            });
        } else {
            BlockPos pos = BlockPos.containing(position);
            int radius = CrashSuperProConfig.lavaSkeletonRadius();
            DeferredActions.enqueue(() -> {
                LavaBurst.apply(level, pos, radius);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FIREWORK, position.x, position.y, position.z,
                            CrashSuperProConfig.arrowImpactParticleCount(), 0.3D, 0.3D, 0.3D, 0.1D);
                }
            });
        }
    }
}
