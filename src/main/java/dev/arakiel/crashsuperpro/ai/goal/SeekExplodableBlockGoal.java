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
import java.util.Objects;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.ai.goal.SwellController;
import dev.arakiel.crashsuperpro.world.TargetFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * The "walk to a valuable block entity and blow it up" goal of the original mod. The block lookup
 * moved to {@link TargetFinder} and the swelling is delegated to a {@link SwellController} so the
 * goal can be handed to monsters that do not swell up.
 */
public class SeekExplodableBlockGoal extends Goal {
    private static final double MIN_DISTANCE_SQR = 0.01D;

    private final Mob hunter;
    private final int radius;
    private final double speed;
    private final double maxResistance;
    private final SwellController swellController;
    private BlockPos targetPos;

    public SeekExplodableBlockGoal(Mob hunter, int radius, double speed, double maxResistance,
            SwellController swellController) {
        this.hunter = hunter;
        this.radius = radius;
        this.speed = speed;
        this.maxResistance = maxResistance;
        this.swellController = swellController;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.targetPos = TargetFinder.findNearestExplodable(this.hunter, this.radius, this.maxResistance);
        return this.targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.targetPos != null
                && this.hunter.getNavigation().isInProgress()
                && this.hunter.level().getBlockEntity(this.targetPos) != null
                && !Objects.requireNonNull(this.hunter.level().getBlockEntity(this.targetPos)).isRemoved();
    }

    @Override
    public void start() {
        this.hunter.getNavigation().moveTo(
                this.targetPos.getX() + 0.5,
                this.targetPos.getY(),
                this.targetPos.getZ() + 0.5,
                this.speed);
    }

    @Override
    public void tick() {
        if (this.hunter.getVehicle() instanceof Mob mount) {
            mount.getNavigation().setSpeedModifier(this.speed);
        }

        if (this.targetPos != null) {
            this.hunter.getLookControl().setLookAt(
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY() + 0.5,
                    this.targetPos.getZ() + 0.5,
                    10.0F,
                    this.hunter.getMaxHeadXRot());

            double distance = this.hunter.distanceToSqr(
                    this.targetPos.getX(),
                    this.targetPos.getY(),
                    this.targetPos.getZ());
            int max = CrashSuperProConfig.creeperSeekSwellMax();
            int swell = distance <= MIN_DISTANCE_SQR ? max : (int) Math.min(max, 10.0D / distance);
            this.swellController.swell(this.hunter, swell);
        }
    }

    @Override
    public void stop() {
        this.targetPos = null;
        this.hunter.getNavigation().stop();
    }
}
