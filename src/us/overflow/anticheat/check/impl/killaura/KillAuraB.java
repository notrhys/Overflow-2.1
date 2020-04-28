package us.overflow.anticheat.check.impl.killaura;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;
import us.overflow.anticheat.utils.MathUtil;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "KillAura (B)")
public final class KillAuraB extends RotationCheck {
    private final Deque<Float> pitchSamples = new LinkedList<>();

    private double buffer;
    private double lastAverage;

    public KillAuraB(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaYaw > 0.0 && deltaPitch > 0.0 && deltaPitch < 30.f) {
            pitchSamples.add(deltaPitch);
        }

        if (pitchSamples.size() == 20) {
            final boolean attacked = playerData.getActionManager().getAttacking().get();

            final double averagePitch = pitchSamples.stream().mapToDouble(d -> d).average().orElse(0.0);
            final double deviationPitch = MathUtil.deviationSquared(pitchSamples);

            final double averageDelta = Math.abs(averagePitch - lastAverage);

            if (deviationPitch > 6.d && averageDelta > 1.d && deviationPitch < 300.f) {
                if (attacked && ++buffer > 6) {
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }
            } else {
                buffer = Math.max(buffer - 0.25, 0);
            }

            lastAverage = averagePitch;
            pitchSamples.clear();
        }
    }
}
