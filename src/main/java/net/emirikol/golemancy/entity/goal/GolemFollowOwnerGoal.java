package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;

import java.util.*;

public class GolemFollowOwnerGoal extends Goal {
	
	protected final AbstractGolemEntity entity;
	private LivingEntity owner;
	private final double speed;
	private final EntityNavigation navigation;
	private int updateCountdownTicks;
	private final float minDistance;
	private float oldWaterPathfindingPenalty;

	public GolemFollowOwnerGoal(AbstractGolemEntity entity, double speed, float minDistance) {
		this.entity = entity;
		this.speed = speed;
		this.navigation = this.entity.getNavigation();
		this.minDistance = minDistance;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		if (!(this.entity.getNavigation() instanceof MobNavigation) && !(this.entity.getNavigation() instanceof BirdNavigation)) {
			throw new IllegalArgumentException("Unsupported mob type for GolemFollowOwnerGoal");
		}
	}

	public boolean canStart() {
		LivingEntity livingEntity = this.entity.getOwner();
		if (livingEntity == null) return false;
		if (livingEntity.isSpectator()) return false;
		if (this.entity.isSitting()) return false;
		if (this.entity.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) return false;
		if (!this.entity.isFollowingWand()) return false;

		this.owner = livingEntity;
		return true;
	}

	public boolean shouldContinue() {
		return !this.navigation.isIdle() && this.canStart();
	}

	public void start() {
		this.updateCountdownTicks = 0;
		this.oldWaterPathfindingPenalty = this.entity.getPathfindingPenalty(PathNodeType.WATER);
		this.entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.entity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
	}

	public void tick() {
		this.entity.getLookControl().lookAt(this.owner, 10.0F, (float)this.entity.getMaxLookPitchChange());
		if (--this.updateCountdownTicks <= 0) {
			this.updateCountdownTicks = 10;
			this.navigation.startMovingTo(this.owner, this.speed);
		}
	}
}
