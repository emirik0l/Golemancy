package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

public class GolemFollowWandGoal extends GolemFollowOwnerGoal {

	public GolemFollowWandGoal(AbstractGolemEntity entity, double speed, float minDistance, float maxDistance) {
		super(entity, speed, minDistance, maxDistance);
	}
	
	@Override
	public boolean canStart() {
		return this.entity.isFollowingWand() && super.canStart();
	}
}
