package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.passive.*;

public class GolemFollowWandGoal extends GolemFollowOwnerGoal {
	
	public GolemFollowWandGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance) {
		super(tameable, speed, minDistance, maxDistance);
	}
	
	@Override
	public boolean canStart() {
		if (!(this.tameable instanceof AbstractGolemEntity)) {
			return false;
		}
		AbstractGolemEntity golemEntity = (AbstractGolemEntity) this.tameable;
		if (golemEntity.isFollowingWand()) {
			super.canStart();
		}
		return false;
	}
}