package us.overflow.anticheat.alert;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class Alert {
    private int violations;

    private final List<Long> alerts = new ArrayList<>();
    private final Check check;

    private final String base = ColorUtil.format("&7[&eOverFlow§7] &e%s &7failed &e%s &7[&eVL: %s&7]");

    /**
     *
     * @param violationLevel - The violations you want to add for a specific check
     * @return - Returns the alert manager. so we can have a nice look. (this.handleAlert().addViolation(ViolationLevel.HIGH).create())
     */
    public Alert addViolation(final ViolationLevel violationLevel) {
        final long now = System.currentTimeMillis();

        // We don't want double alerts
        if (!alerts.contains(now)) {
            // Add alert to the recent alerts list.
            alerts.add(now);

            // Get the level from the enum and get the threshold from the check
            final int level = violationLevel.getLevel();
            final int threshold = check.getThreshold();

            // Add level to the current check violations
            violations += level;

            // If the violations exceeds or is equal to the threshold, ban.
            if (violations >= threshold) {
                // Execute direct punishment.
            }
        }

        return this;
    }

    /*
     * This is a void so its the final statement of the method. After you create, you can't go back to prevent confusion.
     */
    public void create() {
        final String checkName = check.getCheckName();
        final PlayerData playerData = check.getPlayerData();

        final String alert = String.format(base, playerData.getPlayer().getName(), checkName, violations);

        OverflowAPI.INSTANCE.getAlertExecutor().execute(() ->
                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(toSend -> toSend.hasPermission("overflow.alerts"))
                        .forEach(toSend -> toSend.sendMessage(alert)));
    }
}
