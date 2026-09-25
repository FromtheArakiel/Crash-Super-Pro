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
import dev.arakiel.crashsuperpro.world.Bombardier;
import dev.arakiel.crashsuperpro.world.TargetFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;

/**
 * The bomber AI of a tagged phantom, in two stages.
 *
 * <p>Stage one: look for a real player, an iron golem or a villager inside the hunt radius. If there
 * is one the phantom chases it and drops its payload on the spot every few seconds.
 *
 * <p>Stage two: with nobody left to hunt the phantom flies to a valuable target found with the
 * creeper logic - or to a random spot when nothing valuable is around - and bombs it.
 *
 * <p>A phantom without a payload tag never activates this goal, so it keeps the vanilla AI.
 */
public class BombardGoal extends Goal {
    private static final int TARGET_CHECK_INTERVAL = 10;

    private final Phantom phantom;
    private int cooldown;
    private int checkCooldown;
    private int huntTimer;
    private LivingEntity prey;
    private BlockPos target;

    public BombardGoal(Phantom phantom) {
        this.phantom = phantom;
        // Only LOOK: taking MOVE would lock out the vanilla phantom goals and freeze it in place.
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }

        if (!CrashSuperProConfig.bombardmentEnabled() || !BombTags.isBomber(this.phantom)) {
            return false;
        }
        if (this.phantom.level().isClientSide()) {
            return false;
        }

        // Stage one: hunt someone worth chasing. The lookup is throttled, it is an area query.
        if (--this.checkCooldown > 0) {
            return false;
        }
        this.checkCooldown = TARGET_CHECK_INTERVAL;

        this.prey = TargetFinder.findHuntTarget(this.phantom, CrashSuperProConfig.bombardmentHuntRange());
        if (this.prey != null) {
            this.huntTimer = CrashSuperProConfig.bombardmentHuntDropInterval();
            // Hand the target to the vanilla attack goal, that is what actually flies and dives.
            this.phantom.setTarget(this.prey);
            return true;
        }

        // Stage two: nobody around, so go and bomb something valuable (or a random spot).
        int radius = CrashSuperProConfig.bombardmentTargetRadius();
        BlockPos found = null;
        if (CrashSuperProConfig.bombardmentPreferValuableTargets()) {
            found = TargetFinder.findNearestExplodable(this.phantom, radius,
                    CrashSuperProConfig.bombardmentMaxResistance());
        }
        if (found == null) {
            found = TargetFinder.randomSpot(this.phantom, radius, this.phantom.level().random);
        }

        this.target = found;
        return this.target != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (!BombTags.isBomber(this.phantom) || !CrashSuperProConfig.bombardmentEnabled()) {
            return false;
        }
        if (this.prey != null) {
            return this.prey.isAlive() && !this.prey.isRemoved();
        }
        return this.target != null;
    }

    @Override
    public void start() {
        this.huntTimer = CrashSuperProConfig.bombardmentHuntDropInterval();
    }

    @Override
    public void tick() {
        if (this.prey != null) {
            hunt();
        } else if (this.target != null) {
            bombard();
        }
    }

    private void hunt() {
        LivingEntity current = this.prey;
        if (current == null) {
            return;
        }

        double hoverY = current.getY() + CrashSuperProConfig.bombardmentHuntHeight();
        this.phantom.getMoveControl().setWantedPosition(current.getX(), hoverY, current.getZ(), 1.0D);
        this.phantom.getLookControl().setLookAt(current, 30.0F, 30.0F);

        if (--this.huntTimer <= 0) {
            this.huntTimer = CrashSuperProConfig.bombardmentHuntDropInterval();
            dropHere();
        }
    }

    private void bombard() {
        BlockPos current = this.target;
        if (current == null) {
            return;
        }

        double x = current.getX() + 0.5D;
        double z = current.getZ() + 0.5D;
        double hoverY = current.getY() + CrashSuperProConfig.bombardmentHoverHeight();
        this.phantom.getMoveControl().setWantedPosition(x, hoverY, z, 1.0D);
        this.phantom.getLookControl().setLookAt(x, current.getY(), z, 30.0F, 30.0F);

        double arrival = CrashSuperProConfig.bombardmentArrivalDistance();
        if (this.phantom.distanceToSqr(x, this.phantom.getY(), z) < arrival * arrival) {
            if (this.phantom.level().random.nextDouble() < CrashSuperProConfig.bombardmentChance()) {
                Bombardier.drop(this.phantom, current);
            }
            this.cooldown = CrashSuperProConfig.bombardmentCooldown();
            this.target = null;
        }
    }

    /** Drops the payload straight below the phantom while it is chasing its prey. */
    private void dropHere() {
        if (this.phantom.level().random.nextDouble() >= CrashSuperProConfig.bombardmentChance()) {
            return;
        }
        Bombardier.drop(this.phantom, this.phantom.blockPosition().below());
    }

    @Override
    public void stop() {
        this.prey = null;
        this.target = null;
    }
}
