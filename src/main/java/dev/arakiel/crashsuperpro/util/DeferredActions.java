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

package dev.arakiel.crashsuperpro.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Runs work at the end of the server tick instead of inside an event callback.
 *
 * <p>Detonating another creeper or spawning a new one from inside {@code ExplosionEvent.Start} means
 * re-entering the explosion code while the current explosion is still being set up, which is exactly
 * the kind of nested work that made creepers crash. Everything from that event is therefore queued
 * here and executed a tick later, which is not noticeable in game but keeps the explosion loop clean.
 */
public final class DeferredActions {
    private static final List<Runnable> QUEUE = new ArrayList<>();
    private static boolean draining;

    private DeferredActions() {
    }

    public static void enqueue(Runnable action) {
        QUEUE.add(action);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        drain();
    }

    private static void drain() {
        if (draining || QUEUE.isEmpty()) {
            return;
        }

        draining = true;
        try {
            List<Runnable> batch = new ArrayList<>(QUEUE);
            QUEUE.clear();
            for (Runnable action : batch) {
                try {
                    action.run();
                } catch (RuntimeException ignored) {
                    // A single failing follow up action must never take the server down.
                }
            }
        } finally {
            draining = false;
        }
    }
}
