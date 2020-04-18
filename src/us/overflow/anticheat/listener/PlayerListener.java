package us.overflow.anticheat.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.VersionHandler;
import us.overflow.anticheat.update.PositionUpdate;

public final class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        // Grab the player from the event
        final Player player = event.getPlayer();

        // Grab the player data from the player
        final PlayerData playerData = OverflowAPI.INSTANCE.getPlayerDataManager().getData(player);

        // Create the packet handler using the version handler with the appropriate version
        OverflowAPI.INSTANCE.getVersionHandler().create(playerData);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        // Grab the player from the event
        final Player player = event.getPlayer();

        // Grab the player data from the manager
        final PlayerData playerData = OverflowAPI.INSTANCE.getPlayerDataManager().getData(player);

        // Get the from and the to location
        final Location from = event.getFrom();
        final Location to = event.getTo();

        // Create the position update
        final PositionUpdate positionUpdate = new PositionUpdate(from, to);

        // Player did not move
        if (from.distance(to) == 0.0) {
            return;
        }

        // Spoofable but we will have bad packets checks for all of them.
        if (player.isInsideVehicle() || player.isFlying() || player.getAllowFlight() || player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // We do not want checks to mess up due to player being inside an unloaded chunk
        if (!player.getWorld().isChunkLoaded(to.getBlockX() >> 4, to.getBlockZ() >> 4)) {
            return;
        }

        //noinspection unchecked
        playerData.getCheckManager().getChecks().stream().filter(PositionCheck.class::isInstance).forEach(check -> check.process(positionUpdate));
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            final PlayerData playerData = OverflowAPI.INSTANCE.getPlayerDataManager().getData(player);

            playerData.getActionManager().onBukkitDig();
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        // Get the player from the event
        final Player player = event.getPlayer();

        // Remove the player from the player data manager to prevent memory leaks
        OverflowAPI.INSTANCE.getPlayerDataManager().remove(player);
    }
}
