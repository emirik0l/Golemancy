package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.inventory.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;

public class GolemExtractItemGoal extends Goal {
	private final AbstractGolemEntity entity;
	
	private Inventory container;
	private List<Item> filter;
	
	public GolemExtractItemGoal(AbstractGolemEntity entity) {
		this.entity = entity;
		this.filter = new ArrayList<Item>();
	}
	
	public boolean canStart() {
		//Check whether golem has an empty hand, linked block is an inventory, and linked block is close enough to extract from.
		return entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && linkedBlockIsContainer() && linkedBlockIsNearby() && linkedBlockCanExtract();
	}
	
	public void tick() {
		if (this.entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			for (int i = 0; i < this.container.size(); i++) {
				ItemStack stack = this.container.getStack(i);
				if (!stack.isEmpty() && canTake(stack)) {
					this.entity.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
					return;
				}
			}
		}
	}
	
	public void add(Item... items) {
		//Adds items to the filter, marking them as "allowed" to extract.
		//If the filter is empty, anything can be extracted.
		for (Item item: items) {
			this.filter.add(item);
		}
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
	
	private boolean linkedBlockCanExtract() {
		for (int i = 0; i < this.container.size(); i++) {
			ItemStack stack = this.container.getStack(i);
			if (!stack.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean canTake(ItemStack stack) {
		if (this.filter.isEmpty()) {
			return true;
		} else {
			return this.filter.contains(stack.getItem());
		}
	}
	
	public double getDesiredSquaredDistanceToTarget() {
		return 2.5D;
	}
}