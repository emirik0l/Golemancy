package net.emirikol.amm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.world.*;

public class SummonedSpiderEntity extends SpiderEntity implements SummonedEntity {
	
	public SummonedSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
		super(entityType, world);
	}
}