package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;

public class GolemMoveToBlockGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final float searchRadius;
	
	private List<Block> filter;
	protected BlockPos targetPos;
	protected int tryingTime;
	protected int safeWaitingTime;
	
	public GolemMoveToBlockGoal(AbstractGolemEntity entity, float searchRadius) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.filter = new ArrayList<Block>();
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		return this.findTargetPos();
	}
	
	public boolean shouldContinue() {
		return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.findTargetPos();
	}
	
	public void start() {
		this.entity.getNavigation().startMovingTo((double)((float)this.targetPos.getX()) + 0.5D, (double)(this.targetPos.getY() + 1), (double)((float)this.targetPos.getZ()) + 0.5D, 1);
		this.tryingTime = 0;
		this.safeWaitingTime = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
	}

	public void tick() {
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
	
	public void add(Block... blocks) {
		//Adds blocks to the filter, marking them as "allowed" to drink.
		//If the filter is empty, anything can be drunk.
		for (Block block: blocks) {
			this.filter.add(block);
		}
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 0.5D;
	}
	
	public boolean shouldResetPath() {
		return this.tryingTime % 40 == 0;
	}
	
	public boolean findTargetPos() {
		BlockPos.Mutable mutable = this.entity.getBlockPos().mutableCopy();
		ServerWorld world = (ServerWorld) this.entity.world;
		float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
		for(float i = -r; i <= r; ++i) {
			for(float j = -r; j <= r; ++j) {
				for(float k = -r; k <= r; ++k) {
					mutable.set(this.entity.getX() + (double)i, this.entity.getY() + (double)j, this.entity.getZ() + (double)k);
					if (isValidPos(mutable)) {
						this.targetPos = mutable;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isValidPos(BlockPos pos) {
		ServerWorld world = (ServerWorld) this.entity.world;
		BlockState state = world.getBlockState(pos);
		return this.filter.isEmpty() || this.filter.contains(state.getBlock());
	}
}