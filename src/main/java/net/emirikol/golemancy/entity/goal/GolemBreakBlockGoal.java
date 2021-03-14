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
	
	protected static final List<Block> FORBIDDEN_BLOCKS = new ArrayList<Block>() {{
		add(Blocks.AIR);
		add(Blocks.BEDROCK);
		add(Blocks.BARRIER);
	}};
	
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
		return this.breakProgress <= this.getMaxProgress() && this.entity.getLinkedBlockPos().isWithinDistance(this.entity.getPos(), 2.0D);
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
		Block block = this.entity.world.getBlockState(pos).getBlock();
		return !FORBIDDEN_BLOCKS.contains(block);
	}
	
	protected int getMaxProgress() {
		return Math.max(240, this.maxProgress);
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 3.0D;
	}
}