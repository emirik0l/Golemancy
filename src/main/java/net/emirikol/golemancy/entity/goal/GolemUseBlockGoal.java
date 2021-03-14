package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.*;
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
			//Calculate pertinent details.
			BlockPos pos = this.entity.getLinkedBlockPos();
			BlockState state = this.entity.world.getBlockState(pos);
			BlockHitResult hit = new BlockHitResult(new Vec3d(pos.getX(), pos.getY(), pos.getZ()), Direction.UP, pos, false);
			//Create a fake player and equip them with the golem's item.
			ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
			FakePlayerEntity fakePlayer = new FakePlayerEntity(this.entity.world, pos, 0.0F);
			ItemUsageContext context = new ItemUsageContext(fakePlayer, fakePlayer.getActiveHand(), hit);
			fakePlayer.setStackInHand(fakePlayer.getActiveHand(), stack);
			//Try using the item on the block.
			ActionResult result = stack.getItem().useOnBlock(context);
			state.getBlock().onUse(state, this.entity.world, pos, fakePlayer, fakePlayer.getActiveHand(), hit);
			if (result == ActionResult.PASS) {
				//If that doesn't do anything, try just using the block
				
			}
			//Remove the fake player and set the cooldown.
			this.entity.equipStack(EquipmentSlot.MAINHAND, fakePlayer.getStackInHand(fakePlayer.getActiveHand()));
			fakePlayer.remove();
			this.useCooldown = 60;
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