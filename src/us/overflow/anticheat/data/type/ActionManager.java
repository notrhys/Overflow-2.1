package us.overflow.anticheat.data.type;

import lombok.Getter;
import us.overflow.anticheat.data.Observable;

@Getter
public final class ActionManager {

    /*
    We are using observables because they are less laggy than normal booleans. (Note: DON'T ABUSE THEM)
     */
    private final Observable<Boolean> attacking = new Observable<>(false);
    private final Observable<Boolean> swinging = new Observable<>(false);
    private final Observable<Boolean> digging = new Observable<>(false);

    private long lastBukkitDig;

    public void onFlying() {
        final long now = System.currentTimeMillis();
        final boolean digging = now - lastBukkitDig < 100L;

        this.attacking.set(false);
        this.swinging.set(false);

        this.digging.set(digging);
    }

    public void onAttack() {
        this.attacking.set(true);
    }

    public void onSwing() {
        this.swinging.set(true);
    }

    public void onDig() {
        this.digging.set(true);
    }

    public void onBukkitDig() {
        final long now = System.currentTimeMillis();

        if (now - lastBukkitDig < 100L) {
            this.digging.set(true);
        }

        this.lastBukkitDig = now;
    }
}
