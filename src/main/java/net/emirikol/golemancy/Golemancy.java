package net.emirikol.golemancy;

import net.emirikol.golemancy.block.*;
import net.emirikol.golemancy.block.entity.*;
import net.emirikol.golemancy.item.*;
import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.entity.projectile.*;
import net.emirikol.golemancy.screen.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.tool.attribute.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.fabricmc.fabric.api.screenhandler.v1.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.screen.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;

public class Golemancy implements ModInitializer {
	
	public static SoulstoneEmpty SOULSTONE_EMPTY;
	public static SoulstoneFilled SOULSTONE_FILLED;
	
	public static SoulMirror SOUL_MIRROR;
	public static ScreenHandlerType<SoulMirrorScreenHandler> SOUL_MIRROR_SCREEN_HANDLER;
	
	public static GolemWand GOLEM_WAND;
	
	public static SoulGrafterBlock SOUL_GRAFTER;
	public static BlockItem SOUL_GRAFTER_ITEM;
	public static BlockEntityType<SoulGrafterBlockEntity> SOUL_GRAFTER_ENTITY;
	public static ScreenHandlerType<SoulGrafterScreenHandler> SOUL_GRAFTER_SCREEN_HANDLER;
	
	public static ClayEffigy CLAY_EFFIGY;
	public static EntityType<ClayEffigyEntity> CLAY_EFFIGY_ENTITY;

	public static EntityType<CovetousGolemEntity> COVETOUS_GOLEM_ENTITY;
	public static EntityType<CuriousGolemEntity> CURIOUS_GOLEM_ENTITY;
	public static EntityType<EntropicGolemEntity> ENTROPIC_GOLEM_ENTITY;
	public static EntityType<HungryGolemEntity> HUNGRY_GOLEM_ENTITY;
	public static EntityType<IntrepidGolemEntity> INTREPID_GOLEM_ENTITY;
	public static EntityType<ParchedGolemEntity> PARCHED_GOLEM_ENTITY;	
	public static EntityType<RestlessGolemEntity> RESTLESS_GOLEM_ENTITY;
	public static EntityType<TactileGolemEntity> TACTILE_GOLEM_ENTITY;
	public static EntityType<ValiantGolemEntity> VALIANT_GOLEM_ENTITY;
	public static EntityType<WeepingGolemEntity> WEEPING_GOLEM_ENTITY;
	
	public static EntityType<ClayballEntity> CLAYBALL;
	
	private static float GOLEM_WIDTH = 0.7f;
	private static float GOLEM_HEIGHT = 1.30f;
	
	public static final Identifier SpawnPacketID = new Identifier("golemancy", "spawn_packet");
	
	@Override
	public void onInitialize() {
		doInstantiation();
		doRegistration();
		SoulstoneFillHandler.soulstoneFillHook(); //add event hook for replacing soulstones with mob soulstones when you kill mobs
		GolemancyItemGroup.buildGolemancyItemGroup(); ////add custom ItemGroup that contains all mod items including custom soulstones
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
		FabricItemSettings clay_effigy_settings = new FabricItemSettings();
		CLAY_EFFIGY = new ClayEffigy(clay_effigy_settings);
		CLAY_EFFIGY_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ClayEffigyEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		//Instantiate golems.
		COVETOUS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CovetousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		CURIOUS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CuriousGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		ENTROPIC_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntropicGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		HUNGRY_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, HungryGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		INTREPID_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, IntrepidGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		PARCHED_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ParchedGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		RESTLESS_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RestlessGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		TACTILE_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, TactileGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
		VALIANT_GOLEM_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ValiantGolemEntity::new).dimensions(EntityDimensions.fixed(GOLEM_WIDTH, GOLEM_HEIGHT)).build();
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
		Registry.register(Registry.ENTITY_TYPE, "golemancy:clay_effigy", CLAY_EFFIGY_ENTITY);
		FabricDefaultAttributeRegistry.register(CLAY_EFFIGY_ENTITY, ClayEffigyEntity.createClayEffigyAttributes());
		//Register golems.
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_covetous", COVETOUS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_curious", CURIOUS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_entropic", ENTROPIC_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_hungry", HUNGRY_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_intrepid", INTREPID_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_parched", PARCHED_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_restless", RESTLESS_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_tactile", TACTILE_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_valiant", VALIANT_GOLEM_ENTITY);
		Registry.register(Registry.ENTITY_TYPE, "golemancy:golem_weeping", WEEPING_GOLEM_ENTITY);
		for (EntityType type: Golems.getTypes()) {
			FabricDefaultAttributeRegistry.register(type, AbstractGolemEntity.createGolemAttributes());
		}
		//Register clayball projectile.
		Registry.register(Registry.ENTITY_TYPE, "golemancy:clayball", CLAYBALL);
	}
}
