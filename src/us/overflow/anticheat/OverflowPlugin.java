package us.overflow.anticheat;

import org.bukkit.plugin.java.JavaPlugin;

public final class OverflowPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        OverflowAPI.INSTANCE.start(this);
    }

    @Override
    public void onDisable() {
        OverflowAPI.INSTANCE.shutdown();
    }
}
