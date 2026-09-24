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
import java.util.List;
import java.util.function.Predicate;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * The "walk to the nearest free spider and ride it" goal of the original mod, generalised so any
 * monster can mount any mob type.
 */
public class MountNearestEntityGoal<T extends Mob> extends Goal {
    private final Mob rider;
    private final Class<T> mountType;
    private final Predicate<T> mountFilter;
    private final double speedModifier;
    private final double searchRadius;
    private final double mountDistanceSqr;
    private T targetMount;
    private int timeToRecalcPath;

    public MountNearestEntityGoal(Mob rider, Class<T> mountType, Predicate<T> mountFilter,
            double speedModifier, double searchRadius, double mountDistanceSqr) {
        this.rider = rider;
        this.mountType = mountType;
        this.mountFilter = mountFilter;
        this.speedModifier = speedModifier;
        this.searchRadius = searchRadius;
        this.mountDistanceSqr = mountDistanceSqr;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.rider.getVehicle() instanceof Mob) {
            return false;
        }

        List<T> candidates = this.rider.level().getEntitiesOfClass(this.mountType,
                this.rider.getBoundingBox().inflate(this.searchRadius),
                mount -> !mount.isVehicle() && mount.isAlive() && this.mountFilter.test(mount));

        if (candidates.isEmpty()) {
            return false;
        }

        this.targetMount = candidates.get(0);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        double follow = CrashSuperProConfig.creeperMountFollowDistance();
        return !this.rider.isVehicle()
                && this.targetMount != null
                && this.targetMount.isAlive()
                && this.rider.distanceToSqr(this.targetMount) < follow * follow;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.targetMount = null;
    }

    @Override
    public void tick() {
        if (this.targetMount == null || !this.targetMount.isAlive() || this.targetMount.isRemoved()) {
            return;
        }

        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = CrashSuperProConfig.creeperMountRecalcInterval();
            this.rider.getNavigation().moveTo(this.targetMount, this.speedModifier);
        }

        if (this.rider.distanceToSqr(this.targetMount) < this.mountDistanceSqr) {
            this.rider.startRiding(this.targetMount, true);
            this.stop();
        }
    }
}
