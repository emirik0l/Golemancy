package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class GolemMoveToHealGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final float searchRadius;
	private final EntityNavigation navigation;
	
	private TameableEntity friend;
	protected int tryingTime;
	protected int safeWaitingTime;
	
	public GolemMoveToHealGoal(AbstractGolemEntity entity, float searchRadius) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.navigation = this.entity.getNavigation();
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}
	
	public boolean canStart() {
		return findHealTarget();
	}
	
	public boolean shouldContinue() {
		return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.findHealTarget();
	}
	
	public void start() {
		this.navigation.startMovingTo(this.friend, 1);
		this.tryingTime = 0;
		this.safeWaitingTime = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
	}

	public void tick() {
		this.entity.getLookControl().lookAt(this.friend, 10.0F, (float)this.entity.getLookPitchSpeed());
		BlockPos friendPos = this.friend.getBlockPos();
		if (!friendPos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget())) {
			++this.tryingTime;
			if (this.shouldResetPath()) {
				this.navigation.startMovingTo(this.friend, 1);
			}
		} else {
			--this.tryingTime;
		}
	}
	
	public boolean findHealTarget() {
		float r = this.searchRadius + (10.0F * this.entity.getGolemSmarts());
		List<TameableEntity> list = entity.world.getEntitiesByClass(TameableEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		for (TameableEntity tameable: list) {
			if (wounded(tameable) && (this.entity.getOwner() == tameable.getOwner())) {
				this.friend = tameable;
				return true;
			}
		}
		return false;
	}
	
	public boolean wounded(LivingEntity entity) {
		return entity.getHealth() < entity.getMaxHealth();
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 1.0D;
	}
	
	public boolean shouldResetPath() {
		return this.tryingTime % 40 == 0;
	}
}
