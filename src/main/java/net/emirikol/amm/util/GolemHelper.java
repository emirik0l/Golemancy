package net.emirikol.amm.util;

import net.minecraft.text.*;

import java.util.*;

public class GolemHelper {
	private static final Map<String,Integer> TEXTURE_VARIANTS = new HashMap<String,Integer>() {{
		put("Restless", 1);
		put("Curious", 2);
		put("Hungry", 3);
		put("Covetous", 4);
		put("Valiant", 5);
	}};
	
	public static Integer getSoulstoneVariant(String type) {
		return TEXTURE_VARIANTS.get(type);
	}
	
	public static TranslatableText getGolemText(String type) {
		return new TranslatableText("text.amm.type." + type.toLowerCase());
	}
}