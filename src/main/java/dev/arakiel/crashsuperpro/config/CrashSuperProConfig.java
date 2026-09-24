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

package dev.arakiel.crashsuperpro.config;

import net.minecraftforge.common.ForgeConfigSpec;

/** Every mechanic that was added on top of the original Super-Creeper behaviour lives in here. */
public final class CrashSuperProConfig {
    public static final ForgeConfigSpec SPEC;

    private static final ForgeConfigSpec.BooleanValue PHANTOM_NATURAL_ONLY;
    private static final ForgeConfigSpec.ConfigValue<String> PHANTOM_RIDER_TAG;
    private static final ForgeConfigSpec.BooleanValue PHANTOM_RIDER_PERSISTENCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_WITHER_SKELETON_CHANCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_SKELETON_CHANCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_PILLAGER_CHANCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_EVOKER_CHANCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_TNT_TAG_CHANCE;
    private static final ForgeConfigSpec.DoubleValue PHANTOM_FIREBALL_TAG_CHANCE;
    private static final ForgeConfigSpec.ConfigValue<String> PHANTOM_TNT_TAG_NAME;
    private static final ForgeConfigSpec.ConfigValue<String> PHANTOM_FIREBALL_TAG_NAME;

    private static final ForgeConfigSpec.BooleanValue WITHER_ARROWS_ENABLED;
    private static final ForgeConfigSpec.BooleanValue WITHER_ARROWS_RIDER_ONLY;
    private static final ForgeConfigSpec.DoubleValue WITHER_ARROW_WITHER_SECONDS;
    private static final ForgeConfigSpec.IntValue WITHER_ARROW_WITHER_LEVEL;
    private static final ForgeConfigSpec.DoubleValue WITHER_ARROW_BLINDNESS_SECONDS;
    private static final ForgeConfigSpec.IntValue WITHER_ARROW_BLINDNESS_LEVEL;
    private static final ForgeConfigSpec.DoubleValue WITHER_ARROW_FIRE_SECONDS;

    private static final ForgeConfigSpec.BooleanValue SKELETON_ARROWS_ENABLED;
    private static final ForgeConfigSpec.BooleanValue SKELETON_ARROWS_RIDER_ONLY;
    private static final ForgeConfigSpec.IntValue SKELETON_ARROW_INSTANT_DAMAGE_LEVEL;

    private static final ForgeConfigSpec.BooleanValue CREEPER_SPLIT_ENABLED;
    private static final ForgeConfigSpec.DoubleValue CREEPER_SPLIT_CHANCE;
    private static final ForgeConfigSpec.BooleanValue CREEPER_SPLIT_NATURAL_ONLY;
    private static final ForgeConfigSpec.ConfigValue<String> CREEPER_SPLIT_TAG;
    private static final ForgeConfigSpec.IntValue CREEPER_SPLIT_COUNT;
    private static final ForgeConfigSpec.DoubleValue CREEPER_SPLIT_SPAWN_RADIUS;
    private static final ForgeConfigSpec.IntValue CREEPER_SPLIT_DETONATION_TICKS;

    private static final ForgeConfigSpec.DoubleValue CREEPER_MOUNT_SPEED;
    private static final ForgeConfigSpec.DoubleValue CREEPER_MOUNT_SEARCH_RADIUS;
    private static final ForgeConfigSpec.DoubleValue CREEPER_MOUNT_DISTANCE;
    private static final ForgeConfigSpec.IntValue CREEPER_SEEK_RADIUS;
    private static final ForgeConfigSpec.DoubleValue CREEPER_SEEK_SPEED;
    private static final ForgeConfigSpec.DoubleValue CREEPER_SEEK_MAX_RESISTANCE;
    private static final ForgeConfigSpec.BooleanValue CREEPER_OPEN_DOORS;

    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_PREFER_VALUABLE;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_ARRIVAL_DISTANCE;
    private static final ForgeConfigSpec.IntValue BOMBARDMENT_TNT_FUSE;
    private static final ForgeConfigSpec.IntValue BOMBARDMENT_FIREBALL_POWER;
    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_LARGE_FIREBALL;

    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_HUNT_RANGE;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_HUNT_HEIGHT;
    private static final ForgeConfigSpec.IntValue BOMBARDMENT_HUNT_DROP_INTERVAL;
    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_HUNT_PLAYERS;
    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_HUNT_IRON_GOLEMS;
    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_HUNT_VILLAGERS;

