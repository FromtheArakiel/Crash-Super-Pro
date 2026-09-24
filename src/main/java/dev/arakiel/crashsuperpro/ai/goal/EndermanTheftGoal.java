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
import dev.arakiel.crashsuperpro.ai.goal.EndermanTheftTracker;
import dev.arakiel.crashsuperpro.world.TargetFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * New mechanic: an idle enderman walks to a valuable block, picks it up and leaves the spot to be
 * turned into dirt a few seconds later.
 */
public class EndermanTheftGoal extends Goal {
    private final EnderMan enderman;
    private int searchCooldown;
    private BlockPos target;

    public EndermanTheftGoal(EnderMan enderman) {
        this.enderman = enderman;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!CrashSuperProConfig.endermanTheftEnabled() || this.enderman.level().isClientSide()) {
            return false;
        }
        if (this.enderman.getTarget() != null || this.enderman.getCarriedBlock() != null) {
            return false;
        }
        if (--this.searchCooldown > 0) {
            return false;
        }
        this.searchCooldown = CrashSuperProConfig.endermanTheftSearchInterval();

        this.target = TargetFinder.findNearestExplodable(this.enderman,
                CrashSuperProConfig.endermanTheftRadius(), CrashSuperProConfig.endermanTheftMaxResistance());
        return this.target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && this.enderman.getTarget() == null;
    }

    @Override
    public void start() {
        if (this.target != null) {
            this.enderman.getNavigation().moveTo(this.target.getX() + 0.5D, this.target.getY(),
                    this.target.getZ() + 0.5D, CrashSuperProConfig.endermanTheftSpeed());
        }
    }

    @Override
    public void tick() {
        BlockPos current = this.target;
        if (current == null) {
            return;
        }

        this.enderman.getLookControl().setLookAt(current.getX() + 0.5D, current.getY() + 0.5D,
                current.getZ() + 0.5D, 30.0F, 30.0F);

        double reach = CrashSuperProConfig.endermanTheftReach();
        if (this.enderman.distanceToSqr(current.getX() + 0.5D, current.getY() + 0.5D, current.getZ() + 0.5D)
                < reach * reach) {
            steal(current);
            this.target = null;
        }
    }

    private void steal(BlockPos pos) {
        Level level = this.enderman.level();
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) {
            return;
        }

        this.enderman.setCarriedBlock(state);
        level.removeBlock(pos, false);
        EndermanTheftTracker.track(this.enderman);
    }

    @Override
    public void stop() {
        this.target = null;
    }
}
