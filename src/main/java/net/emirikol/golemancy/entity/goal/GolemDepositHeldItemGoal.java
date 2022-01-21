package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

public class GolemDepositHeldItemGoal extends Goal {
	protected final AbstractGolemEntity entity;

	private Inventory container;
	
	public GolemDepositHeldItemGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
		//Check whether golem is holding something, linked block is an inventory, and linked block is close enough to deposit.
		return !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && linkedBlockIsContainer() && linkedBlockIsNearby() && linkedBlockCanInsert();
	}

	@Override
	public void tick() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		GolemHelper.tryInsert(stack, this.container);
	}
	
	private boolean linkedBlockIsContainer() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		ServerWorld world = (ServerWorld) this.entity.world;
		if (pos == null) { return false; }
		Inventory container = GolemHelper.getInventory(pos, world);
		if (container != null) {
			this.container = container;
			return true;
		} else {
			return false;
		}
	}
	
	private boolean linkedBlockIsNearby() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) {
			return false;
		}
		return pos.isWithinDistance(this.entity.getPos(), this.getDesiredSquaredDistanceToTarget());
	}
	
	protected boolean linkedBlockCanInsert() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		return GolemHelper.canInsert(stack, this.container);
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 2.5D;
	}
}