    private static final ForgeConfigSpec.BooleanValue BOMBARDMENT_ENABLED;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_CHANCE;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_RANGE;
    private static final ForgeConfigSpec.IntValue BOMBARDMENT_TARGET_RADIUS;
    private static final ForgeConfigSpec.IntValue BOMBARDMENT_COOLDOWN;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_MAX_RESISTANCE;
    private static final ForgeConfigSpec.DoubleValue BOMBARDMENT_HEIGHT;

    private static final ForgeConfigSpec.BooleanValue CHAIN_EXPLOSION_ENABLED;
    private static final ForgeConfigSpec.DoubleValue CHAIN_EXPLOSION_RADIUS;

    private static final ForgeConfigSpec.DoubleValue VINDICATOR_CHICKEN_CHANCE;
    private static final ForgeConfigSpec.BooleanValue VINDICATOR_NATURAL_ONLY;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("phantom_spawn");
        PHANTOM_NATURAL_ONLY = builder
                .comment("Only phantoms that are spawned naturally roll the events below.")
                .define("natural_spawn_only", true);
        PHANTOM_RIDER_TAG = builder
                .comment("Entity tag put on a phantom rider. The arrow effects look for it.")
                .define("rider_tag", "phantom_rider");
        PHANTOM_RIDER_PERSISTENCE = builder
                .comment("Riders are marked as persistent so they do not despawn.")
                .define("rider_persistence_required", true);
        PHANTOM_WITHER_SKELETON_CHANCE = builder
                .comment("Chance for a naturally spawned phantom to carry a bow wielding wither skeleton.")
                .defineInRange("wither_skeleton_chance", 0.05D, 0.0D, 1.0D);
        PHANTOM_SKELETON_CHANCE = builder
                .comment("Chance for a naturally spawned phantom to carry a skeleton.",
                        "Rolled after the wither skeleton chance, so all rider chances are exclusive.")
                .defineInRange("skeleton_chance", 0.10D, 0.0D, 1.0D);
        PHANTOM_PILLAGER_CHANCE = builder
                .comment("Chance for a naturally spawned phantom to carry a pillager.",
                        "Rolled after the skeleton chance.")
                .defineInRange("pillager_chance", 0.08D, 0.0D, 1.0D);
        PHANTOM_EVOKER_CHANCE = builder
                .comment("Chance for a naturally spawned phantom to carry an evoker.",
                        "Rolled after the pillager chance.")
                .defineInRange("evoker_chance", 0.04D, 0.0D, 1.0D);
        PHANTOM_TNT_TAG_CHANCE = builder
                .comment("Chance for a spawned phantom to carry the TNT payload tag.")
                .defineInRange("tnt_tag_chance", 0.15D, 0.0D, 1.0D);
        PHANTOM_FIREBALL_TAG_CHANCE = builder
                .comment("Chance for a spawned phantom to carry the fireball payload tag.",
                        "Only rolled when the TNT tag was not given, so the two stay exclusive.")
                .defineInRange("fireball_tag_chance", 0.15D, 0.0D, 1.0D);
        PHANTOM_TNT_TAG_NAME = builder
                .comment("Name of the TNT payload tag.")
                .define("tnt_tag", "tnt");
        PHANTOM_FIREBALL_TAG_NAME = builder
                .comment("Name of the fireball payload tag.")
                .define("fireball_tag", "fireball");
        builder.pop();

        builder.push("wither_skeleton_arrows");
        WITHER_ARROWS_ENABLED = builder
                .comment("Arrows shot by a wither skeleton that rides a phantom apply wither and blindness",
                        "and set the target on fire.")
                .define("enabled", true);
        WITHER_ARROWS_RIDER_ONLY = builder
                .comment("Only wither skeletons carrying the rider tag are affected.")
                .define("rider_only", true);
        WITHER_ARROW_WITHER_SECONDS = builder
                .comment("Wither duration in seconds. 0 disables this part.")
                .defineInRange("wither_seconds", 5.0D, 0.0D, 600.0D);
        WITHER_ARROW_WITHER_LEVEL = builder
                .comment("Wither level, 1 means Wither I.")
                .defineInRange("wither_level", 1, 1, 10);
        WITHER_ARROW_BLINDNESS_SECONDS = builder
                .comment("Blindness duration in seconds. 0 disables this part.")
                .defineInRange("blindness_seconds", 5.0D, 0.0D, 600.0D);
        WITHER_ARROW_BLINDNESS_LEVEL = builder
                .comment("Blindness level, 1 means Blindness I.")
                .defineInRange("blindness_level", 1, 1, 10);
        WITHER_ARROW_FIRE_SECONDS = builder
                .comment("Seconds the target burns for. 0 disables this part.")
                .defineInRange("fire_seconds", 5.0D, 0.0D, 600.0D);
        builder.pop();

