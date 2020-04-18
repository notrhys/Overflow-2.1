package us.overflow.anticheat.config;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.config.impl.CheckConfig;
import us.overflow.anticheat.config.impl.MessageConfig;
import us.overflow.anticheat.config.impl.WebConfig;
import us.overflow.anticheat.config.type.Config;
import us.overflow.anticheat.data.type.CheckManager;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.LogUtil;

public final class ConfigManager implements Startable {
    private final ClassToInstanceMap<Config> configs;

    public ConfigManager() {
        configs = new ImmutableClassToInstanceMap.Builder<Config>()
                .put(CheckConfig.class, new CheckConfig())
                .put(MessageConfig.class, new MessageConfig())
                .put(WebConfig.class, new WebConfig())
                .build();

        configs.values().forEach(Config::generate);
    }

    // Get a specific check for pushing a packet/event, or do whatever action possible within said check class
    public final <T extends Config> T getCheck(final Class<T> clazz) {
        return configs.getInstance(clazz);
    }

    @Override
    public void start() {
        LogUtil.log("Initializing config manager and generating configs..");
    }
}
