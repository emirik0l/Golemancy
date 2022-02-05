package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.GolemancyConfig;
import net.emirikol.golemancy.entity.*;

import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemMoveGoal extends Goal {
	protected final AbstractGolemEntity entity;
	protected final float searchRadius;
	protected final float maxYDifference;
	
	private List<BlockPos> failedTargets;
	private int idleTime;
	protected BlockPos targetPos;
	protected int cooldown;
	
	public GolemMoveGoal(AbstractGolemEntity entity, float searchRadius) {
		this(entity, searchRadius, 1);
	}
	
	public GolemMoveGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.maxYDifference = maxYDifference;
		this.failedTargets = new ArrayList<>();
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		if (this.cooldown > 0) {
			--this.cooldown;
			return false;
		}
		this.cooldown = GolemancyConfig.getGolemCooldown();
		return this.findTargetPos() && this.canReachPos(this.targetPos);
	}

	@Override
	public boolean shouldContinue() {
		return this.findTargetPos();
	}

	@Override
	public void start() {
		this.entity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1);
		this.idleTime = 0;
	}

	@Override
	public void tick() {
		if (!this.targetPos.isWithinDistance(this.entity.getPos(), this.getDesiredDistanceToTarget())) {
			if (this.entity.getNavigation().isIdle()) this.idleTime++;

			if (this.idleTime % 40 == 0) {
				//If we have spent 2 seconds idle while trying to reach a targetPos, add it to the list of failed targets.
				this.failedTargets.add(this.targetPos);
			} else {
				//Continue towards targetPos.
				this.entity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1);
			}

		}
	}

	@Override
	public void stop() {
		//When we can no longer find a targetPos, clear the list of failed targets.
		this.failedTargets.clear();
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}
	
	public double getDesiredDistanceToTarget() {
		return 1.0D;
	}
	
	public boolean findTargetPos() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) { pos = this.entity.getBlockPos(); }
		
		float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
		for (BlockPos curPos: BlockPos.iterateOutwards(pos, (int)r, (int) this.maxYDifference, (int)r)) {
			if (this.entity.isInWalkTargetRange(curPos) && isTargetPos(curPos)) {
				this.targetPos = curPos;
				return true;
			}
		}
		return false;
	}
	
	public boolean isTargetPos(BlockPos pos) {
		//Used to determine whether a given BlockPos qualifies to be our targetPos.
		//By default, just disallows any targetPos we have already tried and failed to reach.
		return !this.failedTargets.contains(pos);
	}

	public boolean canReachPos(BlockPos pos) {
		//For most golems, we want to be able to path to the targetPos itself in order to consider it reachable.
		//Override for certain golems where this isn't the case (i.e. you want to get within X blocks of targetPos).
		return this.entity.getNavigation().findPathTo(pos, 0) != null;
	}
}