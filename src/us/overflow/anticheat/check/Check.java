package us.overflow.anticheat.check;

import lombok.Getter;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.alert.Alert;
import us.overflow.anticheat.data.PlayerData;

import java.util.Deque;
import java.util.LinkedList;

@Getter
public abstract class Check<T> {
    protected final PlayerData playerData;

    private final Deque<String> debugs = new LinkedList<>();

    private String checkName;
    private int threshold;

    private final Alert alert = new Alert(this);

    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        final Class clazz = this.getClass();

        if (clazz.isAnnotationPresent(CheckData.class)) {
            final CheckData checkData = (CheckData) clazz.getAnnotation(CheckData.class);

            this.checkName = checkData.name();
            this.threshold = checkData.threshold();
        }
    }

    protected Alert handleViolation() {
        return alert;
    }

    protected void debug(final String log) {
        final boolean enabledDebug = OverflowAPI.INSTANCE.getDebug().get();

        if (enabledDebug) {
            debugs.add(log);
        }
    }

    public abstract void process(T t);
}
