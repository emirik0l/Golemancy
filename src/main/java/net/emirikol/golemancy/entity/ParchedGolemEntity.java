package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
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
		this.goalSelector.add(5, new GolemFillVesselGoal(this));
		this.goalSelector.add(5, new GolemExtractItemGoal(this) {{
			add(Items.BUCKET);
			add(Items.GLASS_BOTTLE);
		}});
		this.goalSelector.add(5, new GolemDepositVesselGoal(this));
		this.goalSelector.add(6, new GolemMoveToFluidGoal(this, 5.0F));
	}
	
	@Override
	public ParchedGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ParchedGolemEntity golemEntity = Golemancy.PARCHED_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if ((uUID != null) && (golemEntity != null)) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}