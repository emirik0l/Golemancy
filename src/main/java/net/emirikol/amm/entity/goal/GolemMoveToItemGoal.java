package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemMoveToItemGoal extends Goal {
	private final ClayEffigyEntity entity;
	private final float searchRadius;
	private final List<String> validTypes;
	
	protected BlockPos targetPos;
	protected int tryingTime;
	protected int safeWaitingTime;
	
	public GolemMoveToItemGoal(ClayEffigyEntity entity, float searchRadius, String[] validTypes) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.validTypes = Arrays.asList(validTypes);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
	}
	
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		String golemType = entity.getGolemType();
		if (!this.validTypes.contains(golemType)) {
			return false;
		}
		return this.findTargetPos() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
	
	public boolean shouldContinue() {
		return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.findTargetPos() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
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
				this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, (double)this.targetPos.getY(), (double)((float)this.targetPos.getZ()) + 0.5D, 1);
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
			if (this.entity.isInWalkTargetRange(pos)) {
				this.targetPos = pos;
				return true;
			}
		}
		return false;
	}
}