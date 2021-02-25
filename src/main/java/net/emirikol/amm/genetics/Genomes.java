package net.emirikol.amm.genetics;

import net.minecraft.entity.*;

import java.util.*;

public class Genomes {
	
	public static final Genome GENERIC = new Genome() {{
		put("type", new Gene<String>("RESTLESS"));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
	}};
	
	public static final Genome SKELETON = new Genome() {{
		put("type", new Gene<String>("RESTLESS"));
		put("strength", new Gene<Integer>(0));
		put("agility", new Gene<Integer>(1));
		put("vigor", new Gene<Integer>(0));
		put("smarts", new Gene<Integer>(0));
	}};
	
	public static final Genome ZOMBIE = new Genome() {{
		put("type", new Gene<String>("RESTLESS"));
		put("strength", new Gene<Integer>(1));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(1));
		put("smarts", new Gene<Integer>(0));
	}};
	
	public static final Map<EntityType,Genome> GENOMES = new HashMap<EntityType,Genome>() {{
		put(EntityType.SKELETON, SKELETON);
		put(EntityType.SKELETON_HORSE, SKELETON);
		put(EntityType.STRAY, SKELETON);
		put(EntityType.DROWNED, ZOMBIE);
		put(EntityType.HUSK, ZOMBIE);
		put(EntityType.ZOGLIN, ZOMBIE);
		put(EntityType.ZOMBIE, ZOMBIE);
		put(EntityType.ZOMBIE_HORSE, ZOMBIE);
		put(EntityType.ZOMBIE_VILLAGER, ZOMBIE);
	}};
	
	public static Set<EntityType> getEntityTypes() {
		return GENOMES.keySet();
	}
	
	public static Genome get(EntityType entityType) {
		return GENOMES.get(entityType);
	}

}