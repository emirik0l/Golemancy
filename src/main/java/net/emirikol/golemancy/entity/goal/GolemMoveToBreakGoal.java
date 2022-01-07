package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.util.math.*;

public class GolemMoveToBreakGoal extends GolemMoveGoal {
	public GolemMoveToBreakGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius, 1);
	}
	
	public GolemMoveToBreakGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
		super(entity, searchRadius, maxYDifference);
	}
	
	@Override
	public boolean shouldContinue() {
		if (isTargetPos(this.targetPos)) {
			return true;
		} else {
			return super.shouldContinue();
		}
	}
	
	@Override
	public boolean isTargetPos(BlockPos pos) {
		BlockState state = this.entity.world.getBlockState(pos);
		if (state == null) { return false; }
		Block linkedBlock = this.entity.getLinkedBlock();
		if (linkedBlock == null) { return false; }
		
		return (state.getBlock() == linkedBlock) && super.isTargetPos(pos);
	}
}