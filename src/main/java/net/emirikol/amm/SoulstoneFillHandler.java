package net.emirikol.amm;

import net.emirikol.amm.item.*;

import net.fabricmc.fabric.api.entity.event.v1.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import java.util.*;

public class SoulstoneFillHandler {

	//Used to link entities with souls to their corresponding soulstones.
	public static Map<EntityType,Soulstone> VALID_ENTITIES = new HashMap<EntityType,Soulstone>() {{
		put(EntityType.CREEPER, AriseMyMinionsMod.SOULSTONE_CREEPER);
		put(EntityType.DROWNED, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.ENDERMAN, AriseMyMinionsMod.SOULSTONE_ENDERMAN);
		put(EntityType.HUSK, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.SKELETON, AriseMyMinionsMod.SOULSTONE_SKELETON);
		put(EntityType.SPIDER, AriseMyMinionsMod.SOULSTONE_SPIDER);
		put(EntityType.ZOMBIE, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
	}};

	public static void soulstoneFillHook() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killed) -> {
			//Check if a player killed them and if the target's soul can be captured.
			if (checkPlayer(entity) && checkSoul(killed)) {
				//Check if the player has empty soulstones.
				PlayerEntity playerEntity = (PlayerEntity) entity;
				PlayerInventory inventory = playerEntity.inventory;
				if (checkSoulstones(inventory)) {
					//Remove a soulstone and add the correct type.
					ItemStack soulstoneStack = getSoulstones(inventory);
					ItemStack newSoulstone = new ItemStack(getSoulstoneFromEntity(killed));
					//Set up default NBT data
					Soulstone newSoulstoneItem = (Soulstone) newSoulstone.getItem();
					newSoulstoneItem.defaultGenes(newSoulstone);
					//Give to player
					soulstoneStack.decrement(1);
					inventory.offerOrDrop(world, newSoulstone);
					inventory.markDirty();
				}
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
		for (EntityType key : VALID_ENTITIES.keySet()) {
			if (entityType == key) {
				return true;
			}
		}
		return false;
	}
	
	//Check if a PlayerInventory contains soulstones.
	private static boolean checkSoulstones(PlayerInventory inventory) {
		if (inventory.contains(new ItemStack(AriseMyMinionsMod.SOULSTONE))){ 
			return true;
		}
		return false;
	}
	
	//Get the ItemStack in a PlayerInventory that contains soulstones.
	private static ItemStack getSoulstones(PlayerInventory inventory) {
		for(ItemStack stack: inventory.main) {
			if (stack.getItem() == AriseMyMinionsMod.SOULSTONE) {
				return stack;
			}
		}
		return null;
	}
	
	//Get the correct soulstone for the given entity.
	private static Item getSoulstoneFromEntity(LivingEntity entity) {
		EntityType entityType = entity.getType();
		for (EntityType key : VALID_ENTITIES.keySet()) {
			if (entityType == key) {
				return VALID_ENTITIES.get(key);
			}
		}
		return null;
	}
}