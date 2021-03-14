package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.hit.*;
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
			FakePlayerEntity fakePlayer = new FakePlayerEntity(this.entity.world, pos, 0.0F);
			fakePlayer.equipStack(EquipmentSlot.MAINHAND, this.entity.getEquippedStack(EquipmentSlot.MAINHAND));
			BlockHitResult result = new BlockHitResult(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.UP, pos, false);
			state.getBlock().onUse(state, this.entity.world, pos, fakePlayer, fakePlayer.getActiveHand(), result);
			fakePlayer.remove();
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