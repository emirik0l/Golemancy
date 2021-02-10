package net.emirikol.amm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.world.*;

public class SummonedZombieEntity extends ZombieEntity implements SummonedEntity {
	
	public SummonedZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}
}