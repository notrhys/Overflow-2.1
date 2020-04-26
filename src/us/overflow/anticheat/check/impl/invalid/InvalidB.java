package us.overflow.anticheat.check.impl.invalid;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInEntityAction;
import us.overflow.anticheat.packet.type.enums.EnumPlayerAction;

@CheckData(name = "Invalid (B)")
public final class InvalidB extends PacketCheck {
    private long lastStopSprinting;

    public InvalidB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInEntityAction) {
            final WrappedPacketPlayInEntityAction wrapper = (WrappedPacketPlayInEntityAction) packet;

            final long now = System.currentTimeMillis();

            if (wrapper.getUseAction() == EnumPlayerAction.START_SPRINTING) {
                final long deltaAction = now - lastStopSprinting;

                // Cannot sprint and un sprint in the same tick.
                if (deltaAction < 40L) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            }

            if (wrapper.getUseAction() == EnumPlayerAction.STOP_SPRINTING) {
                lastStopSprinting = now;
            }
        }
    }
}
