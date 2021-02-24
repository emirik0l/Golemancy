package net.emirikol.amm;

import net.emirikol.amm.genetics.*;

import net.fabricmc.fabric.api.entity.event.v1.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class SoulstoneFillHandler {
	public static void soulstoneFillHook() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killed) -> {
			//Check if a player killed them and if the target's soul can be captured.
			if (checkPlayer(entity) && checkSoul(killed)) {
				System.out.println("Entity killed");
			}
		});
	}
	
	//Check if an entity is a player.
	private static boolean checkPlayer(Entity entity) {
		if (entity instanceof PlayerEntity) { return true; }
		return false;
	}
	
	//Check if an entity has a capturable soul.
	private static boolean checkSoul(LivingEntity entity) {
		EntityType entityType = entity.getType();
		for (EntityType key : Genomes.getEntityTypes()) {
			if (entityType == key) {
				return true;
			}
		}
		return false;
	}
}