package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

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
		//Look at target.
		Vec3d lookPos = Vec3d.ofCenter(this.entity.getLinkedBlockPos());
		this.entity.getLookControl().lookAt(lookPos.getX(), lookPos.getY(), lookPos.getZ());

		if (canUseBlock()) {
			//Swing the arm.
			this.entity.tryAttack();
			//Calculate pertinent details.
			BlockPos pos = this.entity.getLinkedBlockPos();
			//Create a fake player and equip them with the golem's item.
			FakePlayerEntity fakePlayer = new FakePlayerEntity(this.entity.world, pos, 0.0F, null, null);
			fakePlayer.copyFromEntity(this.entity);
			//Try using the fake player to activate the block.
			fakePlayer.useBlock(pos);
			//Remove the fake player and set the cooldown.
			fakePlayer.copyToEntity(this.entity);
			fakePlayer.discard();
			this.useCooldown = this.getUseCooldown();
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

	public int getUseCooldown() { return 60; }
	public double getDesiredSquaredDistanceToTarget() {
		return 3.0D;
	}
}