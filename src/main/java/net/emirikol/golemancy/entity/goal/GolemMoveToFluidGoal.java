package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

public class GolemMoveToFluidGoal extends GolemMoveToBlockGoal {
	public GolemMoveToFluidGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius);
	}
	
	@Override
	public boolean isValidPos(BlockPos pos) {
		ServerWorld world = (ServerWorld) this.entity.world;
		FluidState fluidState = world.getBlockState(pos).getFluidState();
		return fluidState.isStill() && !fluidState.isEmpty() && super.isValidPos(pos);
	}
}