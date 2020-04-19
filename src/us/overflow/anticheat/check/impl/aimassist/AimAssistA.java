package us.overflow.anticheat.check.impl.aimassist;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "AimAssist (A)", threshold = 4)
public final class AimAssistA extends RotationCheck {

    private int buffer;
    private final Deque<Float> pitchSamples = new LinkedList<>();

    public AimAssistA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        if (deltaPitch > 0.0 && deltaYaw > 0.0 && deltaYaw < 25.f && deltaPitch < 20.f) {
            final boolean cinematic = playerData.getCinematic().get();

            if (!cinematic && pitchSamples.add(deltaPitch) && pitchSamples.size() == 120) {
                final long distinct = pitchSamples.stream().distinct().count();
                final long duplicates = pitchSamples.size() - distinct;

                if (duplicates <= 9L) {
                    if (++buffer > 2) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }

                    if (duplicates <= 3) {
                        buffer += 2;
                    }
                } else {
                    buffer = 0;
                }

                pitchSamples.clear();
            }
        }
    }
}
