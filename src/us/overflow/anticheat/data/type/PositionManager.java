package us.overflow.anticheat.data.type;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.*;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.Observable;
import us.overflow.anticheat.utils.Cuboid;

@Getter
public final class PositionManager {
    // We're using observables for better performance and also for thread control
    private Observable<Boolean> touchingAir = new Observable<>(false);
    private Observable<Boolean> touchingClimbable = new Observable<>(false);
    private Observable<Boolean> touchingLiquid = new Observable<>(false);
    private Observable<Boolean> touchingHalfBlocks = new Observable<>(false);
    private Observable<Boolean> touchingIllegalBlocks = new Observable<>(false);
    private Observable<Boolean> touchingFence = new Observable<>(false);
    private Observable<Boolean> touchingDoor = new Observable<>(false);

    public void updatePositionFlags(final Location to) {
        final Cuboid cuboid = new Cuboid(to).expand(0.0625, 0.0625, 0.0625).move(0.0, -0.55, 0.0);

        this.touchingAir.set(false);
        this.touchingClimbable.set(false);
        this.touchingLiquid.set(false);
        this.touchingHalfBlocks.set(false);
        this.touchingIllegalBlocks.set(false);
        this.touchingFence.set(false);
        this.touchingDoor.set(false);

        // Running on a diff thread to minimize load
        OverflowAPI.INSTANCE.getPositionExecutor().execute(() -> {
            final boolean touchingAir = cuboid.checkBlocks(to.getWorld(), material -> material == Material.AIR);
            final boolean touchingClimbable = cuboid.checkBlocks(to.getWorld(), material -> material == Material.VINE || material == Material.LADDER);
            final boolean touchingLiquid = cuboid.checkBlocks(to.getWorld(), material -> material == Material.LAVA || material == Material.WATER || material == Material.STATIONARY_LAVA || material == Material.STATIONARY_WATER);
            final boolean touchingHalfBlocks = cuboid.checkBlocks(to.getWorld(), material -> material.getData() == Stairs.class || material.getData() == Step.class || material.getData() == WoodenStep.class || material == Material.STONE_SLAB2);
            final boolean touchingIllegalBlocks = cuboid.checkBlocks(to.getWorld(), material -> material.getData() == Skull.class || material.getData() == FlowerPot.class || material == Material.CAULDRON || material == Material.WATER_LILY);
            final boolean touchingFence = cuboid.checkBlocks(to.getWorld(), material -> material == Material.FENCE || material == Material.FENCE_GATE);
            final boolean touchingDoor = cuboid.checkBlocks(to.getWorld(), material -> material.getData() == Gate.class);

            this.touchingAir.set(touchingAir);
            this.touchingLiquid.set(touchingLiquid);
            this.touchingClimbable.set(touchingClimbable);
            this.touchingHalfBlocks.set(touchingHalfBlocks);
            this.touchingIllegalBlocks.set(touchingIllegalBlocks);
            this.touchingFence.set(touchingFence);
            this.touchingDoor.set(touchingDoor);
        });
    }
}
