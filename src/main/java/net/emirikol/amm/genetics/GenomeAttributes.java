package net.emirikol.amm.genetics;

import net.emirikol.amm.*;
import net.emirikol.amm.item.*;

import java.util.*;

public class GenomeAttributes {
	public static final double CAVE_SPIDER_POTENCY = 4.0D;
	public static final double CAVE_SPIDER_DAMAGE = 2.0D;
	public static final double CAVE_SPIDER_KNOCKBACK = 0.0D;
	public static final double CAVE_SPIDER_ARMOR = 0.0D;
	public static final double CAVE_SPIDER_MOVEMENT_SPEED = 0.3D;

	public static final double CREEPER_POTENCY = 2.0D;
	public static final double CREEPER_DAMAGE = 2.0D;
	public static final double CREEPER_KNOCKBACK = 0.0D;
	public static final double CREEPER_ARMOR = 0.0D;
	public static final double CREEPER_MOVEMENT_SPEED = 0.25D;
	
	public static final double ENDERMAN_POTENCY = 1.0D;
	public static final double ENDERMAN_DAMAGE = 7.0D;
	public static final double ENDERMAN_KNOCKBACK = 0.0D;
	public static final double ENDERMAN_ARMOR = 0.0D;
	public static final double ENDERMAN_MOVEMENT_SPEED = 0.3D;
	
	public static final double SKELETON_POTENCY = 3.0D;
	public static final double SKELETON_DAMAGE = 2.0D;
	public static final double SKELETON_KNOCKBACK = 0.0D;
	public static final double SKELETON_ARMOR = 0.0D;
	public static final double SKELETON_MOVEMENT_SPEED = 0.25D;
	
	public static final double SPIDER_POTENCY = 2.0D;
	public static final double SPIDER_DAMAGE = 2.0D;
	public static final double SPIDER_KNOCKBACK = 0.0D;
	public static final double SPIDER_ARMOR = 0.0D;
	public static final double SPIDER_MOVEMENT_SPEED = 0.3D;
	
	public static final double ZOMBIE_POTENCY = 4.0D;
	public static final double ZOMBIE_DAMAGE = 3.0D;
	public static final double ZOMBIE_KNOCKBACK = 0.0D;
	public static final double ZOMBIE_ARMOR = 2.0D;
	public static final double ZOMBIE_MOVEMENT_SPEED = 0.23D;
	
	public static final Map<String,Soulstone> SPECIES_SOULSTONES = new HashMap() {{
		put("CAVE SPIDER", AriseMyMinionsMod.SOULSTONE_CAVE_SPIDER);
		put("CREEPER", AriseMyMinionsMod.SOULSTONE_CREEPER);
		put("ENDERMAN", AriseMyMinionsMod.SOULSTONE_ENDERMAN);
		put("SKELETON", AriseMyMinionsMod.SOULSTONE_SKELETON);
		put("SPIDER", AriseMyMinionsMod.SOULSTONE_SPIDER);
		put("ZOMBIE", AriseMyMinionsMod.SOULSTONE_ZOMBIE);
	}};
}