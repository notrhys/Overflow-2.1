package us.overflow.anticheat.check.impl.aimassist;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

@CheckData(name = "AimAssist (F)")
public final class AimAssistF extends RotationCheck {
    private int buffer;

    public AimAssistF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final long now = System.currentTimeMillis();

        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaYaw = Math.abs(to.getYaw() - from.getYaw());
        final float deltaPitch = Math.abs(to.getPitch() - from.getPitch());

        final boolean attacking = now - playerData.getActionManager().getLastAttack() < 1000L;

        if (deltaYaw == 0.0 || !attacking) {
            buffer = 0;
        }

        if (deltaPitch == 0.0 && deltaYaw > 0.1) {
            if (++buffer > 49) {
                this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
            }
        } else {
            buffer = Math.max(buffer - 1, 0);
        }
    }
}
