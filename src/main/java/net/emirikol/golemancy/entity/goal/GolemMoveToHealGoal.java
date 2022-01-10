package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;

import java.util.*;

public class GolemMoveToHealGoal extends GolemMoveGoal {
	private TameableEntity friend;
	
	public GolemMoveToHealGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	public GolemMoveToHealGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
		super(entity, searchRadius, maxYDifference);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	public void tick() {
		//Look at friend.
		this.entity.getLookControl().lookAt(this.friend, 10.0F, (float)this.entity.getMaxLookPitchChange());
		//Continue towards targetPos.
		super.tick();
	}

	@Override
	public boolean findTargetPos() {
		float r = this.searchRadius + (10.0F * this.entity.getGolemSmarts());
		List<TameableEntity> list = entity.world.getEntitiesByClass(TameableEntity.class, entity.getBoundingBox().expand(r,this.maxYDifference,r), (entity) -> true);
		for (TameableEntity tameable: list) {
			if (wounded(tameable) && (this.entity.getOwner() == tameable.getOwner())) {
				this.friend = tameable;
				this.targetPos = tameable.getBlockPos();
				return true;
			}
		}
		return false;
	}
	
	public boolean wounded(LivingEntity entity) {
		return entity.getHealth() < entity.getMaxHealth();
	}
}
