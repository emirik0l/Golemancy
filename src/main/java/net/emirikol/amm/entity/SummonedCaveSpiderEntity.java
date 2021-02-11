package net.emirikol.amm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.world.*;

public class SummonedCaveSpiderEntity extends CaveSpiderEntity implements SummonedEntity {
	
	public SummonedCaveSpiderEntity(EntityType<? extends CaveSpiderEntity> entityType, World world) {
		super(entityType, world);
	}
}