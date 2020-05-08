package us.overflow.anticheat.check.impl.velocity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.update.PositionUpdate;

@CheckData(name = "Velocity (A)")
public final class VelocityA extends PositionCheck {
    private int buffer;

    public VelocityA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final PositionUpdate positionUpdate) {
        final Location from = positionUpdate.getFrom();
        final Location to = positionUpdate.getTo();

        final double deltaX = to.getX() - from.getX();
        final double deltaZ = to.getZ() - from.getZ();

        final boolean velocity = playerData.getVelocity().get();

        if (velocity) {
            final boolean attacked = playerData.getActionManager().getAttacking().get();

            final double velocityX = playerData.getVelocityX();
            final double velocityZ = playerData.getVelocityZ();

            final Vector movementVector = new Vector(deltaX, 0, deltaZ);
            final Vector velocityVector = new Vector(velocityX, 0, velocityZ);

            if (attacked) {
                velocityVector.multiply(0.6);
            }

            final double vectorSubtracted = velocityVector.subtract(movementVector).length();

            if (vectorSubtracted > 0.4) {

            } else {
                buffer = 0;
            }

            playerData.getVelocity().set(false);
        }
    }
}