        builder.push("skeleton_arrows");
        SKELETON_ARROWS_ENABLED = builder
                .comment("Arrows shot by a skeleton that rides a phantom deal instant damage.")
                .define("enabled", true);
        SKELETON_ARROWS_RIDER_ONLY = builder
                .comment("Only skeletons carrying the rider tag are affected.")
                .define("rider_only", true);
        SKELETON_ARROW_INSTANT_DAMAGE_LEVEL = builder
                .comment("Level of the instant damage effect, 2 means Instant Damage II.")
                .defineInRange("instant_damage_level", 2, 1, 10);
        builder.pop();

        builder.push("bombardment");
        BOMBARDMENT_ENABLED = builder
                .comment("Master switch for the phantom bombardment.")
                .define("enabled", true);
        BOMBARDMENT_CHANCE = builder
                .comment("Chance that a bomber phantom actually drops its payload when it reaches the spot.")
                .defineInRange("chance", 1.0D, 0.0D, 1.0D);
        BOMBARDMENT_RANGE = builder
                .comment("Radius in blocks that is checked for living players. A phantom only bombs when it",
                        "finds none, which keeps the vanilla player hunt in charge.")
                .defineInRange("range", 32.0D, 1.0D, 256.0D);
        BOMBARDMENT_TARGET_RADIUS = builder
                .comment("Radius in blocks that is searched for a valuable target using the creeper logic,",
                        "and also the radius used to pick a random spot when nothing valuable is around.")
                .defineInRange("target_search_radius", 24, 1, 128);
        BOMBARDMENT_COOLDOWN = builder
                .comment("Ticks between two drops of the same phantom.")
                .defineInRange("cooldown", 120, 1, 24000);
        BOMBARDMENT_MAX_RESISTANCE = builder
                .comment("A block entity counts as a valuable target while its explosion resistance is below this.")
                .defineInRange("max_target_resistance", 30.0D, 0.0D, 1000.0D);
        BOMBARDMENT_HEIGHT = builder
                .comment("How far above the target a phantom hovers before it drops its payload.")
                .defineInRange("hover_height", 6.0D, 0.0D, 64.0D);
        BOMBARDMENT_PREFER_VALUABLE = builder
                .comment("Look for a valuable target first, false always uses a random spot.")
                .define("prefer_valuable_targets", true);
        BOMBARDMENT_ARRIVAL_DISTANCE = builder
                .comment("The drop happens once the phantom is this close to the target.")
                .defineInRange("arrival_distance", 4.0D, 0.5D, 32.0D);
        BOMBARDMENT_TNT_FUSE = builder
                .comment("Fuse of the dropped TNT in ticks.")
                .defineInRange("tnt_fuse_ticks", 80, 1, 2000);
        BOMBARDMENT_FIREBALL_POWER = builder
                .comment("Explosion power of the dropped fireball.")
                .defineInRange("fireball_explosion_power", 1, 0, 10);
        BOMBARDMENT_LARGE_FIREBALL = builder
                .comment("Use a large (ghast style) fireball, false uses a small (blaze style) one.")
                .define("use_large_fireball", true);
        BOMBARDMENT_HUNT_RANGE = builder
                .comment("Stage one search radius in blocks. A bomber chases the closest target it finds here.")
                .defineInRange("hunt_range", 32.0D, 1.0D, 256.0D);
        BOMBARDMENT_HUNT_HEIGHT = builder
                .comment("How far above its prey a chasing phantom stays.")
                .defineInRange("hunt_height", 3.0D, 0.0D, 64.0D);
        BOMBARDMENT_HUNT_DROP_INTERVAL = builder
                .comment("Ticks between two drops while chasing. 100 ticks are five seconds.")
                .defineInRange("hunt_drop_interval", 100, 1, 24000);
        BOMBARDMENT_HUNT_PLAYERS = builder
                .comment("Survival and adventure players count as stage one targets.")
                .define("hunt_players", true);
        BOMBARDMENT_HUNT_IRON_GOLEMS = builder
                .comment("Iron golems count as stage one targets.")
                .define("hunt_iron_golems", true);
        BOMBARDMENT_HUNT_VILLAGERS = builder
                .comment("Villagers count as stage one targets.")
                .define("hunt_villagers", true);
        builder.pop();

