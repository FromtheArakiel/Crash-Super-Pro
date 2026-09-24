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
 * The two payload tags a bomber can carry. They are ordinary entity tags, so they can be inspected
 * with {@code /tag} and are mutually exclusive by construction.
 */
public final class BombTags {
    private BombTags() {
    }

    public static boolean has(Entity entity, String tag) {
        return entity.getTags().contains(tag);
    }

    public static boolean hasTnt(Entity entity) {
        return has(entity, CrashSuperProConfig.phantomTntTag());
    }

    public static boolean hasFireball(Entity entity) {
        return has(entity, CrashSuperProConfig.phantomFireballTag());
    }

    public static boolean hasPotion(Entity entity) {
        return has(entity, CrashSuperProConfig.potionPhantomTag());
    }

    public static boolean hasExplosiveArrows(Entity entity) {
        return has(entity, CrashSuperProConfig.explosiveSkeletonTag());
    }

    public static boolean hasLavaArrows(Entity entity) {
        return has(entity, CrashSuperProConfig.lavaSkeletonTag());
    }

    /** The payloads the two stage bombardment handles. */
    public static boolean hasExplosivePayload(Entity entity) {
        return hasTnt(entity) || hasFireball(entity);
    }

    public static boolean isBomber(Entity entity) {
        return hasExplosivePayload(entity) || hasPotion(entity);
    }

    /** Rolls the three exclusive payload tags for a freshly spawned entity. */
    public static void roll(Entity entity, double tntChance, double fireballChance, double potionChance) {
        if (tntChance > 0.0D && entity.level().random.nextDouble() < tntChance) {
            entity.addTag(CrashSuperProConfig.phantomTntTag());
            return;
        }
        if (fireballChance > 0.0D && entity.level().random.nextDouble() < fireballChance) {
            entity.addTag(CrashSuperProConfig.phantomFireballTag());
            return;
        }
        if (potionChance > 0.0D && entity.level().random.nextDouble() < potionChance) {
            entity.addTag(CrashSuperProConfig.potionPhantomTag());
        }
    }
}
