package net.emirikol.golemancy;

import me.shedaniel.autoconfig.AutoConfig;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.screen.*;
import net.emirikol.golemancy.network.*;
import net.emirikol.golemancy.client.render.*;
import net.emirikol.golemancy.client.model.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.client.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;

import java.util.*;

public class GolemancyClient implements ClientModInitializer {

	public static final EntityModelLayer MODEL_GOLEM_LAYER = new EntityModelLayer(new Identifier("golemancy", "clay_golem"), "main");
	
	@Override
	public void onInitializeClient() {
		registerEntities();
		registerParticles();
		registerSpawnPacket();
		registerConfigPacket();

		BlockRenderLayerMap.INSTANCE.putBlock(Golemancy.CLAY_EFFIGY_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(Golemancy.TERRACOTTA_EFFIGY_BLOCK, RenderLayer.getCutout());
		EntityModelLayerRegistry.registerModelLayer(MODEL_GOLEM_LAYER, GolemEntityModel::getTexturedModelData);
	}
	
	public void registerEntities() {
		//Register Soul Mirror Screen
		ScreenRegistry.register(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, SoulMirrorScreen::new);
		//Register Soul Grafter Screen
		ScreenRegistry.register(Golemancy.SOUL_GRAFTER_SCREEN_HANDLER, SoulGrafterScreen::new);
		//Register Golem Renderers
		for(EntityType<? extends AbstractGolemEntity> type: SoulTypes.getEntityTypes()) {
			EntityRendererRegistry.register(type, GolemEntityRenderer::new);
		}
		//Register Clayball Renderer
		EntityRendererRegistry.register(Golemancy.CLAYBALL, FlyingItemEntityRenderer::new);
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
				if (e != null) Particles.spawnFoodParticle(pos, e);
			});
		});
		//Register Smoke Particles
		ClientPlayNetworking.registerGlobalReceiver(Particles.SMOKE_PARTICLE_ID, (client, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();

			client.execute(() -> {
				Particles.spawnSmokeParticle(pos);
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

	public void registerConfigPacket() {
		//Used to sync golemancy config between server and client.
		ClientPlayNetworking.registerGlobalReceiver(Golemancy.ConfigPacketID, (client, handler, buf, responseSender) -> {
			float graftSpeedMultiplier = buf.readFloat();
			float graftFuelMultiplier = buf.readFloat();
			float graftPotencyMultiplier = buf.readFloat();
			double terracottaArmorValue = buf.readDouble();
			int golemAICooldown = buf.readInt();

			client.execute(() -> {
				GolemancyConfig config = AutoConfig.getConfigHolder(GolemancyConfig.class).getConfig();
				config.GRAFT_SPEED_MULTIPLIER = graftSpeedMultiplier;
				config.GRAFT_FUEL_MULTIPLIER = graftFuelMultiplier;
				config.GRAFT_POTENCY_MULTIPLIER = graftPotencyMultiplier;
				config.TERRACOTTA_ARMOR_VALUE = terracottaArmorValue;
				config.GOLEM_AI_COOLDOWN = golemAICooldown;
				AutoConfig.getConfigHolder(GolemancyConfig.class).save();
			});
		});
	}
}