package us.overflow.anticheat.data.type;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.check.Check;
import us.overflow.anticheat.check.impl.aimassist.*;
import us.overflow.anticheat.check.impl.aimassist.prediction.Cinematic;
import us.overflow.anticheat.check.impl.aimassist.prediction.Prediction;
import us.overflow.anticheat.check.impl.autoclicker.*;
import us.overflow.anticheat.check.impl.badpackets.*;
import us.overflow.anticheat.check.impl.flight.*;
import us.overflow.anticheat.check.impl.invalid.InvalidA;
import us.overflow.anticheat.check.impl.invalid.InvalidB;
import us.overflow.anticheat.check.impl.killaura.*;
import us.overflow.anticheat.check.impl.motion.MotionA;
import us.overflow.anticheat.check.impl.motion.MotionB;
import us.overflow.anticheat.check.impl.motion.MotionC;
import us.overflow.anticheat.check.impl.motion.MotionD;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldA;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldB;
import us.overflow.anticheat.check.impl.scaffold.ScaffoldC;
import us.overflow.anticheat.check.impl.speed.SpeedA;
import us.overflow.anticheat.check.impl.speed.SpeedB;
import us.overflow.anticheat.check.impl.speed.SpeedC;
import us.overflow.anticheat.check.impl.speed.SpeedD;
import us.overflow.anticheat.check.impl.timer.TimerA;
import us.overflow.anticheat.check.impl.velocity.VelocityA;
import us.overflow.anticheat.check.impl.velocity.VelocityB;
import us.overflow.anticheat.data.PlayerData;

import java.util.Collection;

public final class CheckManager {
    private ClassToInstanceMap<Check> checks;

    public CheckManager(final PlayerData playerData) {
        if (!OverflowAPI.INSTANCE.getClassManager().flag) {
            checks = new ImmutableClassToInstanceMap.Builder<Check>()
                    .put(Cinematic.class, new Cinematic(playerData))
                    .put(Prediction.class, new Prediction(playerData))
                    .put(MotionA.class, new MotionA(playerData))
                    .put(MotionB.class, new MotionB(playerData))
                    .put(MotionC.class, new MotionC(playerData))
                    .put(MotionD.class, new MotionD(playerData))
                    .put(FlightA.class, new FlightA(playerData))
                    .put(FlightB.class, new FlightB(playerData))
                    .put(FlightC.class, new FlightC(playerData))
                    .put(FlightD.class, new FlightD(playerData))
                    .put(FlightE.class, new FlightE(playerData))
                    .put(KillAuraA.class, new KillAuraA(playerData))
                    .put(KillAuraB.class, new KillAuraB(playerData))
                    .put(KillAuraC.class, new KillAuraC(playerData))
                    .put(KillAuraD.class, new KillAuraD(playerData))
                    .put(KillAuraE.class, new KillAuraE(playerData))
                    .put(KillAuraF.class, new KillAuraF(playerData))
                    .put(KillAuraG.class, new KillAuraG(playerData))
                    .put(KillAuraH.class, new KillAuraH(playerData))
                    .put(InvalidA.class, new InvalidA(playerData))
                    .put(InvalidB.class, new InvalidB(playerData))
                    .put(AimAssistA.class, new AimAssistA(playerData))
                    .put(AimAssistB.class, new AimAssistB(playerData))
                    .put(AimAssistC.class, new AimAssistC(playerData))
                    .put(AimAssistD.class, new AimAssistD(playerData))
                    .put(AimAssistE.class, new AimAssistE(playerData))
                    .put(AimAssistF.class, new AimAssistF(playerData))
                    .put(AimAssistG.class, new AimAssistG(playerData))
                    .put(AutoClickerA.class, new AutoClickerA(playerData))
                    .put(AutoClickerB.class, new AutoClickerB(playerData))
                    .put(AutoClickerC.class, new AutoClickerC(playerData))
                    .put(AutoClickerD.class, new AutoClickerD(playerData))
                    .put(AutoClickerE.class, new AutoClickerE(playerData))
                    .put(ScaffoldA.class, new ScaffoldA(playerData))
                    .put(ScaffoldB.class, new ScaffoldB(playerData))
                    .put(ScaffoldC.class, new ScaffoldC(playerData))
                    .put(BadPacketsA.class, new BadPacketsA(playerData))
                    .put(BadPacketsB.class, new BadPacketsB(playerData))
                    .put(BadPacketsC.class, new BadPacketsC(playerData))
                    .put(BadPacketsD.class, new BadPacketsD(playerData))
                    .put(BadPacketsE.class, new BadPacketsE(playerData))
                    .put(BadPacketsF.class, new BadPacketsF(playerData))
                    .put(VelocityA.class, new VelocityA(playerData))
                    .put(VelocityB.class, new VelocityB(playerData))
                    .put(SpeedA.class, new SpeedA(playerData))
                    .put(SpeedB.class, new SpeedB(playerData))
                    .put(TimerA.class, new TimerA(playerData))
                    .put(SpeedC.class, new SpeedC(playerData))
                    .put(FlightF.class, new FlightF(playerData))
                    .put(SpeedD.class, new SpeedD(playerData))
                    .build();
        }
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
