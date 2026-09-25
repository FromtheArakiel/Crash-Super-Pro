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

import dev.arakiel.crashsuperpro.platform.SafeLog;
import dev.arakiel.crashsuperpro.tags.BombTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * A skeleton with exploding arrows is immune to explosions, the lava variant is immune to fire, so
 * they can keep firing at point blank range without killing themselves.
 */
public final class SkeletonImmunityHandler {
    private SkeletonImmunityHandler() {
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        try {
            if (!(event.getEntity() instanceof Skeleton skeleton) || skeleton.level().isClientSide()) {
                return;
            }
            if (BombTags.hasExplosiveArrows(skeleton) && event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                event.setCanceled(true);
                return;
            }
            if (BombTags.hasLavaArrows(skeleton) && event.getSource().is(DamageTypeTags.IS_FIRE)) {
                skeleton.clearFire();
                event.setCanceled(true);
            }
        } catch (RuntimeException exception) {
            SafeLog.error("Failed to apply the arrow skeleton immunities.", exception);
        }
    }
}
