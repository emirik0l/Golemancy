package net.emirikol.amm;

import net.emirikol.amm.block.*;
import net.emirikol.amm.block.entity.*;
import net.emirikol.amm.item.*;
import net.emirikol.amm.entity.*;
import net.emirikol.amm.screen.*;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.fabricmc.fabric.api.screenhandler.v1.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.screen.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;

public class AriseMyMinionsMod implements ModInitializer {

	public static Soulstone SOULSTONE;
	public static SoulstoneBlaze SOULSTONE_BLAZE;
	public static SoulstoneCaveSpider SOULSTONE_CAVE_SPIDER;
	public static SoulstoneCreeper SOULSTONE_CREEPER;
	public static SoulstoneEnderman SOULSTONE_ENDERMAN;
	public static SoulstoneGuardian SOULSTONE_GUARDIAN;
	public static SoulstonePhantom SOULSTONE_PHANTOM;
	public static SoulstoneRavager SOULSTONE_RAVAGER;
	public static SoulstoneSkeleton SOULSTONE_SKELETON;
	public static SoulstoneSlime SOULSTONE_SLIME;
	public static SoulstoneSpider SOULSTONE_SPIDER;
	public static SoulstoneWitherSkeleton SOULSTONE_WITHER_SKELETON;
	public static SoulstoneZombie SOULSTONE_ZOMBIE;
	
	public static ClayEffigy CLAY_EFFIGY;
	public static EntityType<ClayEffigyEntity> CLAY_EFFIGY_ENTITY;
	
	public static SoulMirror SOUL_MIRROR;
	public static ScreenHandlerType<SoulMirrorScreenHandler> SOUL_MIRROR_SCREEN_HANDLER;
	
	public static SoulGrafterBlock SOUL_GRAFTER;
	public static BlockItem SOUL_GRAFTER_ITEM;
	public static BlockEntityType<SoulGrafterBlockEntity> SOUL_GRAFTER_ENTITY;
	public static ScreenHandlerType<SoulGrafterScreenHandler> SOUL_GRAFTER_SCREEN_HANDLER;
	
	@Override
	public void onInitialize() {
		doInstantiation();
		doRegistration();
		SoulstoneFillHandler.soulstoneFillHook(); //add event hook for replacing soulstones with mob soulstones when you kill mobs
	}
	
