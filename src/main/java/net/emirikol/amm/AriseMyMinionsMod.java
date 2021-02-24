package net.emirikol.amm;

import net.emirikol.amm.item.*;

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
	}
	
	public static void doRegistration() {
		//Register soulstones.
		Registry.register(Registry.ITEM, "amm:soulstone_empty", SOULSTONE_EMPTY);
		Registry.register(Registry.ITEM, "amm:soulstone_filled", SOULSTONE_FILLED);
	}
}
