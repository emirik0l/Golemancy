package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class EntropicGolemEntity extends AbstractGolemEntity {
	public EntropicGolemEntity(EntityType<? extends EntropicGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(6, new GolemMoveToBreakGoal(this, 10.0F, 2.0F));
	}
	
	@Override
	public EntropicGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		EntropicGolemEntity golemEntity = Golemancy.ENTROPIC_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if ((uUID != null) && (golemEntity != null)) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}