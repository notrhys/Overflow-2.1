package us.overflow.anticheat.packet.register;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_8_R3;

public final class PacketRegister1_8_R3 {

    public PacketRegister1_8_R3(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_8_R3(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
