package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class CuriousGolemEntity extends AbstractGolemEntity {
	public CuriousGolemEntity(EntityType<? extends CuriousGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(16, new GolemFindBlockGoal(this, 10.0F));
	}
	
	@Override
	public CuriousGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		CuriousGolemEntity golemEntity = Golemancy.CURIOUS_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if ((uUID != null) && (golemEntity != null)) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}