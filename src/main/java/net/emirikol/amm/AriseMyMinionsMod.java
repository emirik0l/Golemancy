package net.emirikol.amm;

import net.emirikol.amm.block.*;
import net.emirikol.amm.block.entity.*;
import net.emirikol.amm.item.*;
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
	
	public static SoulstoneEmpty SOULSTONE_EMPTY;
	public static SoulstoneFilled SOULSTONE_FILLED;
	
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
		//Instantiate soulstones.
		FabricItemSettings soulstone_settings = new FabricItemSettings();
		soulstone_settings.group(ItemGroup.MISC);
		SOULSTONE_EMPTY = new SoulstoneEmpty(soulstone_settings);
		SOULSTONE_FILLED = new SoulstoneFilled(soulstone_settings);
		//Instantiate soul mirror.
		FabricItemSettings soul_mirror_settings = new FabricItemSettings();
		soul_mirror_settings.group(ItemGroup.MISC);
		soul_mirror_settings.maxCount(1);
		soul_mirror_settings.maxDamage(256);
		SOUL_MIRROR = new SoulMirror(soul_mirror_settings);
		SOUL_MIRROR_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("amm", "soul_mirror"), SoulMirrorScreenHandler::new);
		//Instantiate soul grafter.
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
		//Register soulstones.
		Registry.register(Registry.ITEM, "amm:soulstone_empty", SOULSTONE_EMPTY);
		Registry.register(Registry.ITEM, "amm:soulstone_filled", SOULSTONE_FILLED);
		//Register soul mirror.
		Registry.register(Registry.ITEM, "amm:soul_mirror", SOUL_MIRROR);
		//Register soul grafter.
		Registry.register(Registry.BLOCK, "amm:soul_grafter", SOUL_GRAFTER);
		Registry.register(Registry.ITEM, "amm:soul_grafter", SOUL_GRAFTER_ITEM);
		Registry.register(Registry.BLOCK_ENTITY_TYPE, "amm:soul_grafter", SOUL_GRAFTER_ENTITY);
	}
}
