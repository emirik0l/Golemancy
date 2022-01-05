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
	
	private BlockPos breakPos;
	
	public GolemBreakBlockGoal(AbstractGolemEntity entity) {
		this.entity = entity;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK)); //stop the golem from getting distracted while breaking blocks
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
		this.entity.world.setBlockBreakingInfo(this.entity.getId(), this.breakPos, -1);
	}
	
	public void tick() {
		if (this.entity.getRandom().nextInt(5) == 0) {
			this.entity.trySwing();
		}
		
		this.breakProgress++;
		int i = (int)((float)this.breakProgress / (float)this.getMaxProgress() * 10.0F);
		if (i != this.prevBreakProgress) {
			this.entity.world.setBlockBreakingInfo(this.entity.getId(), this.breakPos, i);
			this.prevBreakProgress = i;
		}

		if (this.breakProgress == this.getMaxProgress()) {
			this.entity.world.breakBlock(this.breakPos, true);
			this.breakProgress = 0;
			this.prevBreakProgress = 0;
			this.entity.world.syncWorldEvent(2001, this.breakPos, Block.getRawIdFromState(this.entity.world.getBlockState(this.breakPos)));
		}
	}
	
	public boolean isBlockNearby() {
		BlockPos pos = this.entity.getBlockPos();
		for (BlockPos curPos: BlockPos.iterateOutwards(pos, 2, 2, 2)) {
			if (isBlockBreakable(curPos)) {
				this.breakPos = curPos;
				return true;
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
		return (hardness >= 0) && (hardness <= getBreakingStrength()) && (state.getBlock() == linkedState.getBlock()) && !pos.equals(linkedPos) && !state.isAir();
	}
	
	public float getBreakingStrength() {
		return this.entity.getBlockBreakHardnessFromStrength();
	}
	
	protected int getMaxProgress() {
		return 120;
	}
}