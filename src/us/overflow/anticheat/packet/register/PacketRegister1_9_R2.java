package us.overflow.anticheat.packet.register;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.handlers.PacketHandler1_9_R2;

@Getter
public final class PacketRegister1_9_R2 {
    private final PlayerData playerData;

    public PacketRegister1_9_R2(final PlayerData playerData) {
        this.playerData = playerData;

        final EntityPlayer entityPlayer = ((CraftPlayer) playerData.getPlayer()).getHandle();

        OverflowAPI.INSTANCE.getPacketExecutor().execute(() -> new PacketHandler1_9_R2(entityPlayer.server, entityPlayer.playerConnection.networkManager, entityPlayer, playerData));
    }
}
