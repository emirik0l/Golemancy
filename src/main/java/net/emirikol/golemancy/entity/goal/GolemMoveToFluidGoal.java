package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.EnumSet;

public class GolemMoveToFluidGoal extends GolemMoveGoal {
	private static final int FILL_RANGE = 3;
	
	public GolemMoveToFluidGoal(AbstractGolemEntity entity, float maxYDifference) {
		super(entity, maxYDifference);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}
	
	@Override
	public boolean canStart() {
		return GolemHelper.hasEmptyBucket(this.entity) && super.canStart();
	}

	@Override
	public boolean shouldContinue() { return GolemHelper.hasEmptyBucket(this.entity) && super.shouldContinue(); }

	@Override
	public void tick() {
		//Attempt to look at fluid.
		this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
		//Continue towards targetPos.
		super.tick();
	}

	@Override
	public boolean isTargetPos(BlockPos pos) {
		ServerWorld world = (ServerWorld) this.entity.world;
		FluidState fluidState = world.getBlockState(pos).getFluidState();
		return fluidState.isStill() && !fluidState.isEmpty() && super.isTargetPos(pos);
	}

	@Override
	public double getDesiredDistanceToTarget() {
		return FILL_RANGE;
	}
}