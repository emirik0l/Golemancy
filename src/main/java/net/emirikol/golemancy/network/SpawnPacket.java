package net.emirikol.golemancy.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class SpawnPacket {
    public static final Identifier SPAWN_PACKET_ID = new Identifier("golemancy", "spawn_packet");

    public static void sendSpawnPacket(Entity target) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(target.getType()));
        buf.writeUuid(target.getUuid());
        buf.writeVarInt(target.getId());

        writeVec3d(buf, target.getPos());
        writeAngle(buf, target.getPitch());
        writeAngle(buf, target.getYaw());

        for (ServerPlayerEntity user : PlayerLookup.tracking((ServerWorld) target.world, target.getBlockPos())) {
            ServerPlayNetworking.send(user, SPAWN_PACKET_ID, buf);
        }
    }

    public static void writeVec3d(PacketByteBuf byteBuf, Vec3d vec3d) {
        byteBuf.writeDouble(vec3d.x);
        byteBuf.writeDouble(vec3d.y);
        byteBuf.writeDouble(vec3d.z);
    }

    public static Vec3d readVec3d(PacketByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        return new Vec3d(x, y, z);
    }

    public static void writeAngle(PacketByteBuf byteBuf, float angle) {
        byteBuf.writeByte(packAngle(angle));
    }

    public static float readAngle(PacketByteBuf byteBuf) {
        return unpackAngle(byteBuf.readByte());
    }

    public static byte packAngle(float angle) {
        return (byte) MathHelper.floor(angle * 256 / 360);
    }


    public static float unpackAngle(byte angleByte) {
        return (angleByte * 360) / 256f;
    }
}