package us.overflow.anticheat.check.impl.killaura;

import org.bukkit.Bukkit;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

@CheckData(name = "Example")
public final class Example extends PacketCheck {

    public Example(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket wrappedPacket) {
        if (wrappedPacket instanceof WrappedPacketPlayInFlying) {
        }
    }
}
