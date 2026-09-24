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

package dev.arakiel.crashsuperpro.platform;

import java.io.PrintStream;

/**
 * Writes diagnostics without touching log4j.
 *
 * <p>Forge reports an exception thrown by an event handler through its own logger. When the log4j
 * setup of the instance is broken that logging call fails with a {@code LinkageError} and the game
 * dies with the real exception hidden. Every handler in this mod catches its own failures and reports
 * them here, so a broken log4j can never turn a handled problem into a crash again.
 */
public final class SafeLog {
    private static final String PREFIX = "[CrashSuperPro] ";

    private SafeLog() {
    }

    public static void error(String message, Throwable throwable) {
        PrintStream stream = System.err;
        try {
            stream.println(PREFIX + message);
            if (throwable != null) {
                stream.println(PREFIX + throwable);
                throwable.printStackTrace(stream);
            }
        } catch (Throwable ignored) {
            // Reporting must never be the thing that breaks the game.
        }
    }
}
