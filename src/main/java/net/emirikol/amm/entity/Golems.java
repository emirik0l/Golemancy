package net.emirikol.amm.entity;

import net.emirikol.amm.*;

import net.minecraft.entity.*;

import java.util.*;

public class Golems {
	
	public static EntityType get(String key) {
		return GOLEMS.get(key);
	}
	
	private static final Map<String,EntityType> GOLEMS = new HashMap<String,EntityType>() {{
		put("Covetous", AriseMyMinionsMod.COVETOUS_GOLEM_ENTITY);
		put("Curious", AriseMyMinionsMod.CURIOUS_GOLEM_ENTITY);
		put("Hungry", AriseMyMinionsMod.HUNGRY_GOLEM_ENTITY);
		put("Restless", AriseMyMinionsMod.RESTLESS_GOLEM_ENTITY);
	}};
}