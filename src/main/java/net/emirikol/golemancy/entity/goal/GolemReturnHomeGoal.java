package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemReturnHomeGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final double speed;
	
	protected BlockPos targetPos;
	protected int tryingTime;
	protected int safeWaitingTime;
	
	public GolemReturnHomeGoal(AbstractGolemEntity entity, double speed) {
		this.entity = entity;
		this.speed = speed;
		this.targetPos = BlockPos.ORIGIN;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		return this.findTargetPos();
	}
	
	public boolean shouldContinue() {
		return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.findTargetPos();
	}
	
	public void start() {
		this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, (this.targetPos.getY() + 1), (double)((float)this.targetPos.getZ()) + 0.5D, this.speed);
		this.tryingTime = 0;
		this.safeWaitingTime = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
	}
	
	public void tick() {
		if (!this.targetPos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget())) {
			++this.tryingTime;
			if (this.shouldResetPath()) {
				this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, this.targetPos.getY(), (double)((float)this.targetPos.getZ()) + 0.5D, this.speed);
			}
		} else {
			--this.tryingTime;
		}
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 1.0D;
	}
	
	public boolean shouldResetPath() {
		return this.tryingTime % 40 == 0;
	}
	
	public boolean findTargetPos() {
		this.targetPos = this.entity.getLinkedBlockPos();
		if (this.targetPos == null) { return false; }
		return this.entity.isInWalkTargetRange(this.targetPos);
	}
}