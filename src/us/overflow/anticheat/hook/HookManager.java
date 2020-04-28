package us.overflow.anticheat.hook;

import org.bukkit.Bukkit;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.listener.PlayerListener;
import us.overflow.anticheat.trait.Startable;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
public class HookManager {
    public HookManager() {
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.processorManager);
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.playerDataManager);
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.judgementManager);
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.configManager);
        OverflowAPI.INSTANCE.startables.add(OverflowAPI.INSTANCE.commandManager);
        OverflowAPI.INSTANCE.startables.forEach(Startable::start);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), OverflowAPI.INSTANCE.getPlugin());
    }
}
