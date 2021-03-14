package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class TactileGolemEntity extends AbstractGolemEntity {
	public TactileGolemEntity(EntityType<? extends TactileGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
	}
	
	@Override
	public TactileGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		TactileGolemEntity golemEntity = (TactileGolemEntity) Golemancy.TACTILE_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}