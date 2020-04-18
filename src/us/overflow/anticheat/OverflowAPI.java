package us.overflow.anticheat;

import lombok.Getter;
import org.bukkit.Bukkit;
import us.overflow.anticheat.config.ConfigManager;
import us.overflow.anticheat.data.manager.PlayerDataManager;
import us.overflow.anticheat.judgement.JudgementManager;
import us.overflow.anticheat.listener.PlayerListener;
import us.overflow.anticheat.packet.VersionHandler;
import us.overflow.anticheat.processor.ProcessorManager;
import us.overflow.anticheat.trait.Startable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public enum OverflowAPI {
    INSTANCE;

    private OverflowPlugin plugin;

    private final Executor judgementExecutor = Executors.newSingleThreadExecutor();
    private final Executor alertExecutor = Executors.newSingleThreadExecutor();
    private final Executor packetExecutor = Executors.newSingleThreadExecutor();
    private final Executor positionExecutor = Executors.newSingleThreadExecutor();

    private final ProcessorManager processorManager = new ProcessorManager();
    private final PlayerDataManager playerDataManager = new PlayerDataManager();
    private final ConfigManager configManager = new ConfigManager();
    private final JudgementManager judgementManager = new JudgementManager();

    private final VersionHandler versionHandler = new VersionHandler();

    private final List<Startable> startables = new ArrayList<>();

    public void start(final OverflowPlugin plugin) {
        this.plugin = plugin;

        assert plugin != null: "Overflow faced a fatal error. Contact the developers for a fix. #1";

        startables.add(processorManager);
        startables.add(playerDataManager);
        startables.add(judgementManager);
        startables.add(configManager);
        startables.forEach(Startable::start);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), plugin);
    }

    public void shutdown(final OverflowPlugin plugin) {
        assert plugin != null: "Overflow faced a fatal error. Contact the developers for a fix. #2";

        this.plugin = null;
    }
}
