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

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The hurt handling of the original mod, unchanged: a creeper that is already swelling survives a
 * killing blow, fall damage cannot finish it and a creeper hurt by another creeper swells up.
 */
public final class CreeperCombatHandler {
    private CreeperCombatHandler() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        double damage = event.getAmount();
        DamageSource damageSource = event.getSource();
        Level level = livingEntity.level();
        if (!level.isClientSide() && livingEntity instanceof Creeper creeper) {
            if ((damage > creeper.getHealth() && creeper.getSwellDir() > 0)) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                event.setCanceled(true);
                return;
            }
            if (damage > creeper.getHealth() && damageSource.is(DamageTypes.FALL)) {
                creeper.heal(1);
                creeper.setSwellDir(30);
                event.setCanceled(true);
                return;
            }
            if (damageSource != null && damageSource.getEntity() instanceof Creeper) {
                creeper.setSwellDir((int) (damage * 30 / creeper.getMaxHealth()));
                creeper.getPersistentData().putBoolean("Boom", true);
                if (damage > creeper.getHealth()) {
                    creeper.heal(1);
                    event.setCanceled(true);
                }
            }
        }
    }
}
