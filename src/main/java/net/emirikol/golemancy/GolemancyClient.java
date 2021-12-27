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
		registerSpawnPacket();
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
	}
	
	public void registerParticles() {
		//Register Healing Particles
		ClientPlayNetworking.registerGlobalReceiver(Particles.HEAL_PARTICLE_ID, (client, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			
			client.execute(() -> {
				Particles.spawnHealParticle(pos);
			});
		});
		//Register Food Particles
		ClientPlayNetworking.registerGlobalReceiver(Particles.FOOD_PARTICLE_ID, (client, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			int entityId = buf.readVarInt();
			
			client.execute(() -> {
				Entity e = MinecraftClient.getInstance().world.getEntityById(entityId);
				Particles.spawnFoodParticle(pos, e);
			});
		});
	}
	
	public void registerSpawnPacket() {
		ClientPlayNetworking.registerGlobalReceiver(SpawnPacket.SPAWN_PACKET_ID, (client, handler, buf, responseSender) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(buf.readVarInt());
			UUID uuid = buf.readUuid();
			int entityId = buf.readVarInt();
			Vec3d pos = SpawnPacket.readVec3d(buf);
			float pitch = SpawnPacket.readAngle(buf);
			float yaw = SpawnPacket.readAngle(buf);
			
			client.execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}
}