package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;

import net.minecraft.entity.*;

import java.util.*;

public class Golems {
	
	public static EntityType get(String key) {
		return GOLEMS.get(key);
	}
	
	public static Collection<EntityType> getTypes() {
		return GOLEMS.values();
	}
	
	private static final Map<String,EntityType> GOLEMS = new HashMap<String,EntityType>() {{
		put("Covetous", Golemancy.COVETOUS_GOLEM_ENTITY);
		put("Curious", Golemancy.CURIOUS_GOLEM_ENTITY);
		put("Entropic", Golemancy.ENTROPIC_GOLEM_ENTITY);
		put("Hungry", Golemancy.HUNGRY_GOLEM_ENTITY);
		put("Intrepid", Golemancy.INTREPID_GOLEM_ENTITY);
		put("Parched", Golemancy.PARCHED_GOLEM_ENTITY);
		put("Restless", Golemancy.RESTLESS_GOLEM_ENTITY);
		put("Tactile", Golemancy.TACTILE_GOLEM_ENTITY);
		put("Valiant", Golemancy.VALIANT_GOLEM_ENTITY);
	}};
}