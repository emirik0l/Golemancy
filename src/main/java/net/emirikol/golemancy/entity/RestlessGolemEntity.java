package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.GolemDanceGoal;
import net.emirikol.golemancy.entity.goal.GolemMoveToPickupFlowerGoal;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class RestlessGolemEntity extends AbstractGolemEntity {
	public RestlessGolemEntity(EntityType<? extends RestlessGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(7, new GolemMoveToPickupFlowerGoal(this, 5.0F));
		this.goalSelector.add(6, new GolemDanceGoal(this));
	}
	
	@Override
	public RestlessGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		RestlessGolemEntity golemEntity = Golemancy.RESTLESS_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if ((uUID != null) && (golemEntity != null)) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}