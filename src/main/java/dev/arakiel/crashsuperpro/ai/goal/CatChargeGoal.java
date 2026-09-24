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

import dev.arakiel.crashsuperpro.api.PoweredCreeper;
import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.monster.Creeper;

/**
 * New mechanic: creepers no longer run from cats. Coming close to a cat or an ocelot charges them
 * into a lightning creeper instead.
 */
public class CatChargeGoal extends Goal {
    private final Creeper creeper;
    private int cooldown;

    public CatChargeGoal(Creeper creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        if (!CrashSuperProConfig.creeperCatChargeEnabled() || this.creeper.isPowered()) {
            return false;
        }
        if (--this.cooldown > 0) {
            return false;
        }

        this.cooldown = CrashSuperProConfig.creeperCatCheckInterval();
        double radius = CrashSuperProConfig.creeperCatChargeRadius();
        return !this.creeper.level().getEntitiesOfClass(LivingEntity.class,
                this.creeper.getBoundingBox().inflate(radius),
                entity -> (entity instanceof Cat || entity instanceof Ocelot) && entity.isAlive()).isEmpty();
    }

    @Override
    public void start() {
        ((PoweredCreeper) this.creeper).crashsuperpro$setPowered(true);
        this.creeper.level().playSound(null, this.creeper.getX(), this.creeper.getY(), this.creeper.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
