package us.overflow.anticheat.packet.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_10_R1;

public final class PacketRegister1_10_R1 {

    public PacketRegister1_10_R1(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_10_R1(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