        builder.push("creeper");
        CHAIN_EXPLOSION_ENABLED = builder
                .comment("An exploding creeper instantly detonates every other creeper in range.")
                .define("chain_explosion_enabled", true);
        CHAIN_EXPLOSION_RADIUS = builder
                .comment("Radius in blocks that is checked for other creepers.")
                .defineInRange("chain_explosion_radius", 6.0D, 0.0D, 64.0D);
        CREEPER_SPLIT_ENABLED = builder
                .comment("A creeper carrying the split tag spawns more creepers when it dies.",
                        "The spawned creepers are detonated right away and never carry the tag themselves.")
                .define("split_enabled", true);
        CREEPER_SPLIT_CHANCE = builder
                .comment("Chance that a spawning creeper is given the split tag.")
                .defineInRange("split_chance", 0.10D, 0.0D, 1.0D);
        CREEPER_SPLIT_NATURAL_ONLY = builder
                .comment("Only naturally spawned creepers roll for the split tag.")
                .define("split_natural_spawn_only", true);
        CREEPER_SPLIT_TAG = builder
                .comment("The entity tag that marks a splitting creeper.")
                .define("split_tag", "split");
        CREEPER_SPLIT_COUNT = builder
                .comment("How many creepers a splitting creeper spawns.")
                .defineInRange("split_count", 2, 1, 10);
        CREEPER_SPLIT_SPAWN_RADIUS = builder
                .comment("Distance in blocks from the dead creeper the children are placed at.")
                .defineInRange("split_spawn_radius", 0.9D, 0.0D, 8.0D);
        CREEPER_SPLIT_DETONATION_TICKS = builder
                .comment("Ticks a spawned child waits before it detonates. 1 means it goes off right away.")
                .defineInRange("split_detonation_ticks", 1, 1, 29);
        builder.pop();

        builder.push("creeper_behaviour");
        CREEPER_MOUNT_SPEED = builder
                .comment("Walking speed used while a creeper looks for a spider to ride.")
                .defineInRange("mount_speed", 1.1D, 0.1D, 5.0D);
        CREEPER_MOUNT_SEARCH_RADIUS = builder
                .comment("Radius in blocks a creeper searches for a free spider.")
                .defineInRange("mount_search_radius", 16.0D, 1.0D, 64.0D);
        CREEPER_MOUNT_DISTANCE = builder
                .comment("Distance in blocks at which the creeper climbs onto the spider.")
                .defineInRange("mount_distance", 2.25D, 0.5D, 16.0D);
        CREEPER_SEEK_RADIUS = builder
                .comment("Radius in blocks a creeper searches for a valuable block entity.")
                .defineInRange("seek_radius", 24, 1, 128);
        CREEPER_SEEK_SPEED = builder
                .comment("Walking speed used while walking to that block entity.")
                .defineInRange("seek_speed", 1.3D, 0.1D, 5.0D);
        CREEPER_SEEK_MAX_RESISTANCE = builder
                .comment("A block entity counts as a target while its explosion resistance is below this.")
                .defineInRange("seek_max_resistance", 30.0D, 0.0D, 1000.0D);
        CREEPER_OPEN_DOORS = builder
                .comment("Creepers can open wooden doors while hunting, as in the original mod.")
                .define("open_doors", true);
        builder.pop();

        builder.push("vindicator");
        VINDICATOR_CHICKEN_CHANCE = builder
                .comment("Chance for a spawning vindicator to ride a chicken.")
                .defineInRange("chicken_rider_chance", 0.25D, 0.0D, 1.0D);
        VINDICATOR_NATURAL_ONLY = builder
                .comment("Only naturally spawned vindicators roll for the chicken.")
                .define("natural_spawn_only", false);
        builder.pop();

