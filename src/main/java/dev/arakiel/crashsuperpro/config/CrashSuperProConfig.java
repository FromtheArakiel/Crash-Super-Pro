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

    // creeper cat reaction
    private static final ForgeConfigSpec.BooleanValue CREEPER_CAT_CHARGE_ENABLED;
    private static final ForgeConfigSpec.DoubleValue CREEPER_CAT_CHARGE_RADIUS;

    // potion phantom
    private static final ForgeConfigSpec.BooleanValue POTION_PHANTOM_ENABLED;
    private static final ForgeConfigSpec.ConfigValue<String> POTION_PHANTOM_TAG;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_TAG_CHANCE;
    private static final ForgeConfigSpec.IntValue POTION_PHANTOM_BOTTLE_COUNT;
    private static final ForgeConfigSpec.IntValue POTION_PHANTOM_COOLDOWN;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_HUNT_RANGE;
    private static final ForgeConfigSpec.IntValue POTION_PHANTOM_IDLE_TICKS;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_EXPLOSION_POWER;
    private static final ForgeConfigSpec.IntValue POTION_PHANTOM_TARGET_RADIUS;

    // explosive / lava skeleton arrows
    private static final ForgeConfigSpec.BooleanValue EXPLOSIVE_SKELETON_ENABLED;
    private static final ForgeConfigSpec.ConfigValue<String> EXPLOSIVE_SKELETON_TAG;
    private static final ForgeConfigSpec.DoubleValue EXPLOSIVE_SKELETON_SPAWN_CHANCE;
    private static final ForgeConfigSpec.DoubleValue EXPLOSIVE_SKELETON_RIDER_CHANCE;
    private static final ForgeConfigSpec.DoubleValue EXPLOSIVE_SKELETON_POWER;
    private static final ForgeConfigSpec.BooleanValue LAVA_SKELETON_ENABLED;
    private static final ForgeConfigSpec.ConfigValue<String> LAVA_SKELETON_TAG;
    private static final ForgeConfigSpec.DoubleValue LAVA_SKELETON_SPAWN_CHANCE;
    private static final ForgeConfigSpec.DoubleValue LAVA_SKELETON_RIDER_CHANCE;
    private static final ForgeConfigSpec.IntValue LAVA_SKELETON_RADIUS;

    // enderman theft
    private static final ForgeConfigSpec.BooleanValue ENDERMAN_THEFT_ENABLED;
    private static final ForgeConfigSpec.IntValue ENDERMAN_THEFT_RADIUS;
    private static final ForgeConfigSpec.IntValue ENDERMAN_THEFT_DELAY;
    private static final ForgeConfigSpec.DoubleValue ENDERMAN_THEFT_MAX_RESISTANCE;

    // remaining tuning values so nothing is hard coded any more
    private static final ForgeConfigSpec.BooleanValue NO_SELF_DAMAGE;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_HUNT_HEIGHT;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_THROW_SPEED;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_THROW_SPREAD;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_CHASE_SPEED;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_CHARGE_SPEED;
    private static final ForgeConfigSpec.DoubleValue POTION_PHANTOM_CHARGE_DISTANCE;
    private static final ForgeConfigSpec.IntValue POTION_PHANTOM_SEARCH_INTERVAL;
    private static final ForgeConfigSpec.DoubleValue ENDERMAN_THEFT_REACH;
    private static final ForgeConfigSpec.DoubleValue ENDERMAN_THEFT_SPEED;
    private static final ForgeConfigSpec.IntValue ENDERMAN_THEFT_SEARCH_INTERVAL;
    private static final ForgeConfigSpec.IntValue ENDERMAN_THEFT_PARTICLES;
    private static final ForgeConfigSpec.IntValue CREEPER_MOUNT_RECALC_INTERVAL;
    private static final ForgeConfigSpec.DoubleValue CREEPER_MOUNT_FOLLOW_DISTANCE;
    private static final ForgeConfigSpec.IntValue CREEPER_SEEK_SWELL_MAX;
    private static final ForgeConfigSpec.IntValue CREEPER_CAT_CHECK_INTERVAL;
    private static final ForgeConfigSpec.IntValue ARROW_TRAIL_PARTICLES;
    private static final ForgeConfigSpec.IntValue ARROW_IMPACT_PARTICLES;

    private static final ForgeConfigSpec.DoubleValue SPECIAL_SKELETON_TARGET_RANGE;
    private static final ForgeConfigSpec.IntValue SPECIAL_SKELETON_CHECK_INTERVAL;
    private static final ForgeConfigSpec.IntValue SPECIAL_SKELETON_BLOCK_RADIUS;
    private static final ForgeConfigSpec.IntValue SPECIAL_SKELETON_SHOOT_INTERVAL;
    private static final ForgeConfigSpec.DoubleValue SPECIAL_SKELETON_ARROW_SPEED;
    private static final ForgeConfigSpec.DoubleValue SPECIAL_SKELETON_ARROW_SPREAD;

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
        CREEPER_MOUNT_RECALC_INTERVAL = builder
                .comment("Ticks between two path recalculation steps while looking for a spider.")
                .defineInRange("mount_recalc_interval", 10, 1, 200);
        CREEPER_MOUNT_FOLLOW_DISTANCE = builder
                .comment("Distance in blocks the creeper keeps following a spider before giving up.")
                .defineInRange("mount_follow_distance", 10.0D, 1.0D, 64.0D);
        CREEPER_SEEK_SWELL_MAX = builder
                .comment("Upper limit of the swell value the block hunting goal applies.")
                .defineInRange("seek_swell_max", 30, 1, 100);
        builder.pop();

        builder.push("vindicator");
        VINDICATOR_CHICKEN_CHANCE = builder
                .comment("Chance for a spawning vindicator to ride a chicken.")
                .defineInRange("chicken_rider_chance", 0.25D, 0.0D, 1.0D);
        VINDICATOR_NATURAL_ONLY = builder
                .comment("Only naturally spawned vindicators roll for the chicken.")
                .define("natural_spawn_only", false);
        builder.pop();

        builder.push("creeper_cat_reaction");
        CREEPER_CAT_CHARGE_ENABLED = builder
                .comment("A creeper that comes close to a cat or an ocelot turns into a charged creeper.",
                        "The vanilla flee behaviour is removed either way.")
                .define("enabled", true);
        CREEPER_CAT_CHARGE_RADIUS = builder
                .comment("Radius in blocks that is checked for cats and ocelots.")
                .defineInRange("radius", 6.0D, 1.0D, 32.0D);
        CREEPER_CAT_CHECK_INTERVAL = builder
                .comment("Ticks between two cat checks.")
                .defineInRange("check_interval", 10, 1, 200);
        builder.pop();

        builder.push("potion_phantom");
        POTION_PHANTOM_ENABLED = builder
                .comment("Master switch for the potion phantom, a phantom that throws splash potions.")
                .define("enabled", true);
        POTION_PHANTOM_TAG = builder
                .comment("Entity tag that turns a phantom into a potion phantom.")
                .define("tag", "potion");
        POTION_PHANTOM_TAG_CHANCE = builder
                .comment("Chance that a spawned phantom gets the potion tag.")
                .defineInRange("tag_chance", 0.15D, 0.0D, 1.0D);
        POTION_PHANTOM_BOTTLE_COUNT = builder
                .comment("How many splash potions are thrown at once.")
                .defineInRange("bottle_count", 5, 1, 20);
        POTION_PHANTOM_COOLDOWN = builder
                .comment("Ticks between two volleys. 100 ticks are five seconds.")
                .defineInRange("cooldown", 100, 1, 24000);
        POTION_PHANTOM_HUNT_RANGE = builder
                .comment("Radius in blocks that is checked for players to chase.")
                .defineInRange("hunt_range", 16.0D, 1.0D, 256.0D);
        POTION_PHANTOM_IDLE_TICKS = builder
                .comment("Ticks without a player before it charges a valuable block. 100 ticks are five seconds.")
                .defineInRange("idle_ticks_before_charge", 100, 1, 24000);
        POTION_PHANTOM_EXPLOSION_POWER = builder
                .comment("Explosion strength of the charge. 6.0 is a charged creeper.")
                .defineInRange("explosion_power", 6.0D, 0.0D, 64.0D);
        POTION_PHANTOM_TARGET_RADIUS = builder
                .comment("Radius in blocks searched for the valuable block it charges into.")
                .defineInRange("target_search_radius", 24, 1, 128);
        POTION_PHANTOM_HUNT_HEIGHT = builder
                .comment("How far above the player it flies while chasing. Kept low on purpose: 3 to 7 blocks.")
                .defineInRange("hunt_height", 5.0D, 3.0D, 7.0D);
        POTION_PHANTOM_THROW_SPEED = builder
                .comment("Speed of the thrown potions towards the player.")
                .defineInRange("throw_speed", 0.8D, 0.1D, 4.0D);
        POTION_PHANTOM_THROW_SPREAD = builder
                .comment("Random spread of the thrown potions, so a volley does not fly as one line.")
                .defineInRange("throw_spread", 0.1D, 0.0D, 2.0D);
        POTION_PHANTOM_CHASE_SPEED = builder
                .comment("Flight speed while chasing a player.")
                .defineInRange("chase_speed", 1.0D, 0.1D, 5.0D);
        POTION_PHANTOM_CHARGE_SPEED = builder
                .comment("Flight speed while charging a valuable block.")
                .defineInRange("charge_speed", 1.4D, 0.1D, 5.0D);
        POTION_PHANTOM_CHARGE_DISTANCE = builder
                .comment("Distance in blocks at which the charge detonates.")
                .defineInRange("charge_distance", 2.0D, 0.5D, 16.0D);
        POTION_PHANTOM_SEARCH_INTERVAL = builder
                .comment("Ticks between two valuable block searches while idle.")
                .defineInRange("search_interval", 5, 1, 200);
        builder.pop();

        builder.push("explosive_skeleton");
        EXPLOSIVE_SKELETON_ENABLED = builder
                .comment("Skeletons tagged with this shoot arrows that explode on impact.")
                .define("enabled", true);
        EXPLOSIVE_SKELETON_TAG = builder
                .comment("Entity tag for the exploding arrows. Mutually exclusive with the rider tag.")
                .define("tag", "explosive");
        EXPLOSIVE_SKELETON_SPAWN_CHANCE = builder
                .comment("Chance that a naturally spawned skeleton gets the tag.")
                .defineInRange("spawn_chance", 0.10D, 0.0D, 1.0D);
        EXPLOSIVE_SKELETON_RIDER_CHANCE = builder
                .comment("Chance that a skeleton shaped phantom rider gets the tag instead of riding plainly.")
                .defineInRange("rider_chance", 0.05D, 0.0D, 1.0D);
        EXPLOSIVE_SKELETON_POWER = builder
                .comment("Explosion strength of the arrow. 3.0 is a normal creeper.")
                .defineInRange("explosion_power", 3.0D, 0.0D, 64.0D);
        builder.pop();

        builder.push("lava_skeleton");
        LAVA_SKELETON_ENABLED = builder
                .comment("Skeletons tagged with this shoot arrows that leave lava behind on impact.")
                .define("enabled", true);
        LAVA_SKELETON_TAG = builder
                .comment("Entity tag for the lava arrows. Mutually exclusive with the rider tag.")
                .define("tag", "lava");
        LAVA_SKELETON_SPAWN_CHANCE = builder
                .comment("Chance that a naturally spawned skeleton gets the tag.")
                .defineInRange("spawn_chance", 0.10D, 0.0D, 1.0D);
        LAVA_SKELETON_RIDER_CHANCE = builder
                .comment("Chance that a skeleton shaped phantom rider gets the tag instead of riding plainly.")
                .defineInRange("rider_chance", 0.05D, 0.0D, 1.0D);
        LAVA_SKELETON_RADIUS = builder
                .comment("Radius of the lava patch that is placed on impact.")
                .defineInRange("lava_radius", 1, 0, 8);
        ARROW_IMPACT_PARTICLES = builder
                .comment("Firework particles spawned at the impact of both special arrow types.")
                .defineInRange("impact_particle_count", 12, 0, 128);
        ARROW_TRAIL_PARTICLES = builder
                .comment("Firework particles drawn behind a special arrow every tick. 0 disables the trail.")
                .defineInRange("trail_particle_count", 1, 0, 16);
        builder.pop();

        builder.push("special_skeleton");
        SPECIAL_SKELETON_TARGET_RANGE = builder
                .comment("Radius in blocks checked for players, iron golems and villagers.",
                        "Players must be neither creative nor spectators.")
                .defineInRange("target_range", 16.0D, 1.0D, 128.0D);
        SPECIAL_SKELETON_CHECK_INTERVAL = builder
                .comment("Ticks between two target checks.")
                .defineInRange("check_interval", 20, 1, 400);
        SPECIAL_SKELETON_BLOCK_RADIUS = builder
                .comment("Radius in blocks searched for a valuable block when nobody is around.")
                .defineInRange("block_search_radius", 24, 1, 128);
        SPECIAL_SKELETON_SHOOT_INTERVAL = builder
                .comment("Ticks between two arrows fired at a valuable block.")
                .defineInRange("shoot_interval", 40, 1, 400);
        SPECIAL_SKELETON_ARROW_SPEED = builder
                .comment("Arrow speed used when shooting at a block.")
                .defineInRange("arrow_speed", 1.6D, 0.1D, 8.0D);
        SPECIAL_SKELETON_ARROW_SPREAD = builder
                .comment("Arrow inaccuracy used when shooting at a block.")
                .defineInRange("arrow_spread", 2.0D, 0.0D, 20.0D);
        builder.pop();

        builder.push("enderman");
        ENDERMAN_THEFT_ENABLED = builder
                .comment("An idle enderman walks to a valuable block, takes it and leaves dirt behind.",
                        "This is the same block entity lookup the creepers use.")
                .define("theft_enabled", true);
        ENDERMAN_THEFT_RADIUS = builder
                .comment("Radius in blocks an enderman searches for something to take.")
                .defineInRange("theft_search_radius", 16, 1, 128);
        ENDERMAN_THEFT_DELAY = builder
                .comment("Ticks after the theft before the spot turns into dirt. 60 ticks are three seconds.")
                .defineInRange("theft_delay_ticks", 60, 1, 24000);
        ENDERMAN_THEFT_MAX_RESISTANCE = builder
                .comment("A block entity counts as valuable while its explosion resistance is below this.")
                .defineInRange("theft_max_resistance", 30.0D, 0.0D, 1000.0D);
        ENDERMAN_THEFT_REACH = builder
                .comment("Distance in blocks at which the enderman can pick the block up.")
                .defineInRange("theft_reach", 2.5D, 0.5D, 16.0D);
        ENDERMAN_THEFT_SPEED = builder
                .comment("Walking speed used on the way to the block.")
                .defineInRange("theft_speed", 1.0D, 0.1D, 5.0D);
        ENDERMAN_THEFT_SEARCH_INTERVAL = builder
                .comment("Ticks between two valuable block searches, the lookup scans nine chunks.")
                .defineInRange("theft_search_interval", 10, 1, 200);
        ENDERMAN_THEFT_PARTICLES = builder
                .comment("Teleport particles spawned when the carried block turns into dirt.")
                .defineInRange("theft_particle_count", 32, 0, 256);
        builder.pop();

        builder.push("misc");
        NO_SELF_DAMAGE = builder
                .comment("Damage caused by this mod never hurts the one who caused it: explosion",
                        "children, chain detonations and the potion volleys leave their owner alone.")
                .define("no_self_damage", true);
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

    public static boolean creeperCatChargeEnabled() {
        return read(CREEPER_CAT_CHARGE_ENABLED, true);
    }

    public static double creeperCatChargeRadius() {
        return read(CREEPER_CAT_CHARGE_RADIUS, 6.0D);
    }

    public static boolean potionPhantomEnabled() {
        return read(POTION_PHANTOM_ENABLED, true);
    }

    public static String potionPhantomTag() {
        return read(POTION_PHANTOM_TAG, "potion");
    }

    public static double potionPhantomTagChance() {
        return read(POTION_PHANTOM_TAG_CHANCE, 0.15D);
    }

    public static int potionPhantomBottleCount() {
        return read(POTION_PHANTOM_BOTTLE_COUNT, 5);
    }

    public static int potionPhantomCooldown() {
        return read(POTION_PHANTOM_COOLDOWN, 100);
    }

    public static double potionPhantomHuntRange() {
        return read(POTION_PHANTOM_HUNT_RANGE, 16.0D);
    }

    public static int potionPhantomIdleTicks() {
        return read(POTION_PHANTOM_IDLE_TICKS, 100);
    }

    public static double potionPhantomExplosionPower() {
        return read(POTION_PHANTOM_EXPLOSION_POWER, 6.0D);
    }

    public static int potionPhantomTargetRadius() {
        return read(POTION_PHANTOM_TARGET_RADIUS, 24);
    }

    public static boolean explosiveSkeletonEnabled() {
        return read(EXPLOSIVE_SKELETON_ENABLED, true);
    }

    public static String explosiveSkeletonTag() {
        return read(EXPLOSIVE_SKELETON_TAG, "explosive");
    }

    public static double explosiveSkeletonSpawnChance() {
        return read(EXPLOSIVE_SKELETON_SPAWN_CHANCE, 0.10D);
    }

    public static double explosiveSkeletonRiderChance() {
        return read(EXPLOSIVE_SKELETON_RIDER_CHANCE, 0.05D);
    }

    public static double explosiveSkeletonPower() {
        return read(EXPLOSIVE_SKELETON_POWER, 3.0D);
    }

    public static boolean lavaSkeletonEnabled() {
        return read(LAVA_SKELETON_ENABLED, true);
    }

    public static String lavaSkeletonTag() {
        return read(LAVA_SKELETON_TAG, "lava");
    }

    public static double lavaSkeletonSpawnChance() {
        return read(LAVA_SKELETON_SPAWN_CHANCE, 0.10D);
    }

    public static double lavaSkeletonRiderChance() {
        return read(LAVA_SKELETON_RIDER_CHANCE, 0.05D);
    }

    public static int lavaSkeletonRadius() {
        return read(LAVA_SKELETON_RADIUS, 1);
    }

    public static boolean endermanTheftEnabled() {
        return read(ENDERMAN_THEFT_ENABLED, true);
    }

    public static int endermanTheftRadius() {
        return read(ENDERMAN_THEFT_RADIUS, 16);
    }

    public static int endermanTheftDelay() {
        return read(ENDERMAN_THEFT_DELAY, 60);
    }

    public static double endermanTheftMaxResistance() {
        return read(ENDERMAN_THEFT_MAX_RESISTANCE, 30.0D);
    }

    public static boolean noSelfDamage() {
        return read(NO_SELF_DAMAGE, true);
    }

    public static double potionPhantomHuntHeight() {
        return read(POTION_PHANTOM_HUNT_HEIGHT, 5.0D);
    }

    public static double potionPhantomThrowSpeed() {
        return read(POTION_PHANTOM_THROW_SPEED, 0.8D);
    }

    public static double potionPhantomThrowSpread() {
        return read(POTION_PHANTOM_THROW_SPREAD, 0.1D);
    }

    public static double potionPhantomChaseSpeed() {
        return read(POTION_PHANTOM_CHASE_SPEED, 1.0D);
    }

    public static double potionPhantomChargeSpeed() {
        return read(POTION_PHANTOM_CHARGE_SPEED, 1.4D);
    }

    public static double potionPhantomChargeDistance() {
        return read(POTION_PHANTOM_CHARGE_DISTANCE, 2.0D);
    }

    public static int potionPhantomSearchInterval() {
        return read(POTION_PHANTOM_SEARCH_INTERVAL, 5);
    }

    public static double endermanTheftReach() {
        return read(ENDERMAN_THEFT_REACH, 2.5D);
    }

    public static double endermanTheftSpeed() {
        return read(ENDERMAN_THEFT_SPEED, 1.0D);
    }

    public static int endermanTheftSearchInterval() {
        return read(ENDERMAN_THEFT_SEARCH_INTERVAL, 10);
    }

    public static int endermanTheftParticleCount() {
        return read(ENDERMAN_THEFT_PARTICLES, 32);
    }

    public static int creeperMountRecalcInterval() {
        return read(CREEPER_MOUNT_RECALC_INTERVAL, 10);
    }

    public static double creeperMountFollowDistance() {
        return read(CREEPER_MOUNT_FOLLOW_DISTANCE, 10.0D);
    }

    public static int creeperSeekSwellMax() {
        return read(CREEPER_SEEK_SWELL_MAX, 30);
    }

    public static int creeperCatCheckInterval() {
        return read(CREEPER_CAT_CHECK_INTERVAL, 10);
    }

    public static int arrowTrailParticleCount() {
        return read(ARROW_TRAIL_PARTICLES, 1);
    }

    public static int arrowImpactParticleCount() {
        return read(ARROW_IMPACT_PARTICLES, 12);
    }

    public static double specialSkeletonTargetRange() {
        return read(SPECIAL_SKELETON_TARGET_RANGE, 16.0D);
    }

    public static int specialSkeletonCheckInterval() {
        return read(SPECIAL_SKELETON_CHECK_INTERVAL, 20);
    }

    public static int specialSkeletonBlockRadius() {
        return read(SPECIAL_SKELETON_BLOCK_RADIUS, 24);
    }

    public static int specialSkeletonShootInterval() {
        return read(SPECIAL_SKELETON_SHOOT_INTERVAL, 40);
    }

    public static double specialSkeletonArrowSpeed() {
        return read(SPECIAL_SKELETON_ARROW_SPEED, 1.6D);
    }

    public static double specialSkeletonArrowSpread() {
        return read(SPECIAL_SKELETON_ARROW_SPREAD, 2.0D);
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
