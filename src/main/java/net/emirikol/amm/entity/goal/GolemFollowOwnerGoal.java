package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class GolemFollowOwnerGoal extends Goal {
	
	private final TameableEntity tameable;
	private LivingEntity owner;
	private final WorldView world;
	private final double speed;
	private final EntityNavigation navigation;
	private int updateCountdownTicks;
	private final float maxDistance;
	private final float minDistance;
	private final float teleportDistance;
	private float oldWaterPathfindingPenalty;
	private final boolean leavesAllowed;
	private final List<String> validTypes;

	public GolemFollowOwnerGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance, float teleportDistance, boolean leavesAllowed, String[] validTypes) {
		this.tameable = tameable;
		this.world = tameable.world;
		this.speed = speed;
		this.navigation = tameable.getNavigation();
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		this.teleportDistance = teleportDistance;
		this.leavesAllowed = leavesAllowed;
		this.validTypes = Arrays.asList(validTypes);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		if (!(tameable.getNavigation() instanceof MobNavigation) && !(tameable.getNavigation() instanceof BirdNavigation)) {
			throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
		}
	}

	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour, or if the golem has been commanded to follow with a golem wand.
		ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) this.tameable;
		String golemType = clayEffigyEntity.getGolemType();
		if (!this.validTypes.contains(golemType) && !clayEffigyEntity.isFollowingWand()) {
			return false;
		}
		//Continue with checks copied from FollowOwnerGoal
		LivingEntity livingEntity = this.tameable.getOwner();
		if (livingEntity == null) {
			return false;
		} else if (livingEntity.isSpectator()) {
			return false;
		} else if (this.tameable.isSitting()) {
			return false;
		} else if (this.tameable.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
			return false;
		} else {
			this.owner = livingEntity;
			return true;
		}
	}

	public boolean shouldContinue() {
		if (this.navigation.isIdle()) {
			return false;
		} else if (this.tameable.isSitting()) {
			return false;
		} else {
			return this.canStart();
		}
	}

	public void start() {
		this.updateCountdownTicks = 0;
		this.oldWaterPathfindingPenalty = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
		this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
	}

	public void stop() {
		this.owner = null;
		this.navigation.stop();
		this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
	}

	public void tick() {
		this.tameable.getLookControl().lookAt(this.owner, 10.0F, (float)this.tameable.getLookPitchSpeed());
		if (--this.updateCountdownTicks <= 0) {
			this.updateCountdownTicks = 10;
			this.navigation.startMovingTo(this.owner, this.speed);
		}
	}
}