        SPEC = builder.build();
    }

    private CrashSuperProConfig() {
    }

    public static boolean phantomNaturalOnly() {
        return read(PHANTOM_NATURAL_ONLY, true);
    }

    public static String phantomRiderTag() {
        return read(PHANTOM_RIDER_TAG, "phantom_rider");
    }

    public static boolean phantomRiderPersistenceRequired() {
        return read(PHANTOM_RIDER_PERSISTENCE, true);
    }

    public static double phantomWitherSkeletonChance() {
        return read(PHANTOM_WITHER_SKELETON_CHANCE, 0.05D);
    }

    public static double phantomSkeletonChance() {
        return read(PHANTOM_SKELETON_CHANCE, 0.10D);
    }

    public static double phantomPillagerChance() {
        return read(PHANTOM_PILLAGER_CHANCE, 0.08D);
    }

    public static double phantomEvokerChance() {
        return read(PHANTOM_EVOKER_CHANCE, 0.04D);
    }

    public static double phantomTntTagChance() {
        return read(PHANTOM_TNT_TAG_CHANCE, 0.15D);
    }

    public static double phantomFireballTagChance() {
        return read(PHANTOM_FIREBALL_TAG_CHANCE, 0.15D);
    }

    public static String phantomTntTag() {
        return read(PHANTOM_TNT_TAG_NAME, "tnt");
    }

    public static String phantomFireballTag() {
        return read(PHANTOM_FIREBALL_TAG_NAME, "fireball");
    }

    public static boolean witherArrowsEnabled() {
        return read(WITHER_ARROWS_ENABLED, true);
    }

    public static boolean witherArrowsRiderOnly() {
        return read(WITHER_ARROWS_RIDER_ONLY, true);
    }

    public static double witherArrowWitherSeconds() {
        return read(WITHER_ARROW_WITHER_SECONDS, 5.0D);
    }

    public static int witherArrowWitherLevel() {
        return read(WITHER_ARROW_WITHER_LEVEL, 1);
    }

    public static double witherArrowBlindnessSeconds() {
        return read(WITHER_ARROW_BLINDNESS_SECONDS, 5.0D);
    }

    public static int witherArrowBlindnessLevel() {
        return read(WITHER_ARROW_BLINDNESS_LEVEL, 1);
    }

    public static double witherArrowFireSeconds() {
        return read(WITHER_ARROW_FIRE_SECONDS, 5.0D);
    }

    public static boolean skeletonArrowsEnabled() {
        return read(SKELETON_ARROWS_ENABLED, true);
    }

    public static boolean skeletonArrowsRiderOnly() {
        return read(SKELETON_ARROWS_RIDER_ONLY, true);
    }

    public static int skeletonArrowInstantDamageLevel() {
        return read(SKELETON_ARROW_INSTANT_DAMAGE_LEVEL, 2);
    }

    public static boolean bombardmentEnabled() {
        return read(BOMBARDMENT_ENABLED, true);
    }

    public static double bombardmentChance() {
        return read(BOMBARDMENT_CHANCE, 1.0D);
    }

    public static double bombardmentRange() {
        return read(BOMBARDMENT_RANGE, 32.0D);
    }

    public static int bombardmentTargetRadius() {
        return read(BOMBARDMENT_TARGET_RADIUS, 24);
    }

    public static int bombardmentCooldown() {
        return read(BOMBARDMENT_COOLDOWN, 120);
    }

    public static double bombardmentMaxResistance() {
        return read(BOMBARDMENT_MAX_RESISTANCE, 30.0D);
    }

    public static double bombardmentHoverHeight() {
        return read(BOMBARDMENT_HEIGHT, 6.0D);
    }

    public static boolean bombardmentPreferValuableTargets() {
        return read(BOMBARDMENT_PREFER_VALUABLE, true);
    }

    public static double bombardmentArrivalDistance() {
        return read(BOMBARDMENT_ARRIVAL_DISTANCE, 4.0D);
    }

    public static int bombardmentTntFuse() {
        return read(BOMBARDMENT_TNT_FUSE, 80);
    }

    public static int bombardmentFireballPower() {
        return read(BOMBARDMENT_FIREBALL_POWER, 1);
    }

    public static boolean bombardmentLargeFireball() {
        return read(BOMBARDMENT_LARGE_FIREBALL, true);
    }

    public static double bombardmentHuntRange() {
        return read(BOMBARDMENT_HUNT_RANGE, 32.0D);
    }

    public static double bombardmentHuntHeight() {
        return read(BOMBARDMENT_HUNT_HEIGHT, 3.0D);
    }

    public static int bombardmentHuntDropInterval() {
        return read(BOMBARDMENT_HUNT_DROP_INTERVAL, 100);
    }

    public static boolean bombardmentHuntPlayers() {
        return read(BOMBARDMENT_HUNT_PLAYERS, true);
    }

    public static boolean bombardmentHuntIronGolems() {
        return read(BOMBARDMENT_HUNT_IRON_GOLEMS, true);
    }

    public static boolean bombardmentHuntVillagers() {
        return read(BOMBARDMENT_HUNT_VILLAGERS, true);
    }

    public static boolean chainExplosionEnabled() {
        return read(CHAIN_EXPLOSION_ENABLED, true);
    }

    public static double chainExplosionRadius() {
        return read(CHAIN_EXPLOSION_RADIUS, 6.0D);
    }

    public static boolean creeperSplitEnabled() {
        return read(CREEPER_SPLIT_ENABLED, true);
    }

    public static double creeperSplitChance() {
        return read(CREEPER_SPLIT_CHANCE, 0.10D);
    }

    public static boolean creeperSplitNaturalOnly() {
        return read(CREEPER_SPLIT_NATURAL_ONLY, true);
    }

    public static String creeperSplitTag() {
        return read(CREEPER_SPLIT_TAG, "split");
    }

    public static int creeperSplitCount() {
        return read(CREEPER_SPLIT_COUNT, 2);
    }

    public static double creeperSplitSpawnRadius() {
        return read(CREEPER_SPLIT_SPAWN_RADIUS, 0.9D);
    }

    public static int creeperSplitDetonationTicks() {
        return read(CREEPER_SPLIT_DETONATION_TICKS, 1);
    }

    public static double creeperMountSpeed() {
        return read(CREEPER_MOUNT_SPEED, 1.1D);
    }

    public static double creeperMountSearchRadius() {
        return read(CREEPER_MOUNT_SEARCH_RADIUS, 16.0D);
    }

    public static double creeperMountDistance() {
        return read(CREEPER_MOUNT_DISTANCE, 2.25D);
    }

    public static int creeperSeekRadius() {
        return read(CREEPER_SEEK_RADIUS, 24);
    }

    public static double creeperSeekSpeed() {
        return read(CREEPER_SEEK_SPEED, 1.3D);
    }

    public static double creeperSeekMaxResistance() {
        return read(CREEPER_SEEK_MAX_RESISTANCE, 30.0D);
    }

    public static boolean creeperOpenDoors() {
        return read(CREEPER_OPEN_DOORS, true);
    }

    public static double vindicatorChickenChance() {
        return read(VINDICATOR_CHICKEN_CHANCE, 0.25D);
    }

    public static boolean vindicatorNaturalOnly() {
        return read(VINDICATOR_NATURAL_ONLY, false);
    }

    /*
     * The goals and the spawn hook can run before the config file has been read; falling back to the
     * documented default keeps that safe.
     */
    private static int read(ForgeConfigSpec.IntValue value, int fallback) {
        try {
            return value.get();
        } catch (IllegalStateException notLoadedYet) {
            return fallback;
        }
    }

    private static double read(ForgeConfigSpec.DoubleValue value, double fallback) {
        try {
            return value.get();
        } catch (IllegalStateException notLoadedYet) {
            return fallback;
        }
    }

    private static boolean read(ForgeConfigSpec.BooleanValue value, boolean fallback) {
        try {
            return value.get();
        } catch (IllegalStateException notLoadedYet) {
            return fallback;
        }
    }

    private static String read(ForgeConfigSpec.ConfigValue<String> value, String fallback) {
        try {
            String configured = value.get();
            return configured == null || configured.isBlank() ? fallback : configured;
        } catch (IllegalStateException notLoadedYet) {
            return fallback;
        }
    }
}
