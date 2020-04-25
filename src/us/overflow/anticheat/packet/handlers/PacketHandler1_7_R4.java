package us.overflow.anticheat.packet.handlers;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.entity.Entity;
import us.overflow.anticheat.data.PlayerData;
import us.overflow.anticheat.packet.type.*;
import us.overflow.anticheat.packet.type.enums.EnumEntityUseAction;
import us.overflow.anticheat.packet.type.enums.EnumPlayerAction;
import us.overflow.anticheat.packet.type.enums.EnumPlayerDigType;
import us.overflow.anticheat.utils.ReflectionUtil;

public final class PacketHandler1_7_R4 extends PlayerConnection {
    private final PlayerData playerData;

    public PacketHandler1_7_R4(final MinecraftServer minecraftserver, final NetworkManager networkmanager, final EntityPlayer entityplayer, final PlayerData playerData) {
        super(minecraftserver, networkmanager, entityplayer);

        this.playerData = playerData;
    }

    @Override
    public void a(final PacketPlayInFlying packet) {
        super.a(packet);

        final double posX = packet.c();
        final double posY = packet.d();
        final double posZ = packet.e();

        final float yaw = packet.g();
        final float pitch = packet.h();

        final boolean look = packet.k();
        final boolean position = packet.j();
        final boolean ground = packet.i();

        final WrappedPacketPlayInFlying wrapper = new WrappedPacketPlayInFlying(posX, posY, posZ, position, look, ground, yaw, pitch);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInUseEntity packet) {
        super.a(packet);

        EnumEntityUseAction entityUseAction = EnumEntityUseAction.NONE;
        WrappedPacketPlayInUseEntity wrapper = new WrappedPacketPlayInUseEntity();

        switch (packet.c()) {
            case ATTACK: {
                entityUseAction = EnumEntityUseAction.ATTACK;
                final Entity entity = packet.a(player.world).getBukkitEntity();

                wrapper.setEntity(entity);
                break;
            }

            case INTERACT: {
                entityUseAction = EnumEntityUseAction.INTERACT;
                break;
            }
        }

        wrapper.setUseAction(entityUseAction);
        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInArmAnimation packet) {
        super.a(packet);

        final long timestamp = System.currentTimeMillis();

        final WrappedPacketPlayInArmAnimation wrapper = new WrappedPacketPlayInArmAnimation(timestamp);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInEntityAction packet) {
        super.a(packet);

        EnumPlayerAction enumPlayerAction = EnumPlayerAction.NONE;

        switch (packet.d()) {
            case 1: {
                enumPlayerAction = EnumPlayerAction.START_SNEAKING;
                break;
            }

            case 2: {
                enumPlayerAction = EnumPlayerAction.STOP_SNEAKING;
                break;
            }

            case 3: {
                enumPlayerAction = EnumPlayerAction.START_SPRINTING;
                break;
            }

            case 4: {
                enumPlayerAction = EnumPlayerAction.STOP_SPRINTING;
                break;
            }
        }

        final WrappedPacketPlayInEntityAction wrapper = new WrappedPacketPlayInEntityAction();

        wrapper.setUseAction(enumPlayerAction);
        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInAbilities packet) {
        super.a(packet);

        final boolean flying = packet.isFlying();
        final boolean canFly = packet.c();

        final WrappedPacketPlayInAbilities wrapper = new WrappedPacketPlayInAbilities(flying, canFly);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInTransaction packet) {
        super.a(packet);

        final short id = packet.d();
        final int data = packet.c();

        final WrappedPacketPlayInTransaction wrapper = new WrappedPacketPlayInTransaction(id, data);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInBlockDig packet) {
        super.a(packet);

        EnumPlayerDigType enumPlayerDigType = EnumPlayerDigType.NONE;

        switch (packet.c()) {
            case 0: {
                enumPlayerDigType = EnumPlayerDigType.START_DESTROY_BLOCK;
                break;
            }

            case 2: {
                enumPlayerDigType = EnumPlayerDigType.STOP_DESTROY_BLOCK;
                break;
            }

            case 1: {
                enumPlayerDigType = EnumPlayerDigType.ABORT_DESTROY_BLOCK;
                break;
            }

            case 3: {
                enumPlayerDigType = EnumPlayerDigType.DROP_ALL_ITEMS;
                break;
            }

            case 4: {
                enumPlayerDigType = EnumPlayerDigType.DROP_ITEM;
                break;
            }

            case 5: {
                enumPlayerDigType = EnumPlayerDigType.RELEASE_USE_ITEM;
                break;
            }
        }

        final WrappedPacketPlayInBlockDig wrapper = new WrappedPacketPlayInBlockDig(enumPlayerDigType);

        wrapper.parse(playerData);
    }

    @Override
    public void a(final PacketPlayInHeldItemSlot packet) {
        super.a(packet);

        final int slot = packet.c();

        final WrappedPacketPlayInHeldItemSlot wrapper = new WrappedPacketPlayInHeldItemSlot(slot);

        wrapper.parse(playerData);
    }

    @Override
    public void sendPacket(final Packet packet) {
        super.sendPacket(packet);

        if (packet instanceof PacketPlayOutEntityVelocity) {
            final Class clazz = PacketPlayOutEntityVelocity.class;

            final int entityId = ReflectionUtil.getFieldValue(clazz, "a", int.class, packet);

            final double posX = Math.abs(ReflectionUtil.getFieldValue(clazz, "b", int.class, packet)) / 8000.0;
            final double posY = ((int) ReflectionUtil.getFieldValue(clazz, "c", int.class, packet)) / 8000.0;
            final double posZ = Math.abs(ReflectionUtil.getFieldValue(clazz, "d", int.class, packet)) / 8000.0;

            final WrappedPacketPlayOutEntityVelocity wrapper = new WrappedPacketPlayOutEntityVelocity(entityId, posX, posY, posZ);

            wrapper.parse(playerData);
        }
    }
}
