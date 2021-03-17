package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemMoveToItemGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final float searchRadius;
	
	protected BlockPos targetPos;
	protected int tryingTime;
	protected int safeWaitingTime;
	
	public GolemMoveToItemGoal(AbstractGolemEntity entity, float searchRadius) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		return this.findTargetPos() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
	
	public boolean shouldContinue() {
		return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && canStart();
	}
	
	public void start() {
		this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, (double)(this.targetPos.getY() + 1), (double)((float)this.targetPos.getZ()) + 0.5D, 1);
		this.tryingTime = 0;
		this.safeWaitingTime = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
	}

	public void tick() {
		//Check if there is an item within 1.5 blocks and the golem's hand is empty.
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(1.5F,1.5F,1.5F), null);
		if (!list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			//Take 1 item from the stack.
			ItemStack stack = list.get(0).getStack();
			entity.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
		}
		//Continue towards targetPos.
		if (!this.targetPos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget())) {
			++this.tryingTime;
			if (this.shouldResetPath()) {
				this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, (double)(this.targetPos.getY() + 1), (double)((float)this.targetPos.getZ()) + 0.5D, 1);
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
		float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		if (list.isEmpty()) { return false; }
		for (ItemEntity itemEntity: list) {
			BlockPos pos = itemEntity.getBlockPos();
			if (GolemHelper.canReach(entity, pos)) {
				this.targetPos = pos;
				return true;
			}
		}
		return false;
	}
}