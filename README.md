# Crash Super Pro

Crash Super Pro is a monster overhaul for **Minecraft 1.20.1 / Forge**, and a rewrite of the mod
*Super-Creeper* by First-sight. The original behaviour is preserved; the code was re-organised into
reusable pieces and new mechanics were added.

## Creepers

* A creeper that is already swelling cannot be killed - a killing blow only heals it by one and
  keeps it swelling.
* The same goes for fall damage while it is swelling.
* When a creeper is hurt by another creeper it swells up and is flagged, so it keeps moving.
* Creepers walk to a free spider nearby and ride it, and they open doors while hunting.
* Creepers search the block entities around them and blow up the ones that are not explosion proof.
* Dragons... well, creepers: an exploding creeper instantly detonates every other creeper in range,
  which makes chain reactions the new normal.
* Spawning creepers have a chance to be given the `split` tag (10% by default). A creeper carrying it
  spawns two more creepers when it dies - and those two are pushed right to the brink of detonation.
  The children never carry the tag, so the splitting stops after one generation.
* Creepers no longer flee from cats and ocelots. Coming close to one charges them into a lightning
  creeper instead.
* Chain detonation has the highest priority: if a detonating creeper finds other creepers in range,
  its own blast is skipped and only the neighbours go off. A creeper can therefore never explode
  twice, which used to end in a second, empty explosion.

## Phantoms

* Naturally spawned phantoms are rolled into one of five mutually exclusive outcomes: a wither
  skeleton with a bow, a plain skeleton, a pillager or an evoker riding the phantom, or a completely
  normal spawn.
* Arrows shot by the **wither skeleton rider** apply wither and blindness and set the victim on fire -
  all three configurable and five seconds by default. Arrows shot by an ordinary wither skeleton are
  left untouched.
* Arrows shot by the **skeleton rider** apply Instant Damage II (the level is configurable).
* A spawned phantom may additionally carry the `tnt` tag or the `fireball` tag (never both).
* The AI of a **tagged** phantom is rewritten and works in two stages. Stage one looks for a real
  player (creative and spectator are ignored), an iron golem or a villager within 32 blocks and
  chases the closest one, dropping its payload straight below itself every five seconds while it
  chases. Stage two takes over when nobody is left: the phantom flies to a valuable target - the same
  block entity logic the creepers use - or to a random spot and bombs it. A phantom **without** a
  payload tag never touches this AI and keeps the vanilla behaviour.
* Explosion follow ups (chain detonation and split children) run at the end of the server tick, so
  nothing re-enters the explosion code from inside its own event.
* A third phantom type carries the `potion` tag. It has its own AI: it only chases players and throws
  a volley of five splash potions of Instant Damage II at the player every five seconds
  (configurable), watching a 16 block radius. The three payload tags are mutually exclusive and a
  potion phantom never runs the vanilla AI. Once no player is around for five seconds it charges the
  closest valuable block and detonates with the strength of a charged creeper.
* The potion phantom chases at low altitude, 3 to 7 blocks above the player, and holds that distance
  in a straight line instead of drifting around.

## Vindicators

* Vindicators have a chance to spawn riding a chicken.

## Skeletons

* A naturally spawned skeleton may carry the `explosive` tag: its arrows explode on impact with the
  strength of a normal creeper. A skeleton shaped phantom rider can get the tag as well, in which case
  it does **not** get the rider tag - the two are mutually exclusive.
* The `lava` variant does the same but leaves a patch of lava behind instead of exploding.
* Both variants draw a firework rocket trail behind their arrows.

## Endermen

* An enderman with no attack target walks to the closest valuable block, picks it up and removes it.
  Three seconds later the block it is carrying turns into dirt, with teleport particles (but without
  actually teleporting).

## Stability

* Every event handler catches its own failures and reports them through plain stderr instead of
  letting Forge log them. A broken log4j setup in the instance can therefore no longer turn a handled
  problem into a hard crash with the real cause hidden.
* Damage caused by this mod never hurts the one who caused it: an explosion owner is removed from the
  victim list of its own blast and a bomber is immune to its own potion volley.
* Every number the mod uses - ranges, speeds, cooldowns, durations, delays, particle counts, chances
  and switches - lives in the config file, nothing is hard coded any more.

## Configuration

Everything is configurable in `config/crashsuperpro/crashsuperpro-common.toml`, including the
numbers the original mod hard coded: the four rider chances and the rider tag name, the payload tag
chances and tag names, the wither skeleton and skeleton arrow effects (toggle, rider-only switch,
durations and effect levels), the bombardment (toggle, chance, ranges, cooldown, hover height,
valuable-target toggle, arrival distance, TNT fuse, fireball type and power), the chain explosion
toggle and radius, the split tag name, chance, spawn-only switch, child count, spawn radius and
detonation delay, the creeper mount/seek goals (speed, search radius, distance, max resistance, door
opening) and the vindicator chicken chance.

## Building

```
gradlew build
```

The jar ends up in `build/libs`.

## Licence

GPL-3.0-only. Copyright (C) 2026 FromtheArakiel. Derived from *Super-Creeper* by First-sight (MIT);
see `NOTICE` for the original licence text and the list of changes.
