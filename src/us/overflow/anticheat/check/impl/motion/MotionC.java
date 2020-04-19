package us.overflow.anticheat.check.impl.motion;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;

@CheckData(name = "Motion (C)", threshold = 10)
public final class MotionC extends PositionCheck {
    private double lastDeltaY;
    private double buffer;

    public MotionC(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get();

        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        if (touchingAir) {
            final double deltaY = Math.abs(to.getY() - from.getY());
            final double predictedY = (lastDeltaY * 0.9800000190734863) - 0.08;

            if (Math.abs(deltaY + 0.0980000019) < 0.005) {
                buffer = 0.0D;
                return;
            }

            if (Math.abs(predictedY - deltaY) > 0.002) {
                buffer += 1.5;

                if (buffer > 5)
                    this.handleViolation().addViolation(ViolationLevel.LOW).create();
            } else {
                buffer = Math.max(0, buffer - 1.25);
            }

            this.lastDeltaY = deltaY;
        } else {
            buffer = Math.max(0, buffer - 10D);
        }
    }
}
