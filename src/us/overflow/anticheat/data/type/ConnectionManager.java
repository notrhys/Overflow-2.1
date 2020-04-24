package us.overflow.anticheat.data.type;

import us.overflow.anticheat.utils.EvictingMap;

public final class ConnectionManager {
    private final EvictingMap<Long, Long> sentKeepAlives = new EvictingMap<>(20);
    private final EvictingMap<Short, Long> sentTransactions = new EvictingMap<>(20);

    private long packets;

    public void onFlying() {
        final long packetNext = ++packets;
        final short packetId = (short) (packetNext & Short.MAX_VALUE);

        sentKeepAlives.put(packetNext, System.currentTimeMillis());
        sentTransactions.put(packetId, System.currentTimeMillis());
    }

    public void onTransaction(final short action) {
        final boolean found = sentTransactions.containsKey(action);

        // No need to account for spoofed transactions
        if (found) {

        }
    }

    public void onKeepAlive(final long action) {
        final boolean found = sentKeepAlives.containsKey(action);

        // No need to account for spoofed keep alives
        if (found) {

        }
    }
}
