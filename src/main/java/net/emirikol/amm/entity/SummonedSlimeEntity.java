package net.emirikol.amm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.world.*;

public class SummonedSlimeEntity extends SlimeEntity implements SummonedEntity {
	
	public SummonedSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
		super(entityType, world);
	}
}