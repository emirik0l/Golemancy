package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

public class GolemMoveToBreakGoal extends GolemMoveToBlockGoal {
	public GolemMoveToBreakGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius);
	}
	
	@Override
	public boolean shouldContinue() {
		if (isValidPos(this.targetPos)) {
			return true;
		} else {
			return super.shouldContinue();
		}
	}
	
	@Override
	public boolean isValidPos(BlockPos pos) {
		BlockState state = this.entity.world.getBlockState(pos);
		if (state == null) { return false; }
		BlockPos linkedPos = this.entity.getLinkedBlockPos();
		if (linkedPos == null) { return false; }
		BlockState linkedState = this.entity.world.getBlockState(linkedPos);
		if (linkedState == null) { return false; }
		
		return (state.getBlock() == linkedState.getBlock()) && !pos.equals(linkedPos) && GolemHelper.canReach(this.entity, pos) && super.isValidPos(pos);
	}
}