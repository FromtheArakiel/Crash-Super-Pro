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

package dev.arakiel.crashsuperpro.world;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.tags.BombTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;

/** Turns a bomber's tag into the actual payload. */
public final class Bombardier {
    private Bombardier() {
    }

    public static void drop(Mob bomber, BlockPos target) {
        Level level = bomber.level();
        if (level.isClientSide) {
            return;
        }

        double x = target.getX() + 0.5D;
        double y = target.getY() + 1.0D;
        double z = target.getZ() + 0.5D;

        if (BombTags.hasTnt(bomber)) {
            PrimedTnt tnt = new PrimedTnt(level, x, y, z, bomber);
            tnt.setFuse(CrashSuperProConfig.bombardmentTntFuse());
            level.addFreshEntity(tnt);
        } else if (BombTags.hasFireball(bomber)) {
            Fireball fireball = CrashSuperProConfig.bombardmentLargeFireball()
                    ? new LargeFireball(level, bomber, 0.0D, -1.0D, 0.0D,
                            CrashSuperProConfig.bombardmentFireballPower())
                    : new SmallFireball(level, bomber, 0.0D, -1.0D, 0.0D);
            fireball.setPos(x, y + 4.0D, z);
            fireball.setDeltaMovement(0.0D, -0.8D, 0.0D);
            level.addFreshEntity(fireball);
        }
    }
}
