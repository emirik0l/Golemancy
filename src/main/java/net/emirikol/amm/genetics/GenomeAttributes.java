package net.emirikol.amm.genetics;

import net.emirikol.amm.*;
import net.emirikol.amm.item.*;

import java.util.*;

public class GenomeAttributes {
	public static final double BLAZE_POTENCY = 2.0D;
	public static final double BLAZE_DAMAGE = 6.0D;
	public static final double BLAZE_KNOCKBACK = 0.0D;
	public static final double BLAZE_ARMOR = 0.0D;
	public static final double BLAZE_MOVEMENT_SPEED = 0.23D;
	
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
	
	public static final double MAGMA_CUBE_POTENCY = 3.0D;
	public static final double MAGMA_CUBE_DAMAGE = 2.0D; 
	public static final double MAGMA_CUBE_KNOCKBACK = 0.0D;
	public static final double MAGMA_CUBE_ARMOR = 3.0D;
	public static final double MAGMA_CUBE_MOVEMENT_SPEED = 0.2D; 
	
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
	public static final double SLIME_DAMAGE = 0.0D;
	public static final double SLIME_KNOCKBACK = 0.0D;
	public static final double SLIME_ARMOR = 0.0D;
	public static final double SLIME_MOVEMENT_SPEED = 0.2D; 
	
	public static final double SPIDER_POTENCY = 3.0D;
	public static final double SPIDER_DAMAGE = 2.0D;
	public static final double SPIDER_KNOCKBACK = 0.0D;
	public static final double SPIDER_ARMOR = 0.0D;
	public static final double SPIDER_MOVEMENT_SPEED = 0.3D;
	
	public static final double WITHER_SKELETON_POTENCY = 2.0D;
	public static final double WITHER_SKELETON_DAMAGE = 4.0D;
	public static final double WITHER_SKELETON_KNOCKBACK = 0.0D;
	public static final double WITHER_SKELETON_ARMOR = 0.0D;
	public static final double WITHER_SKELETON_MOVEMENT_SPEED = 0.25D;
	
	public static final double ZOMBIE_POTENCY = 4.0D;
	public static final double ZOMBIE_DAMAGE = 3.0D;
	public static final double ZOMBIE_KNOCKBACK = 0.0D;
	public static final double ZOMBIE_ARMOR = 2.0D;
	public static final double ZOMBIE_MOVEMENT_SPEED = 0.23D;
	
	
	public static int getNameColor(String key) {
		switch (key) {
			case "BLAZE":
				return 0xfc9600;
			case "CAVE SPIDER":
				return 0x2E32;
			case "CREEPER":
				return 0x4BD920;
			case "ENDERMAN":
				return 0x821281;
			case "GUARDIAN":
				return 0x6A9583;
			case "MAGMA CUBE":
				return 0x520000;
			case "PHANTOM":
				return 0x5061A4;
			case "RAVAGER":
				return 0x42413F;
			case "SKELETON":
				return 0x7E7E84;
			case "SLIME":
				return 0x51A03E;
			case "SPIDER":
				return 0x282115;
			case "WITHER SKELETON":
				return 0x0;
			case "ZOMBIE":
				return 0x1A6419;
			default:
				return 0x404040;
		}
	}
}