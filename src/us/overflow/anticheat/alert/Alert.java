package us.overflow.anticheat.alert;

import lombok.RequiredArgsConstructor;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.Check;

import java.util.Map;
import java.util.WeakHashMap;

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
        // Send alert to players that have the permission etc
    }
}
