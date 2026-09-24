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

import java.util.EnumSet;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.tags.BombTags;
import dev.arakiel.crashsuperpro.world.TargetFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;

/**
 * Behaviour of the two special arrow skeletons. While a player, iron golem or villager is inside
 * the target radius the skeleton hunts them like a normal skeleton. Once nobody is left it turns on
 * the valuable blocks around it and shoots those instead.
 */
public class SpecialSkeletonGoal extends Goal {
    private final Mob skeleton;
    private int checkCooldown;
    private int shootCooldown;
    private BlockPos blockTarget;

    public SpecialSkeletonGoal(Mob skeleton) {
        this.skeleton = skeleton;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!isSpecial() || this.skeleton.level().isClientSide()) {
            return false;
        }
        if (--this.checkCooldown > 0) {
            return false;
        }
        this.checkCooldown = CrashSuperProConfig.specialSkeletonCheckInterval();

        LivingEntity prey = TargetFinder.findHuntTarget(this.skeleton,
                CrashSuperProConfig.specialSkeletonTargetRange());
        if (prey != null) {
            // Somebody worth hunting is around, the vanilla skeleton AI takes it from here.
            this.skeleton.setTarget(prey);
            this.blockTarget = null;
            return false;
        }

        this.skeleton.setTarget(null);
        this.blockTarget = TargetFinder.findNearestExplodable(this.skeleton,
                CrashSuperProConfig.specialSkeletonBlockRadius(),
                CrashSuperProConfig.bombardmentMaxResistance());
        return this.blockTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        return isSpecial() && this.blockTarget != null
                && TargetFinder.findHuntTarget(this.skeleton,
                        CrashSuperProConfig.specialSkeletonTargetRange()) == null;
    }

    @Override
    public void start() {
        this.shootCooldown = CrashSuperProConfig.specialSkeletonShootInterval();
    }

    @Override
    public void tick() {
        BlockPos target = this.blockTarget;
        if (target == null) {
            return;
        }

        this.skeleton.getLookControl().setLookAt(target.getX() + 0.5D, target.getY() + 0.5D,
                target.getZ() + 0.5D, 30.0F, 30.0F);

        if (--this.shootCooldown <= 0) {
            this.shootCooldown = CrashSuperProConfig.specialSkeletonShootInterval();
            shootAt(target);
        }
    }

    /** Fires a normal skeleton arrow at the block, the arrow carries the skeleton's own tag. */
    private void shootAt(BlockPos target) {
        Level level = this.skeleton.level();
        Arrow arrow = new Arrow(level, this.skeleton);
        double dx = target.getX() + 0.5D - this.skeleton.getX();
        double dz = target.getZ() + 0.5D - this.skeleton.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double dy = target.getY() + 0.5D - arrow.getY();

        arrow.shoot(dx, dy + horizontal * 0.2D, dz,
                (float) CrashSuperProConfig.specialSkeletonArrowSpeed(),
                (float) CrashSuperProConfig.specialSkeletonArrowSpread());
        level.addFreshEntity(arrow);
    }

    private boolean isSpecial() {
        return BombTags.hasExplosiveArrows(this.skeleton) || BombTags.hasLavaArrows(this.skeleton);
    }

    @Override
    public void stop() {
        this.blockTarget = null;
    }
}
