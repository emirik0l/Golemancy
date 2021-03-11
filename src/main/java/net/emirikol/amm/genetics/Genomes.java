package net.emirikol.amm.genetics;

import net.minecraft.entity.*;
import net.minecraft.text.*;

import java.util.*;

public class Genomes {
	
	public static final Genome GENERIC = new Genome() {{
		put("type", new Gene<String>("Generic"));
		put("potency", new Gene<Integer>(1));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(0);
	}};
	
	public static final Genome BLAZE = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(2));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Genome CREEPER = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(2));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Genome ENDERMAN = new Genome() {{
		put("type", new Gene<String>("Covetous"));
		put("potency", new Gene<Integer>(1));
		put("strength", new Gene<Integer>(2));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(2));
		put("smarts", new Gene<Integer>(1));
		setTextureId(4);
	}};
	
	public static final Genome GHAST = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(2));
		setTextureId(1);
	}};
	
	public static final Genome GUARDIAN = new Genome() {{
		put("type", new Gene<String>("Valiant"));
		put("potency", new Gene<Integer>(2));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(2));
		put("smarts", new Gene<Integer>(0));
		setTextureId(5);
	}};
	
	public static final Genome HOGLIN = new Genome() {{
		put("type", new Gene<String>("Hungry"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(1));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(2));
		put("smarts", new Gene<Integer>(0));
		setTextureId(3);
	}};
	
	public static final Genome PHANTOM = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(2));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(2));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(1));
		setTextureId(1);
	}};
	
	public static final Genome RAVAGER = new Genome() {{
		put("type", new Gene<String>("Valiant"));
		put("potency", new Gene<Integer>(2));
		put("strength", new Gene<Integer>(2));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(3));
		put("smarts", new Gene<Integer>(0));
		setTextureId(5);
	}};
	
	public static final Genome SHULKER = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(1));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(2));
		put("smarts", new Gene<Integer>(1));
	}};
	
	public static final Genome SILVERFISH = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(5));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Genome SKELETON = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Genome SLIME = new Genome() {{
		put("type", new Gene<String>("Curious"));
		put("potency", new Gene<Integer>(4));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(2);
	}};
	
	public static final Genome SPIDER = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(2));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Genome WITHER_SKELETON = new Genome() {{
		put("type", new Gene<String>("Valiant"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(2));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
		setTextureId(5);
	}};
	
	public static final Genome ZOMBIE = new Genome() {{
		put("type", new Gene<String>("Restless"));
		put("potency", new Gene<Integer>(3));
		put("strength", new Gene<Integer>(1));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(1));
		put("smarts", new Gene<Integer>(0));
		setTextureId(1);
	}};
	
	public static final Map<EntityType,Genome> GENOMES = new HashMap<EntityType,Genome>() {{
		put(EntityType.BLAZE, BLAZE);
		put(EntityType.CREEPER, CREEPER);
		put(EntityType.ENDERMAN, ENDERMAN);
		put(EntityType.GHAST, GHAST);
		put(EntityType.GUARDIAN, GUARDIAN);
		put(EntityType.ELDER_GUARDIAN, GUARDIAN);
		put(EntityType.HOGLIN, HOGLIN);
		put(EntityType.PHANTOM, PHANTOM);
		put(EntityType.RAVAGER, RAVAGER);
		put(EntityType.SHULKER, SHULKER);
		put(EntityType.SILVERFISH, SILVERFISH);
		put(EntityType.ENDERMITE, SILVERFISH);
		put(EntityType.SKELETON, SKELETON);
		put(EntityType.SKELETON_HORSE, SKELETON);
		put(EntityType.STRAY, SKELETON);
		put(EntityType.MAGMA_CUBE, SLIME);
		put(EntityType.SLIME, SLIME);
		put(EntityType.CAVE_SPIDER, SPIDER);
		put(EntityType.SPIDER, SPIDER);
		put(EntityType.WITHER_SKELETON, WITHER_SKELETON);
		put(EntityType.DROWNED, ZOMBIE);
		put(EntityType.HUSK, ZOMBIE);
		put(EntityType.ZOGLIN, ZOMBIE);
		put(EntityType.ZOMBIE, ZOMBIE);
		put(EntityType.ZOMBIE_HORSE, ZOMBIE);
		put(EntityType.ZOMBIE_VILLAGER, ZOMBIE);
		put(EntityType.ZOMBIFIED_PIGLIN, ZOMBIE);
	}};
	
	public static Set<EntityType> getEntityTypes() {
		return GENOMES.keySet();
	}
	
	public static Genome get(EntityType entityType) {
		return GENOMES.get(entityType);
	}
}