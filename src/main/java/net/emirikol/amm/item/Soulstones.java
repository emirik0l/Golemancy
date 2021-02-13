package net.emirikol.amm.item;

import net.emirikol.amm.*;

import net.minecraft.entity.*;

import java.util.*;

public class Soulstones {
	//Used to link entities with souls to their corresponding soulstones.
	private static final Map<EntityType,Soulstone> ENTITY_SOULSTONES = new HashMap<EntityType,Soulstone>() {{
		put(EntityType.BLAZE, AriseMyMinionsMod.SOULSTONE_BLAZE);
		put(EntityType.CAVE_SPIDER, AriseMyMinionsMod.SOULSTONE_CAVE_SPIDER);
		put(EntityType.CREEPER, AriseMyMinionsMod.SOULSTONE_CREEPER);
		put(EntityType.DROWNED, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.ELDER_GUARDIAN, AriseMyMinionsMod.SOULSTONE_GUARDIAN);
		put(EntityType.ENDERMAN, AriseMyMinionsMod.SOULSTONE_ENDERMAN);
		put(EntityType.GUARDIAN, AriseMyMinionsMod.SOULSTONE_GUARDIAN);
		put(EntityType.HUSK, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.MAGMA_CUBE, AriseMyMinionsMod.SOULSTONE_MAGMA_CUBE);
		put(EntityType.PHANTOM, AriseMyMinionsMod.SOULSTONE_PHANTOM);
		put(EntityType.RAVAGER, AriseMyMinionsMod.SOULSTONE_RAVAGER);
		put(EntityType.SKELETON, AriseMyMinionsMod.SOULSTONE_SKELETON);
		put(EntityType.SKELETON_HORSE, AriseMyMinionsMod.SOULSTONE_SKELETON);
		put(EntityType.SLIME, AriseMyMinionsMod.SOULSTONE_SLIME);
		put(EntityType.SPIDER, AriseMyMinionsMod.SOULSTONE_SPIDER);
		put(EntityType.STRAY, AriseMyMinionsMod.SOULSTONE_SKELETON);
		put(EntityType.WITHER_SKELETON, AriseMyMinionsMod.SOULSTONE_WITHER_SKELETON);
		put(EntityType.ZOMBIE, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.ZOMBIE_HORSE, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
		put(EntityType.ZOMBIE_VILLAGER, AriseMyMinionsMod.SOULSTONE_ZOMBIE);
	}};
	
	//Used to link species genes to their corresponding soulstones.
	private static final Map<String,Soulstone> SPECIES_SOULSTONES = new HashMap<String,Soulstone>() {{
		put("BLAZE", AriseMyMinionsMod.SOULSTONE_BLAZE);
		put("CAVE SPIDER", AriseMyMinionsMod.SOULSTONE_CAVE_SPIDER);
		put("CREEPER", AriseMyMinionsMod.SOULSTONE_CREEPER);
		put("ENDERMAN", AriseMyMinionsMod.SOULSTONE_ENDERMAN);
		put("GUARDIAN", AriseMyMinionsMod.SOULSTONE_GUARDIAN);
		put("MAGMA CUBE", AriseMyMinionsMod.SOULSTONE_MAGMA_CUBE);
		put("PHANTOM", AriseMyMinionsMod.SOULSTONE_PHANTOM);
		put("SKELETON", AriseMyMinionsMod.SOULSTONE_SKELETON);
		put("RAVAGER", AriseMyMinionsMod.SOULSTONE_RAVAGER);
		put("SLIME", AriseMyMinionsMod.SOULSTONE_SLIME);
		put("SPIDER", AriseMyMinionsMod.SOULSTONE_SPIDER);
		put("WITHER SKELETON", AriseMyMinionsMod.SOULSTONE_WITHER_SKELETON);
		put("ZOMBIE", AriseMyMinionsMod.SOULSTONE_ZOMBIE);
	}};
	
	public static Soulstone get(EntityType entityType) {
		return ENTITY_SOULSTONES.get(entityType);
	}
	
	public static Soulstone get(String species) {
		return SPECIES_SOULSTONES.get(species);
	}
	
	public static Set<EntityType> getEntityTypes() {
		return ENTITY_SOULSTONES.keySet();
	}
	
	public static Set<String> getSpecies() {
		return SPECIES_SOULSTONES.keySet();
	}
}