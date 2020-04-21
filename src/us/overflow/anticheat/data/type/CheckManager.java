package us.overflow.anticheat.data.type;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.impl.aimassist.prediction.Cinematic;
import us.overflow.anticheat.check.impl.aimassist.prediction.Prediction;
import us.overflow.anticheat.check.impl.flight.FlightA;
import us.overflow.anticheat.check.impl.flight.FlightB;
import us.overflow.anticheat.check.impl.flight.FlightC;
import us.overflow.anticheat.check.impl.invalid.InvalidA;
import us.overflow.anticheat.check.impl.invalid.InvalidB;
import us.overflow.anticheat.check.impl.killaura.KillAuraA;
import us.overflow.anticheat.check.impl.killaura.KillAuraB;
import us.overflow.anticheat.check.impl.killaura.KillAuraC;
import us.overflow.anticheat.check.impl.killaura.KillAuraD;
import us.overflow.anticheat.check.impl.motion.MotionA;
import us.overflow.anticheat.check.impl.motion.MotionB;
import us.overflow.anticheat.check.impl.motion.MotionC;
import us.overflow.anticheat.data.PlayerData;

import java.util.Collection;

public final class CheckManager {
    private final ClassToInstanceMap<Check> checks;

    public CheckManager(final PlayerData playerData) {
        checks = new ImmutableClassToInstanceMap.Builder<Check>()
                .put(Cinematic.class, new Cinematic(playerData))
                .put(Prediction.class, new Prediction(playerData))
                .put(MotionA.class, new MotionA(playerData))
                .put(MotionB.class, new MotionB(playerData))
                .put(MotionC.class, new MotionC(playerData))
                .put(FlightA.class, new FlightA(playerData))
                .put(FlightB.class, new FlightB(playerData))
                .put(FlightC.class, new FlightC(playerData))
                .put(KillAuraA.class, new KillAuraA(playerData))
                .put(KillAuraB.class, new KillAuraB(playerData))
                .put(KillAuraC.class, new KillAuraC(playerData))
                .put(KillAuraD.class, new KillAuraD(playerData))
                .put(InvalidA.class, new InvalidA(playerData))
                .put(InvalidB.class, new InvalidB(playerData))
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
