package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
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
		this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.add(5, new GolemExtractToolGoal(this));
		this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
		this.targetSelector.add(2, new AttackWithOwnerGoal(this));
		this.targetSelector.add(3, new RevengeGoal(this, AbstractGolemEntity.class).setGroupRevenge(new Class[0]));
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof CreeperEntity);
		}));
	}
	
	@Override
	public ValiantGolemEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ValiantGolemEntity golemEntity = Golemancy.VALIANT_GOLEM_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();

		if ((uUID != null) && (golemEntity != null)) {
			golemEntity.setOwnerUuid(uUID);
			golemEntity.setTamed(true);
		}
		return golemEntity;
	}
}