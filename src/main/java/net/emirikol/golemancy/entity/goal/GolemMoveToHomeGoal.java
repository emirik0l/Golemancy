package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;
import net.minecraft.util.math.BlockPos;

public class GolemMoveToHomeGoal extends GolemMoveGoal {
	
	public GolemMoveToHomeGoal(AbstractGolemEntity entity) {
		// Search radius and y difference are ignored for GolemMoveToHomeGoal, as it should always try to return home.
		super(entity, 0.0F);
	}

	@Override
	public boolean findTargetPos() {
		this.targetPos = this.entity.getLinkedBlockPos();
		if (this.targetPos == null) { return false; }
		return this.entity.isInWalkTargetRange(this.targetPos);
	}

	@Override
	public boolean canReachPos(BlockPos pos) {
		//Golems should always try to get as close to their home block as possible, even if they can't reach it.
		return true;
	}
}