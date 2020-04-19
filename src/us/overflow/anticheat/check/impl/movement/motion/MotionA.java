package us.overflow.anticheat.check.impl.movement.motion;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;
import us.overflow.anticheat.alert.type.ViolationLevel;
import us.overflow.anticheat.check.CheckData;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.WrappedPacket;
import us.overflow.anticheat.packet.type.WrappedPacketPlayInFlying;
import us.overflow.anticheat.utils.MathUtil;

@CheckData(name = "Motion (A)")
public final class MotionA extends PacketCheck {

    public MotionA(final PlayerData playerData) {
        super(playerData);
    }

    private boolean lastOnGround, onGround;
    private double fromPosY, posY;
    private int jumpPotionTicks;

    @Override
    public void process(WrappedPacket wrappedPacket) {

        if (wrappedPacket instanceof WrappedPacketPlayInFlying) {
            WrappedPacketPlayInFlying wrappedPacketPlayInFlying = (WrappedPacketPlayInFlying) wrappedPacket;

            if (wrappedPacketPlayInFlying.isHasPos()) {

                //TODO: fix some falses when jumping up a hill for a long time...

                if (getPlayerData().getPlayer().hasPotionEffect(PotionEffectType.JUMP)) {
                    if (jumpPotionTicks < 20) jumpPotionTicks++;
                } else {
                    if (jumpPotionTicks > 0) jumpPotionTicks--;
                }

                if (!onGround && lastOnGround) {

                    double delta = (posY - fromPosY);

                    double max = 0.41999998688697815F;

                    if (jumpPotionTicks > 0) {
                        max = (max + MathUtil.getPotionEffectLevel(getPlayerData().getPlayer(), PotionEffectType.JUMP) * 0.1F);
                    }

                    if (Math.abs(delta) > 0.0 && ((delta > max || delta < max))) {
                        this.handleViolation().addViolation(ViolationLevel.MEDIUM).create();
                    }
                }

                lastOnGround = onGround;
                onGround = wrappedPacketPlayInFlying.isOnGround();

                fromPosY = posY;
                posY = wrappedPacketPlayInFlying.getY();
            }
        }
    }
}