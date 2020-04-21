package us.overflow.anticheat.check.impl.invalid;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.MathUtil;

public final class InvalidA extends PacketCheck {
    private int standTicks;
    private Location lastLocation = new Location(playerData.getPlayer().getWorld(), 0, 0, 0);

    public InvalidA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            if (wrapper.isHasPos()) {
                standTicks = 0;

                final Location playerLocation = playerData.getPlayer().getLocation();

                if (playerLocation != null && lastLocation != null) {
                    final double horizontalDistance = MathUtil.vectorDistance(playerLocation, lastLocation);
                    final double velocityDistance = playerData.getVelocityManager().getMaxVertical() + playerData.getVelocityManager().getMaxHorizontal();

                    if (horizontalDistance < 0.00089 && velocityDistance == 0.0 && standTicks == 0.0) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                }

                this.lastLocation = playerLocation;
            } else {
                final boolean invalid = standTicks > 20;

                if (invalid) {
                    this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                }
            }
        }
    }
}
