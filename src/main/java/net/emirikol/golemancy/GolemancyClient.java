package net.emirikol.golemancy;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.screen.*;
import net.emirikol.golemancy.network.*;
import net.emirikol.golemancy.client.render.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.*;
import net.fabricmc.fabric.api.client.screenhandler.v1.*;
import net.fabricmc.fabric.api.network.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.entity.*;
import net.minecraft.particle.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.client.*;
import net.minecraft.client.render.entity.*;

import java.util.*;

public class GolemancyClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		registerEntities();
		registerParticles();
	}
	
	public void registerEntities() {
		//Register Soul Mirror Screen
		ScreenRegistry.register(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, SoulMirrorScreen::new);
		//Register Soul Grafter Screen
		ScreenRegistry.register(Golemancy.SOUL_GRAFTER_SCREEN_HANDLER, SoulGrafterScreen::new);
		//Register Clay Effigy Renderer
		EntityRendererRegistry.INSTANCE.register(Golemancy.CLAY_EFFIGY_ENTITY, (dispatcher, context) -> {
			return new ClayEffigyEntityRenderer(dispatcher);
		});
		//Register Golem Renderers
		for(EntityType type: Golems.getTypes()) {
			EntityRendererRegistry.INSTANCE.register(type, (dispatcher, context) -> {
				return new ClayGolemEntityRenderer(dispatcher);
			});
		}
		//Register Clayball Renderer
		EntityRendererRegistry.INSTANCE.register(Golemancy.CLAYBALL, (dispatcher, context) -> {
			return new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer());
		});

		//Register Spawn Packet
		ClientSidePacketRegistry.INSTANCE.register(Golemancy.SpawnPacketID, (ctx, byteBuf) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = EntitySpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = EntitySpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.getTaskQueue().execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.pitch = pitch;
				e.yaw = yaw;
				e.setEntityId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}
	
	public void registerParticles() {
		//Register Healing Particles
		ClientPlayNetworking.registerGlobalReceiver(Particles.HEAL_PARTICLE_ID, (client, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			Random rand = MinecraftClient.getInstance().world.getRandom();
			
			client.execute(() -> {
				for(int i = 0; i<15; i++) {
					double d = 0.7D;
					double g = 1.0D;
					double h = rand.nextGaussian() * 0.02D;
					double j = rand.nextGaussian() * 0.02D;
					double k = rand.nextGaussian() * 0.02D;
					double l = 0.5D - d;
					double m = (double)pos.getX() + rand.nextDouble() * d;
					double n = (double)pos.getY() + rand.nextDouble() * g;
					double o = (double)pos.getZ() + rand.nextDouble() * d;
					MinecraftClient.getInstance().world.addParticle(ParticleTypes.HAPPY_VILLAGER, m, n, o, h, j, k);
				}
			});
		});
	}
}