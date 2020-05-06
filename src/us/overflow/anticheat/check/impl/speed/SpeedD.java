package us.overflow.anticheat.check.impl.speed;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.CustomLocation;

@CheckData(name = "Speed (D)")
public final class SpeedD extends PacketCheck {

    public SpeedD(final PlayerData playerData) {
        super(playerData);
    }

    private double lastX, lastZ, movementSpeed, lastBlockY, offset = Math.pow(0.984, 9);

    private int verbose, airTicks, groundTicks, slabTicks, stairTicks, iceTicks, blockAboveTicks, speedPotionTicks, slimeTicks;

    private long lastBlockJump, lastIce;

    private CustomLocation lastGroundLocation, to, from;

    private boolean fail;

    @Override
    public void process(WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;

            if (wrappedPacketPlayInFlying.isHasPos()) {

                if (this.fail) {
                    this.fail = false;
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }

                double x = wrappedPacketPlayInFlying.getX();
                double z = wrappedPacketPlayInFlying.getZ();

                CustomLocation location = new CustomLocation(wrappedPacketPlayInFlying.getX(), wrappedPacketPlayInFlying.getY(), wrappedPacketPlayInFlying.getZ());


                //Using packet even because its better, fuck you

                this.doCalulcations(location);

                this.from = this.to;
                this.to = location;
                this.movementSpeed = getSpeed(x, z);
                this.lastX = x;
                this.lastZ = z;
            }
        }
    }

    private void doCalulcations(CustomLocation location) {
        //Fucking cancer.

        boolean ground = !getPlayerData().getPositionManager().getTouchingAir().get();

        boolean stair = playerData.getPositionManager().getTouchingStair().get();

        boolean slab = playerData.getPositionManager().getTouchingStair().get();

        boolean ice = playerData.getPositionManager().getTouchingIce().get();

        boolean blockAbove = playerData.getPositionManager().getBelowBlocks().get();

        boolean slime = playerData.getPositionManager().getTouchingSlime().get();

        boolean hasSpeed = getPlayerData().getPlayer().hasPotionEffect(PotionEffectType.SPEED);

        if (location != null && this.to != null && this.from != null) {
            Location playerLocation = getPlayerData().getPlayer().getLocation();

            double current = playerLocation.clone().add(0, -1, 0).getBlockY();

            if (ground) {

                if ((current - this.lastBlockY) > 0.0) {
                    this.lastBlockJump = System.currentTimeMillis();
                }

                this.lastBlockY = playerLocation.clone().add(0, -1, 0).getBlockY();
            }
        }

        if (stair) {
            if (stairTicks < 20) stairTicks++;
        } else {
            if (stairTicks > 0) stairTicks--;
        }

        if (slab) {
            if (slabTicks < 20) slabTicks++;
        } else {
            if (slabTicks > 0) slabTicks--;
        }

        if (slime) {
            if (slimeTicks < 20) slabTicks++;
        } else {
            if (slimeTicks > 0) slimeTicks--;
        }

        if (ice) {
            if (iceTicks < 20) iceTicks++;
            this.lastIce = System.currentTimeMillis();
        } else {
            if (iceTicks > 0) iceTicks--;
        }


        if (hasSpeed) {
            if (speedPotionTicks < 20) speedPotionTicks++;
        } else {
            if (speedPotionTicks > 0) speedPotionTicks--;
        }

        if (blockAbove) {
            if (blockAboveTicks < 20) blockAboveTicks++;
        } else {
            if (blockAboveTicks > 0) blockAboveTicks--;
        }


        if (ground) {
            if (groundTicks < 20) groundTicks++;
            airTicks = 0;
        } else {
            if (airTicks < 20) airTicks++;
            groundTicks = 0;
        }

        this.doCheck();
    }

    private void doCheck() {
        float threshold = (float) (this.airTicks > 0 ? 0.4163 * this.offset : this.groundTicks > 24 ? 0.291 : 0.375);

        if (slabTicks > 0 || stairTicks > 0) {
            threshold += .3f;
        }

        if (blockAboveTicks > 0 && iceTicks < 1) {
            threshold += .4f;
        }

        if (iceTicks > 0 && blockAboveTicks > 0) {
            threshold += 1.1f;
        }

        if ((System.currentTimeMillis() - getPlayerData().getLastAttackDamage()) < 1000L || (System.currentTimeMillis() - this.lastBlockJump) < 1000L) threshold += .8f;

       if (speedPotionTicks > 0) threshold += getPotionEffectLevel() * 0.2;

       if ((!getPlayerData().getPlayer().hasPotionEffect(PotionEffectType.SPEED) && speedPotionTicks > 0) || this.slimeTicks > 0 || this.iceTicks > 0 || (System.currentTimeMillis() - this.lastIce) < 1000L) {
           this.verbose = 0;
           return;
       }

        if (this.movementSpeed > threshold) {
            if (this.verbose++ > 2) {
                this.verbose = 0;
                this.fail = true;
            }
        } else {
            this.verbose -= (this.verbose > 0 ? 1 : 0);
        }
    }

    private int getPotionEffectLevel() {
        for (PotionEffect pe : getPlayerData().getPlayer().getActivePotionEffects()) {
            if (pe.getType().getName().equalsIgnoreCase(PotionEffectType.SPEED.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }

    private double getSpeed(double cx, double cz) {
        double x = Math.abs(Math.abs(cx) - Math.abs(this.lastX));
        double z = Math.abs(Math.abs(cz) - Math.abs(this.lastZ));
        return Math.sqrt(x * x + z * z);
    }
}
