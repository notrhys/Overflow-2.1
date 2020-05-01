package us.overflow.anticheat.check.impl.timer;

import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;

import java.util.Deque;
import java.util.LinkedList;

@CheckData(name = "Timer (A)", threshold = 2)
public final class TimerA extends PacketCheck {
    private int buffer;
    private long lastFlying;
    private final Deque<Long> delaySamples = new LinkedList<>();

    public TimerA(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final long now = System.currentTimeMillis();

            final boolean invalid = now - playerData.getLastJoin() < 1000L || playerData.getConnectionManager().getDelayed().get() || playerData.getActionManager().getTeleported().get();

            if (!invalid) {
                delaySamples.add(now - lastFlying);
            }

            if (delaySamples.size() == 40) {
                final double averagePacketDiff = delaySamples.stream()
                        .mapToDouble(d -> d)
                        .average()
                        .orElse(0.0);

                final double timerSpeed = 50.0 / averagePacketDiff;

                if (timerSpeed > 1.02) {
                    if (++buffer > 3) {
                        this.handleViolation().addViolation(ViolationLevel.HIGH).create();
                    }
                } else {
                    buffer = Math.max(buffer - 1, 0);
                }

                delaySamples.clear();
            }

            this.lastFlying = now;
        }
    }
}
