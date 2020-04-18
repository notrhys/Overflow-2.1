package us.overflow.anticheat.check;

import lombok.Getter;
import us.overflow.anticheat.alert.Alert;
import us.overflow.anticheat.data.PlayerData;

@Getter
public abstract class Check<T> {
    protected final PlayerData playerData;

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

    public abstract void process(T t);
}
