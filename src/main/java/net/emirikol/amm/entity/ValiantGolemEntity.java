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

public class ValiantGolemEntity extends AbstractGolemEntity {
	public ValiantGolemEntity(EntityType<? extends ValiantGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
	}
	
	@Override
	public ValiantGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ValiantGolemEntity golemEntity = (ValiantGolemEntity) AriseMyMinionsMod.VALIANT_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}