package us.overflow.anticheat.check;

import lombok.Getter;
import us.overflow.anticheat.data.PlayerData;

@Getter
public abstract class Check<T> {
    protected final PlayerData playerData;

    private String checkName;
    private int threshold;

    public Check(final PlayerData playerData) {
        this.playerData = playerData;

        final Class clazz = this.getClass();

        if (clazz.isAnnotationPresent(CheckData.class)) {
            final CheckData checkData = (CheckData) clazz.getAnnotation(CheckData.class);

            this.checkName = checkData.name();
            this.threshold = checkData.threshold();
        }
    }

    protected void fail() {

    }

    public abstract void process(T t);
}
