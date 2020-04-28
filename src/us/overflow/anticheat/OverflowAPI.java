package us.overflow.anticheat;

import lombok.Getter;
import org.bukkit.Bukkit;
import us.overflow.anticheat.command.CommandManager;
import us.overflow.anticheat.config.ConfigManager;
import us.overflow.anticheat.config.impl.MessageConfig;
import us.overflow.anticheat.data.Observable;
import us.overflow.anticheat.data.manager.PlayerDataManager;
import us.overflow.anticheat.hook.ClassManager;
import us.overflow.anticheat.judgement.JudgementManager;
import us.overflow.anticheat.listener.PlayerListener;
import us.overflow.anticheat.packet.VersionHandler;
import us.overflow.anticheat.processor.ProcessorManager;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.KeyFile;
import us.overflow.anticheat.utils.OSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum OverflowAPI {
    INSTANCE;

    private OverflowPlugin plugin;

    private final Executor judgementExecutor = Executors.newSingleThreadExecutor();
    private final Executor alertExecutor = Executors.newSingleThreadExecutor();
    private final Executor packetExecutor = Executors.newSingleThreadExecutor();
    private final Executor positionExecutor = Executors.newSingleThreadExecutor();

    private final ScheduledExecutorService authExecutor = Executors.newSingleThreadScheduledExecutor();

    private final Observable<Boolean> debug = new Observable<>(false);

    public final ProcessorManager processorManager = new ProcessorManager();
    public final PlayerDataManager playerDataManager = new PlayerDataManager();
    public final ConfigManager configManager = new ConfigManager();
    public final CommandManager commandManager = new CommandManager();
    public final JudgementManager judgementManager = new JudgementManager();

    private final VersionHandler versionHandler = new VersionHandler();
    public final List<Startable> startables = new ArrayList<>();

    public String key;

    public ClassManager classManager = new ClassManager();

    public void start(final OverflowPlugin plugin) {
        this.plugin = plugin;

        if (OSUtils.getPlatform() == OSUtils.OS.LINUX) {

            KeyFile.getInstance().setup(plugin);
            this.key = KeyFile.getInstance().getData().getString("LicenseKey");

            if (this.key.equalsIgnoreCase("ENTER_KEY_HERE")) {
                plugin.getLogger().warning("Please enter a key!");
                return;
            }

            classManager.start();
        }
    }

    public void shutdown() {
        this.plugin = null;
        authExecutor.shutdownNow();
    }
}
