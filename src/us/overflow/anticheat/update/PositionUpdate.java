package us.overflow.anticheat.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

@Getter @RequiredArgsConstructor
public final class PositionUpdate {
    private final Location from, to;
}
