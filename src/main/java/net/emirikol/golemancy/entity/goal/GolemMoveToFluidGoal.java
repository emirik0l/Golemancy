package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

public class GolemMoveToFluidGoal extends GolemMoveToBlockGoal {
	public GolemMoveToFluidGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius, 1);
	}
	
	public GolemMoveToFluidGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
		super(entity, searchRadius, maxYDifference);
	}
	
	@Override
	public boolean canStart() {
		return GolemHelper.hasEmptyBucket(this.entity) && super.canStart();
	}
	
	@Override
	public boolean isTargetPos(BlockPos pos) {
		ServerWorld world = (ServerWorld) this.entity.world;
		FluidState fluidState = world.getBlockState(pos).getFluidState();
		return fluidState.isStill() && !fluidState.isEmpty() && super.isTargetPos(pos);
	}
}