	public static void doInstantiation() {
		//Instantiate Soulstones
		FabricItemSettings soulstone_settings = new FabricItemSettings();
		soulstone_settings.group(ItemGroup.MISC);
		SOULSTONE = new Soulstone(soulstone_settings);
		SOULSTONE_BLAZE = new SoulstoneBlaze(soulstone_settings);
		SOULSTONE_CAVE_SPIDER = new SoulstoneCaveSpider(soulstone_settings);
		SOULSTONE_CREEPER = new SoulstoneCreeper(soulstone_settings);
		SOULSTONE_ENDERMAN = new SoulstoneEnderman(soulstone_settings);
		SOULSTONE_GUARDIAN = new SoulstoneGuardian(soulstone_settings);
		SOULSTONE_PHANTOM = new SoulstonePhantom(soulstone_settings);
		SOULSTONE_RAVAGER = new SoulstoneRavager(soulstone_settings);
		SOULSTONE_SKELETON = new SoulstoneSkeleton(soulstone_settings);
		SOULSTONE_SLIME = new SoulstoneSlime(soulstone_settings);
		SOULSTONE_SPIDER = new SoulstoneSpider(soulstone_settings);
		SOULSTONE_WITHER_SKELETON = new SoulstoneWitherSkeleton(soulstone_settings);
		SOULSTONE_ZOMBIE = new SoulstoneZombie(soulstone_settings);
		//Instantiate Clay Effigy
		FabricItemSettings clay_effigy_settings = new FabricItemSettings();
		clay_effigy_settings.group(ItemGroup.MISC);
		CLAY_EFFIGY = new ClayEffigy(clay_effigy_settings);
		CLAY_EFFIGY_ENTITY = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ClayEffigyEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.0f)).build();
		//Instantiate Soul Mirror
		FabricItemSettings soul_mirror_settings = new FabricItemSettings();
		soul_mirror_settings.group(ItemGroup.MISC);
		soul_mirror_settings.maxCount(1);
		soul_mirror_settings.maxDamage(256);
		SOUL_MIRROR = new SoulMirror(soul_mirror_settings);
		SOUL_MIRROR_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("amm", "soul_mirror"), SoulMirrorScreenHandler::new);
		//Instantiate Soul Grafter
		FabricBlockSettings soul_grafter_settings = FabricBlockSettings.of(Material.STONE);
		soul_grafter_settings.hardness(4.0F).strength(5.0F, 1200.0F);
		soul_grafter_settings.requiresTool();
		SOUL_GRAFTER = new SoulGrafterBlock(soul_grafter_settings);
		FabricItemSettings soul_grafter_item_settings = new FabricItemSettings();
		soul_grafter_item_settings.group(ItemGroup.MISC);
		SOUL_GRAFTER_ITEM = new BlockItem(SOUL_GRAFTER, soul_grafter_item_settings);
		SOUL_GRAFTER_ENTITY = BlockEntityType.Builder.create(SoulGrafterBlockEntity::new, SOUL_GRAFTER).build(null);
		SOUL_GRAFTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("amm", "soul_grafter"), SoulGrafterScreenHandler::new);
	}
	
	public static void doRegistration() {
		//Register Soulstones
		Registry.register(Registry.ITEM, "amm:soulstone", SOULSTONE);
		Registry.register(Registry.ITEM, "amm:soulstone_blaze", SOULSTONE_BLAZE);
		Registry.register(Registry.ITEM, "amm:soulstone_cave_spider", SOULSTONE_CAVE_SPIDER);
		Registry.register(Registry.ITEM, "amm:soulstone_creeper", SOULSTONE_CREEPER);
		Registry.register(Registry.ITEM, "amm:soulstone_enderman", SOULSTONE_ENDERMAN);
		Registry.register(Registry.ITEM, "amm:soulstone_guardian", SOULSTONE_GUARDIAN);
		Registry.register(Registry.ITEM, "amm:soulstone_phantom", SOULSTONE_PHANTOM);
		Registry.register(Registry.ITEM, "amm:soulstone_ravager", SOULSTONE_RAVAGER);
		Registry.register(Registry.ITEM, "amm:soulstone_skeleton", SOULSTONE_SKELETON);
		Registry.register(Registry.ITEM, "amm:soulstone_slime", SOULSTONE_SLIME);
		Registry.register(Registry.ITEM, "amm:soulstone_spider", SOULSTONE_SPIDER);
		Registry.register(Registry.ITEM, "amm:soulstone_wither_skeleton", SOULSTONE_WITHER_SKELETON);
		Registry.register(Registry.ITEM, "amm:soulstone_zombie", SOULSTONE_ZOMBIE);
		//Register Clay Effigy
		Registry.register(Registry.ITEM, "amm:clay_effigy", CLAY_EFFIGY);
		Registry.register(Registry.ENTITY_TYPE, "amm:clay_effigy", CLAY_EFFIGY_ENTITY);
		FabricDefaultAttributeRegistry.register(CLAY_EFFIGY_ENTITY, ClayEffigyEntity.createClayEffigyAttributes());
		//Register Soul Mirror
		Registry.register(Registry.ITEM, "amm:soul_mirror", SOUL_MIRROR);
		//Register Soul Grafter
		Registry.register(Registry.BLOCK, "amm:soul_grafter", SOUL_GRAFTER);
		Registry.register(Registry.ITEM, "amm:soul_grafter", SOUL_GRAFTER_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, "amm:soul_grafter", SOUL_GRAFTER_ENTITY);
	}
}
