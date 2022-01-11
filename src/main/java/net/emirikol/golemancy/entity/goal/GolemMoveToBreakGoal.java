package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.util.math.*;

import java.util.EnumSet;

public class GolemMoveToBreakGoal extends GolemMoveGoal {
	private static final double BREAK_RANGE = 5.0D;

	protected int breakProgress;
	protected int prevBreakProgress;

	public GolemMoveToBreakGoal(AbstractGolemEntity entity, float searchRadius) {
		super(entity, searchRadius, 1);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}
	
	public GolemMoveToBreakGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
		super(entity, searchRadius, maxYDifference);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public void tick() {
		//Attempt to look at block.
		this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
		//Attempt to break block.
		if (this.canBreak(this.targetPos)) {
			if (this.entity.getRandom().nextInt(5) == 0) this.entity.trySwing();

			this.breakProgress++;
			int i = (int)((float)this.breakProgress / (float)this.getMaxProgress() * 10.0F);
			if (i != this.prevBreakProgress) {
				this.entity.world.setBlockBreakingInfo(this.entity.getId(), this.targetPos, i);
				this.prevBreakProgress = i;
			}

			if (this.breakProgress == this.getMaxProgress()) {
				this.entity.world.breakBlock(this.targetPos, true);
				this.breakProgress = 0;
				this.prevBreakProgress = 0;
				this.entity.world.syncWorldEvent(2001, this.targetPos, Block.getRawIdFromState(this.entity.world.getBlockState(this.targetPos)));
			}
		}
		//Continue towards targetPos.
		super.tick();
	}
	
	@Override
	public boolean isTargetPos(BlockPos pos) {
		BlockState state = this.entity.world.getBlockState(pos);
		if (state == null) { return false; }
		Block linkedBlock = this.entity.getLinkedBlock();
		if (linkedBlock == null) { return false; }
		
		return (state.getBlock() == linkedBlock) && super.isTargetPos(pos);
	}

	@Override
	public boolean canReachPos(BlockPos pos) {
		//Check if we can path to any block within BREAK_RANGE.
		EntityNavigation nav = this.entity.getNavigation();
		for (BlockPos curPos: BlockPos.iterateOutwards(pos, (int) BREAK_RANGE, (int) BREAK_RANGE, (int) BREAK_RANGE)) {
			if (nav.findPathTo(curPos, 0) != null) return true;
		}
		return false;
	}

	public boolean canBreak(BlockPos pos) {
		 if (pos.isWithinDistance(this.entity.getPos(), BREAK_RANGE)) {
			 BlockState state = this.entity.world.getBlockState(pos);
			 if (state == null) { return false; }
			 Block linkedBlock = this.entity.getLinkedBlock();
			 if (linkedBlock == null) { return false; }
			 float hardness = state.getHardness(this.entity.world, pos);
			 //To break a block: must be valid for breaking; must be strong enough; must be same type as linked block.
			 return (hardness >= 0) && (hardness <= getBreakingStrength()) && (state.getBlock() == linkedBlock) && !state.isAir();
		 }
		 return false;
	}

	public float getBreakingStrength() {
		return this.entity.getBlockBreakHardnessFromStrength();
	}

	protected int getMaxProgress() {
		return 120;
	}
}