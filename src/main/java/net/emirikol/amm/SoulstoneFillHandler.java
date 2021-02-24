package net.emirikol.amm;

import net.fabricmc.fabric.api.entity.event.v1.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class SoulstoneFillHandler {
	public static void soulstoneFillHook() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killed) -> {
			System.out.println("Entity killed");
		});
	}
}