package us.overflow.anticheat.packet.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public final class WrappedPacketPlayInBlockPlace extends WrappedPacket{
    private final int face;
}
