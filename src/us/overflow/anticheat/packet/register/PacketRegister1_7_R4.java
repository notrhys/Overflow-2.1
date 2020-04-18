package us.overflow.anticheat.packet.register;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_7_R4;

@Getter
public final class PacketRegister1_7_R4 {
    private final PlayerData playerData;

    public PacketRegister1_7_R4(final PlayerData playerData) {
        this.playerData = playerData;

        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_7_R4(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
