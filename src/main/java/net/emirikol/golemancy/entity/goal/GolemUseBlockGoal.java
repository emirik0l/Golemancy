package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

public class GolemUseBlockGoal extends Goal {
	private final AbstractGolemEntity entity;
	
	protected int useCooldown;
	
	public GolemUseBlockGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
		return isBlockNearby();
	}
	
	public void tick() {
		if (canUseBlock()) {
			BlockPos pos = this.entity.getLinkedBlockPos();
			BlockState state = this.entity.world.getBlockState(pos);
			state.getBlock().onUse(state, this.entity.world, pos, null, this.entity.getActiveHand(), null);
			this.useCooldown = 20;
		} else {
			this.useCooldown--;
		}
	}
	
	public boolean isBlockNearby() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) { return false; }
		return pos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget());
	}
	
	public boolean canUseBlock() {
		return this.useCooldown <= 0;
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 3.0D;
	}
}