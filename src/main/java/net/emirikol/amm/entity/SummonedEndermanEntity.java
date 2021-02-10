package net.emirikol.amm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.world.*;

public class SummonedEndermanEntity extends EndermanEntity implements SummonedEntity {
	
	public SummonedEndermanEntity(EntityType<? extends EndermanEntity> entityType, World world) {
		super(entityType, world);
	}
}