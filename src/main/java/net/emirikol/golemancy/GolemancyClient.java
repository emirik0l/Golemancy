package net.emirikol.golemancy;

import me.shedaniel.autoconfig.AutoConfig;
import net.emirikol.golemancy.client.model.GolemEntityModel;
import net.emirikol.golemancy.client.render.GolemEntityRenderer;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.network.Particles;
import net.emirikol.golemancy.network.SpawnPacket;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.emirikol.golemancy.registry.GMObjects;
import net.emirikol.golemancy.screen.SoulGrafterScreen;
import net.emirikol.golemancy.screen.SoulMirrorScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;

import java.util.UUID;

public class GolemancyClient implements ClientModInitializer {

    public static final EntityModelLayer MODEL_GOLEM_LAYER = new EntityModelLayer(new Identifier("golemancy", "clay_golem"), "main");

    @Override
    public void onInitializeClient(ModContainer container) {
        registerEntities();
        registerParticles();
        registerSpawnPacket();
        registerConfigPacket();

        BlockRenderLayerMap.put(RenderLayer.getCutout(), GMObjects.CLAY_EFFIGY);
        BlockRenderLayerMap.put(RenderLayer.getCutout(), GMObjects.TERRACOTTA_EFFIGY);
        EntityModelLayerRegistry.registerModelLayer(MODEL_GOLEM_LAYER, GolemEntityModel::getTexturedModelData);
    }

    public void registerEntities() {
        //Register Soul Mirror Screen
        ScreenRegistry.register(Golemancy.SOUL_MIRROR_SCREEN_HANDLER, SoulMirrorScreen::new);
        //Register Soul Grafter Screen
        ScreenRegistry.register(Golemancy.SOUL_GRAFTER_SCREEN_HANDLER, SoulGrafterScreen::new);
        //Register Golem Renderers
        for (EntityType<? extends AbstractGolemEntity> type : SoulTypes.getEntityTypes()) {
            EntityRendererRegistry.register(type, GolemEntityRenderer::new);
        }
        //Register Clayball Renderer
        EntityRendererRegistry.register(GMEntityTypes.CLAYBALL, FlyingItemEntityRenderer::new);
    }

    public void registerParticles() {
        //Register Healing Particles
        ClientPlayNetworking.registerGlobalReceiver(Particles.HEAL_PARTICLE_ID, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();

            client.execute(() -> Particles.spawnHealParticle(pos));
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

            client.execute(() -> Particles.spawnSmokeParticle(pos));
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
                e.updatePosition(pos.x, pos.y, pos.z);
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
            double golemArmorValue = buf.readDouble();
            int golemAICooldown = buf.readInt();
            int golemAIRadius = buf.readInt();

            client.execute(() -> {
                ConfigurationHandler config = AutoConfig.getConfigHolder(ConfigurationHandler.class).getConfig();
                config.GRAFT_SPEED_MULTIPLIER = graftSpeedMultiplier;
                config.GRAFT_FUEL_MULTIPLIER = graftFuelMultiplier;
                config.GRAFT_POTENCY_MULTIPLIER = graftPotencyMultiplier;
                config.GOLEM_ARMOR_VALUE = golemArmorValue;
                config.GOLEM_AI_COOLDOWN = golemAICooldown;
                config.GOLEM_AI_RADIUS = golemAIRadius;
                AutoConfig.getConfigHolder(ConfigurationHandler.class).save();
            });
        });
    }
}