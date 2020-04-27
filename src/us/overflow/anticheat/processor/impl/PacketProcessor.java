package us.overflow.anticheat.processor.impl;

import org.bukkit.Bukkit;
import us.overflow.anticheat.check.type.PacketCheck;
import us.overflow.anticheat.check.type.RotationCheck;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.*;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;
import us.overflow.anticheat.processor.type.Processor;
import us.overflow.anticheat.update.RotationUpdate;
import us.overflow.anticheat.update.head.HeadRotation;

public final class PacketProcessor implements Processor<WrappedPacket> {

    @Override
    public void process(final PlayerData playerData, final WrappedPacket packet) {
        if (packet instanceof WrappedPacketPlayInFlying) {
            final WrappedPacketPlayInFlying wrapper = (WrappedPacketPlayInFlying) packet;

            final boolean position = wrapper.isHasPos();
            final boolean looked = wrapper.isHasLook();

            if (looked) {
                final float yaw = wrapper.getYaw();
                final float pitch = wrapper.getPitch();

                final float lastYaw = playerData.getLastYaw();
                final float lastPitch = playerData.getLastPitch();

                final HeadRotation from = new HeadRotation(lastYaw, lastPitch);
                final HeadRotation to = new HeadRotation(yaw, pitch);

                final RotationUpdate rotationUpdate = new RotationUpdate(from, to);

                //noinspection unchecked
                playerData.getCheckManager().getChecks().stream().filter(RotationCheck.class::isInstance).forEach(check -> check.process(rotationUpdate));

                playerData.setLastYaw(yaw);
                playerData.setLastPitch(pitch);
            }

            if (position) {
                playerData.setStandTicks(0);
            } else {
                final int standTicks = playerData.getStandTicks();

                playerData.setStandTicks(standTicks + 1);
            }

            playerData.setClientTicks(playerData.getClientTicks() + 1);
            playerData.getActionManager().onFlying();
        } else if (packet instanceof WrappedPacketPlayInUseEntity) {
            final WrappedPacketPlayInUseEntity wrapper = (WrappedPacketPlayInUseEntity) packet;

            if (wrapper.getUseAction() == EnumEntityUseAction.ATTACK) {
                playerData.getActionManager().onAttack();
            }
        } else if (packet instanceof WrappedPacketPlayInBlockDig) {
            final WrappedPacketPlayInBlockDig wrapper = (WrappedPacketPlayInBlockDig) packet;

            switch (wrapper.getDigType()) {
                case START_DESTROY_BLOCK:
                case ABORT_DESTROY_BLOCK:
                case STOP_DESTROY_BLOCK: {
                    playerData.getActionManager().onDig();
                }
            }
        } else if (packet instanceof WrappedPacketPlayOutEntityVelocity) {
            final WrappedPacketPlayOutEntityVelocity wrapper = (WrappedPacketPlayOutEntityVelocity) packet;

            if (wrapper.getEntityId() == playerData.getPlayer().getEntityId()) {
                final double posX = wrapper.getX();
                final double posY = wrapper.getY();
                final double posZ = wrapper.getZ();

                playerData.getVelocityManager().addVelocityEntry(posX, posY, posZ);
            }
        } else if (packet instanceof WrappedPacketPlayInArmAnimation) {
            playerData.getActionManager().onSwing();
        } else if (packet instanceof WrappedPacketPlayOutPosition) {
            playerData.getActionManager().onTeleport();
        }

        //noinspection unchecked
        playerData.getCheckManager().getChecks().stream().filter(PacketCheck.class::isInstance).forEach(check -> check.process(packet));
    }
}
