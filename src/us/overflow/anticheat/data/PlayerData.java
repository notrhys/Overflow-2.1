package us.overflow.anticheat.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import us.overflow.anticheat.data.type.*;

@Getter @Setter
public final class PlayerData {
    private final Player player;

    @Setter(AccessLevel.NONE)
    private final CheckManager checkManager = new CheckManager(this);

    @Setter(AccessLevel.NONE)
    private final ActionManager actionManager = new ActionManager();

    @Setter(AccessLevel.NONE)
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Setter(AccessLevel.NONE)
    private final VelocityManager velocityManager = new VelocityManager();

    @Setter(AccessLevel.NONE)
    private final PositionManager positionManager = new PositionManager();

    private final Observable<Boolean> cinematic = new Observable<>(false);

    private int standTicks;
    private float lastYaw, lastPitch;

    public PlayerData(final Player player) {
        this.player = player;
    }
}
