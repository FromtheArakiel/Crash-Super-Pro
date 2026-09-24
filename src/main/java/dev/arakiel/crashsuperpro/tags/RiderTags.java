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

package dev.arakiel.crashsuperpro.tags;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import net.minecraft.world.entity.Entity;

/**
 * Marks the monsters that were spawned as a phantom rider. The arrow effects look for this tag, so
 * only our riders get them - an ordinary skeleton or wither skeleton is left alone.
 */
public final class RiderTags {
    private RiderTags() {
    }

    public static void markPhantomRider(Entity rider) {
        rider.addTag(CrashSuperProConfig.phantomRiderTag());
    }

    public static boolean isPhantomRider(Entity entity) {
        return entity.getTags().contains(CrashSuperProConfig.phantomRiderTag());
    }
}
