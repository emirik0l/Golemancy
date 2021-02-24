package net.emirikol.amm;

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
	
	@Override
	public void onInitialize() {
		doInstantiation();
		doRegistration();
	}
	
	public static void doInstantiation() {
	}
	
	public static void doRegistration() {
	}
}
