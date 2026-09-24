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

package dev.arakiel.crashsuperpro.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.arakiel.crashsuperpro.tags.BombTags;
import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.platform.SafeLog;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

/**
 * Leaves a firework rocket trail behind the special arrows of a tagged skeleton. The particles are
 * sent from the server, so the arrow does not need to know its shooter on the client.
 */
@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void crashsuperpro$fireworkTrail(CallbackInfo ci) {
        try {
            AbstractArrow arrow = (AbstractArrow) (Object) this;
            Level level = arrow.level();
            if (!(level instanceof ServerLevel serverLevel)) {
                return;
            }
            // WitherSkeleton is a sibling of Skeleton in 1.20.1, so this only matches plain skeletons.
            if (!(arrow.getOwner() instanceof Skeleton shooter)) {
                return;
            }
            if (!BombTags.hasExplosiveArrows(shooter) && !BombTags.hasLavaArrows(shooter)) {
                return;
            }

            int trail = CrashSuperProConfig.arrowTrailParticleCount();
            if (trail > 0) {
                serverLevel.sendParticles(ParticleTypes.FIREWORK, arrow.getX(), arrow.getY(), arrow.getZ(),
                        trail, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to draw the firework trail of an arrow.", exception);
        }
    }
}
