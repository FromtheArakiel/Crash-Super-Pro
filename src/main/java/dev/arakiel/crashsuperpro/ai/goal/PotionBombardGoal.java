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

import java.util.EnumSet;

import dev.arakiel.crashsuperpro.config.CrashSuperProConfig;
import dev.arakiel.crashsuperpro.tags.BombTags;
import dev.arakiel.crashsuperpro.platform.DamageImmunity;
import dev.arakiel.crashsuperpro.world.TargetFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

/**
 * The exclusive AI of a potion phantom.
 *
 * <p>It only ever chases players and throws a volley of splash potions of Instant Damage while it
 * does. As soon as no player is left for a while it charges the closest valuable block and goes off
 * with the strength of a charged creeper.
 */
public class PotionBombardGoal extends Goal {
    private final Phantom phantom;
    private int cooldown;
    private int idleTicks;
    private BlockPos chargeTarget;

    public PotionBombardGoal(Phantom phantom) {
        this.phantom = phantom;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return CrashSuperProConfig.potionPhantomEnabled()
                && BombTags.hasPotion(this.phantom)
                // The three payload types are mutually exclusive, this is a second safety net.
                && !BombTags.hasExplosivePayload(this.phantom)
                && !this.phantom.level().isClientSide();
    }

    @Override
    public boolean canContinueToUse() {
        return CrashSuperProConfig.potionPhantomEnabled()
                && BombTags.hasPotion(this.phantom)
                && !BombTags.hasExplosivePayload(this.phantom);
    }

    @Override
    public void tick() {
        Player player = TargetFinder.findNearestPlayer(this.phantom, CrashSuperProConfig.potionPhantomHuntRange());
        if (player != null) {
            this.idleTicks = 0;
            this.chargeTarget = null;
            chaseAndThrow(player);
            return;
        }

        if (++this.idleTicks < CrashSuperProConfig.potionPhantomIdleTicks()) {
            return;
        }

        if (this.chargeTarget == null) {
            if (this.idleTicks % CrashSuperProConfig.potionPhantomSearchInterval() != 0) {
                return;
            }
            this.chargeTarget = TargetFinder.findNearestExplodable(this.phantom,
                    CrashSuperProConfig.potionPhantomTargetRadius(), CrashSuperProConfig.bombardmentMaxResistance());
            if (this.chargeTarget == null) {
                this.idleTicks = 0;
                return;
            }
        }

        flyToCharge();
    }

    private void chaseAndThrow(Player player) {
        // Low level chase, and high enough that it does not catch its own splash potions.
        this.phantom.getMoveControl().setWantedPosition(player.getX(),
                player.getY() + CrashSuperProConfig.potionPhantomHuntHeight(), player.getZ(),
                CrashSuperProConfig.potionPhantomChaseSpeed());
        this.phantom.getLookControl().setLookAt(player, 30.0F, 30.0F);

        if (--this.cooldown <= 0) {
            this.cooldown = CrashSuperProConfig.potionPhantomCooldown();
            throwVolley(player);
        }
    }

    /** Throws the volley at the player, not straight down. */
    private void throwVolley(Player player) {
        Level level = this.phantom.level();
        // Its own volley must never splash back onto the phantom.
        DamageImmunity.protectFromOwnPotions(this.phantom);
        int count = CrashSuperProConfig.potionPhantomBottleCount();

        for (int i = 0; i < count; i++) {
            ThrownPotion potion = new ThrownPotion(level, this.phantom);
            potion.setItem(splashPotion());
            potion.setPos(this.phantom.getX(), this.phantom.getY() - 0.5D, this.phantom.getZ());

            double dx = player.getX() - this.phantom.getX();
            double dy = player.getY() + 1.0D - (this.phantom.getY() - 0.5D);
            double dz = player.getZ() - this.phantom.getZ();
            double horizontal = Math.max(Math.sqrt(dx * dx + dz * dz), 1.0D);
            double speed = CrashSuperProConfig.potionPhantomThrowSpeed();
            double spread = CrashSuperProConfig.potionPhantomThrowSpread();

            potion.setDeltaMovement(
                    dx / horizontal * speed + (level.random.nextDouble() - 0.5D) * spread,
                    dy / horizontal * speed + 0.15D,
                    dz / horizontal * speed + (level.random.nextDouble() - 0.5D) * spread);
            level.addFreshEntity(potion);
        }
    }

    private static ItemStack splashPotion() {
        ItemStack stack = new ItemStack(Items.SPLASH_POTION);
        return PotionUtils.setPotion(stack, Potions.STRONG_HARMING);
    }

    private void flyToCharge() {
        BlockPos target = this.chargeTarget;
        if (target == null) {
            return;
        }

        double x = target.getX() + 0.5D;
        double z = target.getZ() + 0.5D;
        this.phantom.getMoveControl().setWantedPosition(x, target.getY() + 1.0D, z,
                CrashSuperProConfig.potionPhantomChargeSpeed());
        this.phantom.getLookControl().setLookAt(x, target.getY(), z, 30.0F, 30.0F);

        double distance = CrashSuperProConfig.potionPhantomChargeDistance();
        if (this.phantom.distanceToSqr(x, this.phantom.getY(), z) < distance * distance) {
            detonate();
        }
    }

    private void detonate() {
        this.phantom.level().explode(this.phantom, this.phantom.getX(), this.phantom.getY(), this.phantom.getZ(),
                (float) CrashSuperProConfig.potionPhantomExplosionPower(), Level.ExplosionInteraction.MOB);
        this.phantom.discard();
        this.chargeTarget = null;
        this.idleTicks = 0;
    }

    @Override
    public void stop() {
        this.chargeTarget = null;
    }
}
