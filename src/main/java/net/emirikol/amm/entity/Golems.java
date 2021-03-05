package net.emirikol.amm.entity;

import net.minecraft.entity.*;

import java.util.*;

public class Golems {
	private static final Map<String,EntityType> GOLEMS = new HashMap<String,EntityType>() {{
	}};
	
	public static EntityType get(String key) {
		return GOLEMS.get(key);
	}
}