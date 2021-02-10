package net.emirikol.amm.screen.slot;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.slot.*;

//A slot that only allows one type of item to be inserted.

public class RestrictedSlot extends Slot {
	private Item item;
	
	public RestrictedSlot(Inventory inventory, Item item, int index, int x, int y) {
		super(inventory, index, x, y);
		this.item = item;
	}
	
	@Override
	public boolean canInsert(ItemStack stack) {
		if (stack.getItem() == this.item) {
			return true;
		}
		return false;
	}
}