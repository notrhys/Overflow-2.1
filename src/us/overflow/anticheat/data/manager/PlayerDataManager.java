package us.overflow.anticheat.data.manager;

import org.bukkit.entity.Player;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.trait.Startable;
import us.overflow.anticheat.utils.LogUtil;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerDataManager implements Startable {
    // This is where we will be storing the player data
    private final Map<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    /**
     * @param player - The player you want to get the data from
     * @return - The player-data
     */
    public final PlayerData getData(final Player player) {
        return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData(player));
    }

    /**
     * @param player - The player you want to remove from the map
     */
    public void remove(final Player player) {
        final UUID uuid = player.getUniqueId();

        playerDataMap.remove(uuid);
    }

    /**
     * @return - Returns all the player data that are currently stored.
     */
    public Collection<PlayerData> getAllPlayerData() {
        return playerDataMap.values();
    }

    @Override
    public void start() {
        LogUtil.log("Started up the player-data manager.");
    }
}
