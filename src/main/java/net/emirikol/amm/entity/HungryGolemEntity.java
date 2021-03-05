package net.emirikol.amm.entity;

import net.emirikol.amm.*;
import net.emirikol.amm.entity.goal.*;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class HungryGolemEntity extends AbstractGolemEntity {
	public HungryGolemEntity(EntityType<? extends HungryGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(5, new GolemEatHeldItemGoal(this));
		this.goalSelector.add(6, new GolemMoveToItemGoal(this, 10.0F));
	}
	
	@Override
	public HungryGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		HungryGolemEntity golemEntity = (HungryGolemEntity) AriseMyMinionsMod.HUNGRY_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}