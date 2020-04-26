package us.overflow.anticheat.alert;

import lombok.RequiredArgsConstructor;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.config.impl.MessageConfig;
import us.overflow.anticheat.utils.ColorUtil;

@RequiredArgsConstructor
public final class Alert {
    private int level;
    private final Check check;

    public Alert addViolation(final ViolationLevel violationLevel) {
        final int threshold = check.getThreshold();
        final int violation = violationLevel.getLevel();

        level += violation;

        if (level > threshold) {
            // Execute ban from config
        }

        return this;
    }

    public void create() {
        final String baseAlertMessage = OverflowAPI.INSTANCE.getConfigManager().getConfig(MessageConfig.class).getAlertMessage();

        final String playerName = check.getPlayerData().getPlayer().getName();
        final String checkName = check.getCheckName();
        final String violations = String.valueOf(level);

        final String format = ColorUtil.format(baseAlertMessage.replace("%player%", playerName).replace("%check%", checkName).replace("%vl%", violations));

        OverflowAPI.INSTANCE.getAlertExecutor().execute(() ->
                OverflowAPI.INSTANCE.getPlayerDataManager()
                        .getAllPlayerData()
                        .stream().filter(data -> data.getAlerts().get())
                        .forEach(data -> data.getPlayer().sendMessage(format)));
    }
}
