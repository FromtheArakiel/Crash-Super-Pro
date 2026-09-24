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

package dev.arakiel.crashsuperpro;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.event.CreeperChainHandler;
import dev.arakiel.crashsuperpro.event.CreeperCombatHandler;
import dev.arakiel.crashsuperpro.event.CreeperSplitHandler;
import dev.arakiel.crashsuperpro.event.ExplosiveArrowHandler;
import dev.arakiel.crashsuperpro.event.MobSpawnHandler;
import dev.arakiel.crashsuperpro.event.SkeletonArrowHandler;
import dev.arakiel.crashsuperpro.event.WitherArrowHandler;
import dev.arakiel.crashsuperpro.platform.DeferredActions;
import dev.arakiel.crashsuperpro.ai.goal.EndermanTheftTracker;
import dev.arakiel.crashsuperpro.platform.DamageImmunity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(CrashSuperPro.MOD_ID)
public final class CrashSuperPro {
    public static final String MOD_ID = "crashsuperpro";

    public CrashSuperPro() {
        // config/crashsuperpro/crashsuperpro-common.toml
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CrashSuperProConfig.SPEC,
                MOD_ID + "/" + MOD_ID + "-common.toml");

        MinecraftForge.EVENT_BUS.register(CreeperCombatHandler.class);
        MinecraftForge.EVENT_BUS.register(CreeperChainHandler.class);
        MinecraftForge.EVENT_BUS.register(CreeperSplitHandler.class);
        MinecraftForge.EVENT_BUS.register(MobSpawnHandler.class);
        MinecraftForge.EVENT_BUS.register(SkeletonArrowHandler.class);
        MinecraftForge.EVENT_BUS.register(WitherArrowHandler.class);
        MinecraftForge.EVENT_BUS.register(DeferredActions.class);
        MinecraftForge.EVENT_BUS.register(EndermanTheftTracker.class);
        MinecraftForge.EVENT_BUS.register(ExplosiveArrowHandler.class);
        MinecraftForge.EVENT_BUS.register(DamageImmunity.class);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
