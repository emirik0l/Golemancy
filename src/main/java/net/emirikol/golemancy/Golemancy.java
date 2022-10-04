package net.emirikol.golemancy;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.emirikol.golemancy.block.entity.SoulGrafterBlockEntity;
import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.CommandRegistrationHandler;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.emirikol.golemancy.event.SoulstoneFillHandler;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.registry.GMEntityTypes;
import net.emirikol.golemancy.registry.GMObjects;
import net.emirikol.golemancy.screen.SoulGrafterScreenHandler;
import net.emirikol.golemancy.screen.SoulMirrorScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Golemancy implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Golemancy");
    public static final Identifier ConfigPacketID = new Identifier("golemancy", "config_packet");

    public static ScreenHandlerType<SoulMirrorScreenHandler> SOUL_MIRROR_SCREEN_HANDLER =  ScreenHandlerRegistry.registerExtended(new GMIdentifier("soul_mirror"), SoulMirrorScreenHandler::new);
    public static BlockEntityType<SoulGrafterBlockEntity> SOUL_GRAFTER_ENTITY = FabricBlockEntityTypeBuilder.create(SoulGrafterBlockEntity::new, GMObjects.SOUL_GRAFTER).build(null);
    public static ScreenHandlerType<SoulGrafterScreenHandler> SOUL_GRAFTER_SCREEN_HANDLER =  ScreenHandlerRegistry.registerSimple(new GMIdentifier("soul_grafter"), SoulGrafterScreenHandler::new);


    @Override
    public void onInitialize(ModContainer container) {
        GMObjects.register();
        GMEntityTypes.register();
        Registry.register(Registry.BLOCK_ENTITY_TYPE, "golemancy:soul_grafter", SOUL_GRAFTER_ENTITY);
        for (EntityType<? extends AbstractGolemEntity> type : SoulTypes.getEntityTypes()) {
            FabricDefaultAttributeRegistry.register(type, AbstractGolemEntity.createGolemAttributes());
        }
        CommandRegistrationHandler.commandRegistrationHook(); //add event hook for registering this mod's commands
        SoulstoneFillHandler.soulstoneFillHook(); //add event hook for replacing soulstones with mob soulstones when you kill mobs
        ConfigurationHandler.syncConfigHook(); //add event hook for syncing server and client configs when a player connects
        //buildGolemancyItemGroup(); ////add custom ItemGroup that contains all mod items including custom soulstones
        AutoConfig.register(ConfigurationHandler.class, GsonConfigSerializer::new); //register the AutoConfig handler - see GolemancyConfig for details
        LOGGER.info("Arise, my minions!");
    }
}
