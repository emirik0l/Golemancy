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
	
	public static final double GUARDIAN_POTENCY = 3.0D;
	public static final double GUARDIAN_DAMAGE = 6.0D;
	public static final double GUARDIAN_KNOCKBACK = 0.0D;
	public static final double GUARDIAN_ARMOR = 0.0D;
	public static final double GUARDIAN_MOVEMENT_SPEED = 0.5D;
	
	public static final double PHANTOM_POTENCY = 2.0D;
	public static final double PHANTOM_DAMAGE = 2.0D;
	public static final double PHANTOM_KNOCKBACK = 0.0D;
	public static final double PHANTOM_ARMOR = 0.0D;
	public static final double PHANTOM_MOVEMENT_SPEED = 0.3D;
	
	public static final double RAVAGER_POTENCY = 1.0D;
	public static final double RAVAGER_DAMAGE = 12.0D;
	public static final double RAVAGER_KNOCKBACK = 1.5D;
	public static final double RAVAGER_ARMOR = 0.0D;
	public static final double RAVAGER_MOVEMENT_SPEED = 0.3D;
	
	public static final double SKELETON_POTENCY = 3.0D;
	public static final double SKELETON_DAMAGE = 2.0D;
	public static final double SKELETON_KNOCKBACK = 0.0D;
	public static final double SKELETON_ARMOR = 0.0D;
	public static final double SKELETON_MOVEMENT_SPEED = 0.25D;
	
	public static final double SLIME_POTENCY = 3.0D;
	public static final double SLIME_DAMAGE = 0.0D; //slime damage depends on size, so this is really a modifier
	public static final double SLIME_KNOCKBACK = 0.0D;
	public static final double SLIME_ARMOR = 0.0D;
	public static final double SLIME_MOVEMENT_SPEED = 0.2D; //slime speed depends on size, so this is really a modifier
	
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
	
	public static int getNameColor(String key) {
		switch (key) {
			case "CAVE SPIDER":
				return 11826;
			case "CREEPER":
				return 4970784;
			case "ENDERMAN":
				return 8524417;
			case "GUARDIAN":
				return 6985091;
			case "PHANTOM":
				return 5267876;
			case "RAVAGER":
				return 4342079;
			case "SKELETON":
				return 8289924;
			case "SLIME":
				return 5349438;
			case "SPIDER":
				return 2629909;
			case "ZOMBIE":
				return 1729561;
			default:
				return 4210752;
		}
	}
}