package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;

public class GolemBreakBlockGoal extends Goal {
	private final AbstractGolemEntity entity;
	protected int breakProgress;
	protected int prevBreakProgress;
	protected int maxProgress;
	
	private BlockPos breakPos;
	
	public GolemBreakBlockGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
		return isBlockNearby();
	}
	
	public void start() {
		this.breakProgress = 0;
	}
	
	public boolean shouldContinue() {
		return this.breakProgress <= this.getMaxProgress() && canStart();
	}

	public void stop() {
		super.stop();
		this.entity.world.setBlockBreakingInfo(this.entity.getEntityId(), this.breakPos, -1);
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
			this.entity.world.setBlockBreakingInfo(this.entity.getEntityId(), this.breakPos, i);
			this.prevBreakProgress = i;
		}

		if (this.breakProgress == this.getMaxProgress()) {
			this.entity.world.breakBlock(this.breakPos, true);
			this.entity.world.syncWorldEvent(2001, this.breakPos, Block.getRawIdFromState(this.entity.world.getBlockState(this.breakPos)));
		}
	}
	
	public boolean isBlockNearby() {
		BlockPos.Mutable mutable = this.entity.getBlockPos().mutableCopy();
		ServerWorld world = (ServerWorld) this.entity.world;
		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				for(int k = -2; k <= 2; ++k) {
					mutable.set(this.entity.getX() + (double)i, this.entity.getY() + (double)j, this.entity.getZ() + (double)k);
					if (isBlockBreakable(mutable)) {
						this.breakPos = mutable;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isBlockBreakable(BlockPos pos) {
		BlockState state = this.entity.world.getBlockState(pos);
		if (state == null) { return false; }
		BlockPos linkedPos = this.entity.getLinkedBlockPos();
		if (linkedPos == null) { return false; }
		BlockState linkedState = this.entity.world.getBlockState(linkedPos);
		if (linkedState == null) { return false; }
		float hardness = state.getHardness(this.entity.world, pos);
		//To break a block: must be valid for breaking; must be strong enough; must be same type as linked block; must not be linked block.
		return (hardness >= 0) && (hardness <= getBreakingStrength()) && (state.getBlock() == linkedState.getBlock()) && !pos.equals(linkedPos);
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
		return Math.max(120, this.maxProgress);
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 2.0D;
	}
}