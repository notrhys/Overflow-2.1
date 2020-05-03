package us.overflow.anticheat.check.impl.flight;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.BlockUtil;
import us.overflow.anticheat.utils.Verbose;

@CheckData(name = "Flight (F)")
public final class FlightF extends PacketCheck {

    private double lastY;


    private int airTicks;

    public FlightF(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) packet;

            if (wrappedPacketPlayInFlying.isHasPos() && (System.currentTimeMillis() - playerData.getLastJoin()) > 1000L) {

                /*
                    Hate this fucking check i legit wanna kill my self at this point i have 0 fucking motivation for this shit anymore thinking about putting a fork into the 200wat outlet
                 */

                if (getPlayerData().getPlayer().isFlying() || getPlayerData().getPlayer().getAllowFlight() || playerData.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    airTicks = 0;
                    return;
                }

                boolean yGround = wrappedPacketPlayInFlying.getY() % 0.015625 == 0.0;

                boolean clientGround = wrappedPacketPlayInFlying.isOnGround();

                if (!clientGround) {
                    if (airTicks < 20) airTicks++;
                } else {
                    airTicks = 0;
                }

                double diff = (lastY - wrappedPacketPlayInFlying.getY());


                if (!clientGround && Math.abs(diff) < 0.0009f && airTicks > 15 && !playerData.getPositionManager().getBelowBlocks().get() && !playerData.getPositionManager().getTouchingLiquid().get() && !playerData.getPositionManager().getTouchingHalfBlocks().get() && !playerData.getPositionManager().getTouchingSlime().get() && !playerData.getPositionManager().getTouchingClimbable().get()) {
             //       this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }

                if ((yGround && !clientGround) && !playerData.getPositionManager().getTouchingSlab().get() && !BlockUtil.isSlab(playerData.getPlayer()) && !playerData.getPositionManager().getBelowBlocks().get() && !playerData.getPositionManager().getTouchingLiquid().get() && !playerData.getPositionManager().getTouchingHalfBlocks().get() && !playerData.getPositionManager().getTouchingSlime().get() && !playerData.getPositionManager().getTouchingClimbable().get()) {
           //         this.handleViolation().addViolation(ViolationLevel.LOW).create();
                }

                lastY = wrappedPacketPlayInFlying.getY();
            }
        }
    }
}
