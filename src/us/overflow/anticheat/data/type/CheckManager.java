package us.overflow.anticheat.data.type;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.impl.aimassist.prediction.Cinematic;
import us.overflow.anticheat.check.impl.aimassist.prediction.Prediction;
import us.overflow.anticheat.check.impl.killaura.Example;
import us.overflow.anticheat.check.impl.movement.motion.MotionA;
import us.overflow.anticheat.data.PlayerData;

import java.util.Collection;

public final class CheckManager {
    private final ClassToInstanceMap<Check> checks;

    public CheckManager(final PlayerData playerData) {
        checks = new ImmutableClassToInstanceMap.Builder<Check>()
                .put(Cinematic.class, new Cinematic(playerData))
                .put(Prediction.class, new Prediction(playerData))
                .put(MotionA.class, new MotionA(playerData))
                .build();
    }

    // Get a specific check for pushing a packet/event, or do whatever action possible within said check class
    public final <T extends Check> T getCheck(final Class<T> clazz) {
        return checks.getInstance(clazz);
    }

    // Get all checks we have stored
    public final Collection<Check> getChecks() {
        return checks.values();
    }
}
