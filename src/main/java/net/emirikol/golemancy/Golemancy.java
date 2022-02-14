package net.emirikol.golemancy;

import net.emirikol.golemancy.block.*;
import net.emirikol.golemancy.block.entity.*;
import net.emirikol.golemancy.genetics.SoulTypes;
import net.emirikol.golemancy.item.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.entity.projectile.*;
import net.emirikol.golemancy.screen.*;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import net.emirikol.golemancy.test.Tests;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.screenhandler.v1.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.screen.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Golemancy implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Golemancy");

	public static SoulstoneEmpty SOULSTONE_EMPTY;
	public static SoulstoneFilled SOULSTONE_FILLED;

	public static SoulMirror SOUL_MIRROR;
	public static ScreenHandlerType<SoulMirrorScreenHandler> SOUL_MIRROR_SCREEN_HANDLER;

	public static GolemWand GOLEM_WAND;

	public static SoulGrafterBlock SOUL_GRAFTER;
	public static BlockItem SOUL_GRAFTER_ITEM;
	public static BlockEntityType<SoulGrafterBlockEntity> SOUL_GRAFTER_ENTITY;
	public static ScreenHandlerType<SoulGrafterScreenHandler> SOUL_GRAFTER_SCREEN_HANDLER;

	public static BlockItem CLAY_EFFIGY;
	public static ClayEffigyBlock CLAY_EFFIGY_BLOCK;

	public static BlockItem TERRACOTTA_EFFIGY;
	public static TerracottaEffigyBlock TERRACOTTA_EFFIGY_BLOCK;

	public static BlockItem OBSIDIAN_EFFIGY;
	public static ObsidianEffigyBlock OBSIDIAN_EFFIGY_BLOCK;

	public static EntityType<CarefulGolemEntity> CAREFUL_GOLEM_ENTITY;
	public static EntityType<CovetousGolemEntity> COVETOUS_GOLEM_ENTITY;
	public static EntityType<CuriousGolemEntity> CURIOUS_GOLEM_ENTITY;
	public static EntityType<EntropicGolemEntity> ENTROPIC_GOLEM_ENTITY;
	public static EntityType<HungryGolemEntity> HUNGRY_GOLEM_ENTITY;
	public static EntityType<IntrepidGolemEntity> INTREPID_GOLEM_ENTITY;
	public static EntityType<MarshyGolemEntity> MARSHY_GOLEM_ENTITY;
	public static EntityType<ParchedGolemEntity> PARCHED_GOLEM_ENTITY;
	public static EntityType<RestlessGolemEntity> RESTLESS_GOLEM_ENTITY;
	public static EntityType<RusticGolemEntity> RUSTIC_GOLEM_ENTITY;
	public static EntityType<TactileGolemEntity> TACTILE_GOLEM_ENTITY;
	public static EntityType<ValiantGolemEntity> VALIANT_GOLEM_ENTITY;
	public static EntityType<VerdantGolemEntity> VERDANT_GOLEM_ENTITY;
	public static EntityType<WeepingGolemEntity> WEEPING_GOLEM_ENTITY;

	public static EntityType<ClayballEntity> CLAYBALL;

	private static final float GOLEM_WIDTH = 0.7f;
	private static final float GOLEM_HEIGHT = 1.30f;

	public static final Identifier ConfigPacketID = new Identifier("golemancy", "config_packet");

	@Override
	public void onInitialize() {
		doInstantiation();
		doRegistration();
		registerCommands();
		SoulstoneFillHandler.soulstoneFillHook(); //add event hook for replacing soulstones with mob soulstones when you kill mobs
		GolemancyConfig.syncConfigHook(); //add event hook for syncing server and client configs when a player connects
		GolemancyItemGroup.buildGolemancyItemGroup(); ////add custom ItemGroup that contains all mod items including custom soulstones
		AutoConfig.register(GolemancyConfig.class, GsonConfigSerializer::new); //register the AutoConfig handler - see GolemancyConfig for details
		LOGGER.info("Arise, my minions!");
	}

	public static void doInstantiation() {
		//Instantiate soulstones.
		FabricItemSettings soulstone_settings = new FabricItemSettings();
		SOULSTONE_EMPTY = new SoulstoneEmpty(soulstone_settings);
		SOULSTONE_FILLED = new SoulstoneFilled(soulstone_settings);
		//Instantiate soul mirror.
		FabricItemSettings soul_mirror_settings = new FabricItemSettings();
		soul_mirror_settings.maxCount(1);
		soul_mirror_settings.maxDamage(256);
		SOUL_MIRROR = new SoulMirror(soul_mirror_settings);
		SOUL_MIRROR_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("golemancy", "soul_mirror"), SoulMirrorScreenHandler::new);
		//Instantiate golem wand.
		FabricItemSettings golem_wand_settings = new FabricItemSettings();
		golem_wand_settings.maxCount(1);
		GOLEM_WAND = new GolemWand(golem_wand_settings);
		//Instantiate soul grafter.
		FabricBlockSettings soul_grafter_settings = FabricBlockSettings.of(Material.STONE);
		soul_grafter_settings.hardness(4.0F).strength(5.0F, 1200.0F);
		soul_grafter_settings.requiresTool();
		SOUL_GRAFTER = new SoulGrafterBlock(soul_grafter_settings);
		FabricItemSettings soul_grafter_item_settings = new FabricItemSettings();
		SOUL_GRAFTER_ITEM = new BlockItem(SOUL_GRAFTER, soul_grafter_item_settings);
		SOUL_GRAFTER_ENTITY = FabricBlockEntityTypeBuilder.create(SoulGrafterBlockEntity::new, SOUL_GRAFTER).build(null);
		SOUL_GRAFTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("golemancy", "soul_grafter"), SoulGrafterScreenHandler::new);
		//Instantiate clay effigy.
		FabricBlockSettings clay_effigy_settings = FabricBlockSettings.of(Material.DECORATION);
		clay_effigy_settings.strength(0.6F).nonOpaque();
		CLAY_EFFIGY_BLOCK = new ClayEffigyBlock(clay_effigy_settings);
		FabricItemSettings effigy_settings = new FabricItemSettings();
		effigy_settings.group(null);
		CLAY_EFFIGY = new BlockItem(CLAY_EFFIGY_BLOCK, effigy_settings);
		//Instantiate terracotta effigy.
		TERRACOTTA_EFFIGY_BLOCK = new TerracottaEffigyBlock(clay_effigy_settings);
		TERRACOTTA_EFFIGY = new BlockItem(TERRACOTTA_EFFIGY_BLOCK, effigy_settings);
		//Instantiate obsidian effigy.
		OBSIDIAN_EFFIGY_BLOCK = new ObsidianEffigyBlock(clay_effigy_settings);
		OBSIDIAN_EFFIGY = new BlockItem(OBSIDIAN_EFFIGY_BLOCK, effigy_settings);
		//Instantiate golems.
		CAREFUL_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CarefulGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		COVETOUS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CovetousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		CURIOUS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CuriousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		ENTROPIC_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntropicGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		HUNGRY_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HungryGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		INTREPID_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, IntrepidGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		MARSHY_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MarshyGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		PARCHED_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ParchedGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		RESTLESS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RestlessGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		RUSTIC_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RusticGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		TACTILE_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TactileGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		VALIANT_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ValiantGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		VERDANT_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, VerdantGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		WEEPING_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WeepingGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		//Instantiate clayball projectile.
		CLAYBALL = FabricEntityTypeBuilder.<ClayballEntity>create(SpawnGroup.MISC, ClayballEntity::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();
	}

	public static void doRegistration() {
		//Register soulstones.
		Registry.register(Registry.ITEM, "golemancy:soulstone_empty", SOULSTONE_EMPTY);
		Registry.register(Registry.ITEM, "golemancy:soulstone_filled", SOULSTONE_FILLED);
		//Register soul mirror.
		Registry.register(Registry.ITEM, "golemancy:soul_mirror", SOUL_MIRROR);
		//Register golem wand.
		Registry.register(Registry.ITEM, "golemancy:golem_wand", GOLEM_WAND);
		//Register soul grafter.
		Registry.register(Registry.BLOCK, "golemancy:soul_grafter", SOUL_GRAFTER);
		Registry.register(Registry.ITEM, "golemancy:soul_grafter", SOUL_GRAFTER_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, "golemancy:soul_grafter", SOUL_GRAFTER_ENTITY);
		//Register clay effigy.
		Registry.register(Registry.ITEM, "golemancy:clay_effigy", CLAY_EFFIGY);
		Registry.register(Registry.BLOCK, "golemancy:clay_effigy", CLAY_EFFIGY_BLOCK);
		//Register terracotta effigy.
		Registry.register(Registry.ITEM, "golemancy:terracotta_effigy", TERRACOTTA_EFFIGY);
		Registry.register(Registry.BLOCK, "golemancy:terracotta_effigy", TERRACOTTA_EFFIGY_BLOCK);
		//Register obsidian effigy.
		Registry.register(Registry.ITEM, "golemancy:obsidian_effigy", OBSIDIAN_EFFIGY);
		Registry.register(Registry.BLOCK, "golemancy:obsidian_effigy", OBSIDIAN_EFFIGY_BLOCK);
		//Register golems.
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_careful", CAREFUL_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_covetous", COVETOUS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_curious", CURIOUS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_entropic", ENTROPIC_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_hungry", HUNGRY_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_intrepid", INTREPID_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_marshy", MARSHY_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_parched", PARCHED_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_restless", RESTLESS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_rustic", RUSTIC_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_tactile", TACTILE_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_valiant", VALIANT_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_verdant", VERDANT_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_weeping", WEEPING_GOLEM_ENTITY);
		for (EntityType<? extends AbstractGolemEntity> type: SoulTypes.getEntityTypes()) {
			FabricDefaultAttributeRegistry.register(type, AbstractGolemEntity.createGolemAttributes());
		}
		//Register clayball projectile.
		Registry.register(Registry.ENTITY_TYPE, "golemancy:clayball", CLAYBALL);
	}

	public static void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			// Command to run all test suites.
			// For best results, run on a superflat world in creative.
			dispatcher.register(CommandManager.literal("golemancytest").executes(context -> {
				ServerCommandSource source = context.getSource();
				Tests.runAll(source);
				return 0;
			}));
		});
	}
}
