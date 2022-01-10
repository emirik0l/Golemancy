package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

public class GolemMoveToHomeGoal extends GolemMoveGoal {
	
	public GolemMoveToHomeGoal(AbstractGolemEntity entity) {
		// Search radius and y difference are ignored for GolemMoveToHomeGoal, as it should always try to return home.
		super(entity, 0.0F, 0.0F);
	}

	@Override
	public boolean findTargetPos() {
		this.targetPos = this.entity.getLinkedBlockPos();
		if (this.targetPos == null) { return false; }
		return this.entity.isInWalkTargetRange(this.targetPos);
	}
}