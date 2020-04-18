package us.overflow.anticheat.config.impl;

import org.bukkit.configuration.file.YamlConfiguration;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.OverflowPlugin;
import us.overflow.anticheat.config.type.Config;

import java.io.File;

public final class MessageConfig implements Config {
    private File file;
    private final OverflowPlugin plugin = OverflowAPI.INSTANCE.getPlugin();

    @Override
    public void generate() {
        this.create();

        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains("messages")) {
            config.set("messages", "");
        }

        if (!config.contains("messages.alert.message")) {
            config.set("messages.alert", "&7[&cOverFlow&7] &c%player% &7failed &c%check% &7[VL: %vl%]");
        }

        if (!config.contains("messages.alert.broadcast")) {
            config.set("messages.alert.broadcast", "&7[&cOverFlow&7] &c%player% &7was detected &ccheating &7and was removed from the network.");
        }

        if (!config.contains("messages.judgement.broadcast")) {
            config.set("messages.judgement.broadcast", "&7[&cOverFlow&7] &c%player% &7was detected &ccheating &7and was removed from the network.");
        }

        if (!config.contains("messages.judgement.start")) {
            config.set("messages.judgement.start", "&7[&cOverFlow&7] &cStarting the judgement day...");
        }

        try {
            config.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void create() {
        file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdir();

                plugin.saveResource("messages.yml", false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
