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

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.arakiel.crashsuperpro.api.PoweredCreeper;
import dev.arakiel.crashsuperpro.ai.goal.CatChargeGoal;
import dev.arakiel.crashsuperpro.ai.goal.MountNearestEntityGoal;
import dev.arakiel.crashsuperpro.ai.goal.SeekExplodableBlockGoal;
import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.ai.goal.SwellController;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.network.syncher.EntityDataAccessor;

/** Adds the same three goals the original mod added, now built from the reusable pieces. */
@Mixin(Creeper.class)
public abstract class CreeperMixin extends Mob implements PoweredCreeper {
    @Shadow
    @Final
    private static EntityDataAccessor<Boolean> DATA_IS_POWERED;

    protected CreeperMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void crashsuperpro$setPowered(boolean powered) {
        ((Creeper) (Object) this).getEntityData().set(DATA_IS_POWERED, powered);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void crashsuperpro$registerGoals(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        this.goalSelector.addGoal(1, new MountNearestEntityGoal<>(creeper, Spider.class, spider -> true,
                CrashSuperProConfig.creeperMountSpeed(), CrashSuperProConfig.creeperMountSearchRadius(),
                CrashSuperProConfig.creeperMountDistance()));
        if (CrashSuperProConfig.creeperOpenDoors()) {
            this.goalSelector.addGoal(1, new OpenDoorGoal(creeper, true));
        }
        this.goalSelector.addGoal(2, new SeekExplodableBlockGoal(creeper, CrashSuperProConfig.creeperSeekRadius(),
                CrashSuperProConfig.creeperSeekSpeed(), CrashSuperProConfig.creeperSeekMaxResistance(),
                SwellController.creeper()));
        this.goalSelector.addGoal(2, new CatChargeGoal(creeper));
    }

    /** The vanilla cat and ocelot flee goals are dropped, the cat reaction goal replaces them. */
    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void crashsuperpro$removeCatFleeGoals(CallbackInfo ci) {
        List<Goal> toRemove = new ArrayList<>();
        for (WrappedGoal wrapped : this.goalSelector.getAvailableGoals()) {
            Goal goal = wrapped.getGoal();
            if (goal instanceof AvoidEntityGoal<?> avoid) {
                Class<?> avoided = ((AvoidEntityGoalAccessor) avoid).getAvoidedClass();
                if (avoided == Cat.class || avoided == Ocelot.class) {
                    toRemove.add(goal);
                }
            }
        }
        toRemove.forEach(this.goalSelector::removeGoal);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void crashsuperpro$tick(CallbackInfo ci) {
        Creeper creeper = (Creeper) (Object) this;
        if (creeper.getPersistentData().getBoolean("Boom")) {
            creeper.setSwellDir(3);
        }
        if (creeper.isOnFire()) {
            creeper.setSwellDir(1);
        }
    }
}
