package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.inventory.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemDepositHeldItemGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final List<String> validTypes;
	
	private Inventory container;
	
	public GolemDepositHeldItemGoal(AbstractGolemEntity entity, String[] validTypes) {
		this.entity = entity;
		this.validTypes = Arrays.asList(validTypes);
	}
	
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		String golemType = entity.getGolemType();
		if (!this.validTypes.contains(golemType)) {
			return false;
		}
		//Check whether golem is holding something, linked block is an inventory, and linked block is close enough to deposit.
		return !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && linkedBlockIsContainer() && linkedBlockIsNearby() && linkedBlockCanInsert();
	}
	
	public void tick() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		if (!stack.isEmpty()) {
			for (int i = 0; i < this.container.size(); i++) {
				ItemStack slotStack = this.container.getStack(i);
				if (slotStack.isEmpty() && this.container.isValid(i, stack)) {
					this.container.setStack(i, stack);
					this.entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
					break;
				} else if ((ItemStack.areItemsEqual(stack, slotStack)) && (ItemStack.areTagsEqual(stack, slotStack)) && (slotStack.getCount() < this.container.getMaxCountPerStack())) {
					stack.decrement(1);
					slotStack.increment(1);
				}
			}
		}
	}
	
	private boolean linkedBlockIsContainer() {
		BlockPos pos = this.entity.getLinkedBlockPos();
		if (pos == null) { return false; }
		BlockEntity blockEntity = this.entity.world.getBlockEntity(pos);
		if (blockEntity == null) { return false; }
		if (blockEntity instanceof Inventory) {
			this.container = (Inventory) blockEntity;
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
	
	private boolean linkedBlockCanInsert() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		for (int i = 0; i < this.container.size(); i++) {
			if (this.container.isValid(i, stack)) {
				return true;
			}
		}
		return false;
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 2.5D;
	}
}