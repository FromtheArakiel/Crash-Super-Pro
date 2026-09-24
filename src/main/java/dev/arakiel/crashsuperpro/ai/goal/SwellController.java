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

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Creeper;

/**
 * Lets a goal tell its owner to start swelling without hard wiring the goal to a creeper, so the
 * "walk to something and blow it up" behaviour can be reused by other monsters.
 */
@FunctionalInterface
public interface SwellController {
    void swell(Mob mob, int direction);

    /** The creeper flavour kept from the original mod. */
    static SwellController creeper() {
        return (mob, direction) -> {
            if (mob instanceof Creeper creeper) {
                creeper.setSwellDir(direction);
            }
        };
    }

    /** For monsters that do not swell up at all. */
    static SwellController none() {
        return (mob, direction) -> {
        };
    }
}
