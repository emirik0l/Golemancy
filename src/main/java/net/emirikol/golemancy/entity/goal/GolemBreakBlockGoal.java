package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemBreakBlockGoal extends Goal {
	private final AbstractGolemEntity entity;
	protected int breakProgress;
	protected int prevBreakProgress;
	protected int maxProgress;
	
	public GolemBreakBlockGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public void start() {
		this.breakProgress = 0;
	}
	
	public boolean canStart() {
		return isBlockNearby() && isBlockBreakable();
	}
	
	public boolean shouldContinue() {
		return this.breakProgress <= this.getMaxProgress() && this.entity.getLinkedBlockPos().isWithinDistance(this.entity.getPos(), 2.0D) && canStart();
	}

	public void stop() {
		super.stop();
		this.entity.world.setBlockBreakingInfo(this.entity.getEntityId(), this.entity.getLinkedBlockPos(), -1);
	}
	
	public void tick() {
		if (this.entity.getRandom().nextInt(20) == 0) {
			if (!this.entity.handSwinging) {
				this.entity.swingHand(this.entity.getActiveHand());
			}
		}
		
		this.breakProgress++;
		int i = (int)((float)this.breakProgress / (float)this.getMaxProgress() * 10.0F);
		if (i != this.prevBreakProgress) {
			this.entity.world.setBlockBreakingInfo(this.entity.getEntityId(), this.entity.getLinkedBlockPos(), i);
			this.prevBreakProgress = i;
		}

		if (this.breakProgress == this.getMaxProgress()) {
			this.entity.world.breakBlock(this.entity.getLinkedBlockPos(), true);
			this.entity.world.syncWorldEvent(2001, this.entity.getLinkedBlockPos(), Block.getRawIdFromState(this.entity.world.getBlockState(this.entity.getLinkedBlockPos())));
		}
	}
	
	public boolean isBlockNearby() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) { return false; }
		return pos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget());
	}
	
	public boolean isBlockBreakable() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) { return false; }
		BlockState state = this.entity.world.getBlockState(pos);
		if (state == null) { return false; }
		float hardness = state.getHardness(this.entity.world, pos);
		return (hardness > 0) && (hardness <= getBreakingStrength());
	}
	
	public float getBreakingStrength() {
		switch (this.entity.getGolemStrength()) {
			case 0:
				//low strength = can break dirt, wood, smooth stone
				return 2.0F;
			case 1:
				//average strength = can break ores
				return 4.0F;
			case 2:
				//high strength = can break metal blocks
				return 5.0F;
			case 3:
				//perfect strength = can break obsidian
				return 50.0F;
			default:
				return 0.0F;
		}
	}
	
	protected int getMaxProgress() {
		return Math.max(240, this.maxProgress);
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 2.0D;
	}
}