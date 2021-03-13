package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
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
		this.goalSelector.add(5, new GolemBreakBlockGoal(this));
	}
	
	@Override
	public EntropicGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		EntropicGolemEntity golemEntity = (EntropicGolemEntity) Golemancy.ENTROPIC_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}