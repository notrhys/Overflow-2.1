package us.overflow.anticheat.packet;

import org.bukkit.Bukkit;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;

import java.lang.reflect.Method;

public final class VersionHandler {
    private String version = Bukkit.getServer().getClass().getName().split("org.bukkit.craftbukkit.")[1];

    public VersionHandler() {
        if (version.endsWith(".CraftServer")) {
            version = version.replace(".CraftServer", "");
        }
        version = version.substring(1);
    }

    public void create(final PlayerData playerData) {
        try {
            Class.forName("us.overflow.anticheat.packet.register.PacketRegister" + version).getConstructor(PlayerData.class).newInstance(playerData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(final WrappedPacket packet) {
        try {
            final Class reflectionClass = Class.forName("us.overflow.anticheat.packet.register.PacketRegister" + version);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
