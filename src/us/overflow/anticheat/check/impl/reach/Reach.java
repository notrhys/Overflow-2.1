package us.overflow.anticheat.check.impl.reach;

import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInUseEntity;

public final class Reach extends PacketCheck {

    public Reach(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInUseEntity) {

        }
    }
}
