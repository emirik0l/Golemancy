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

public class ParchedGolemEntity extends AbstractGolemEntity {
	public ParchedGolemEntity(EntityType<? extends ParchedGolemEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override 
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(5, new GolemDrinkFluidGoal(this) {{ 
			add(Blocks.WATER);
		}});
		this.goalSelector.add(6, new GolemMoveToBlockGoal(this, 10.0F) {{
			add(Blocks.WATER);
		}});
	}
	
	@Override
	public ParchedGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ParchedGolemEntity golemEntity = (ParchedGolemEntity) Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}