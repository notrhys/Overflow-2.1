package us.overflow.anticheat.check.impl.flight;

import org.bukkit.Location;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.ReflectionUtil;

@CheckData(name = "Flight (A)")
public final class FlightA extends PositionCheck {
    private Location lastGroundLocation = null;

    public FlightA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final boolean touchingAir = playerData.getPositionManager().getTouchingAir().get();

        if (playerData.getVelocityManager().getMaxHorizontal() > 0.0 || playerData.getVelocityManager().getMaxVertical() > 0.0) {
            return;
        }

        if (touchingAir) {
            final double deltaY = to.getY() - from.getY();
            final double motionY = ReflectionUtil.getMotionY(playerData);

            if (deltaY >= 0.0 && motionY <= 0.0 && lastGroundLocation != null) {
                final double horizontalDistance = MathUtil.vectorDistance(to, lastGroundLocation);

                if (horizontalDistance > 5.d) {
                    this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                }
            }
        } else {
            lastGroundLocation = to;
        }
    }
}
