package us.overflow.anticheat.processor.impl;

import org.bukkit.Location;
import us.overflow.anticheat.check.type.PositionCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.processor.type.Processor;
import us.overflow.anticheat.update.PositionUpdate;

public final class MovementProcessor implements Processor<PositionUpdate> {

    @Override
    public void process(final PlayerData playerData, final PositionUpdate positionUpdate) {
        final Location to = positionUpdate.getTo();

        //noinspection unchecked
        playerData.getCheckManager().getChecks().stream().filter(PositionCheck.class::isInstance).forEach(check -> check.process(positionUpdate));
        playerData.getPositionManager().updatePositionFlags(to);
    }
}
