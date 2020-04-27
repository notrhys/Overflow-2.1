package us.overflow.anticheat.packet.register;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_12_R1.PacketPlayOutTransaction;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_12_R1;

public final class PacketRegister1_12_R1 {

    public PacketRegister1_12_R1(final PlayerData playerData) {
        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_12_R1(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }

    public void sendTransactionPacket(final PlayerData playerData, final short action) {
        final PacketPlayOutTransaction transaction = new PacketPlayOutTransaction((byte) 0, action, false);

        ((CraftPlayer) playerData.getPlayer()).getHandle().playerConnection.sendPacket(transaction);
    }

    public void sendKeepAlivePacket(final PlayerData playerData, int action) {
        final PacketPlayOutKeepAlive keepAlive = new PacketPlayOutKeepAlive(action);

        ((CraftPlayer) playerData.getPlayer()).getHandle().playerConnection.sendPacket(keepAlive);
    }
}
