package net.emirikol.amm.screen.slot;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.slot.*;

//A slot that doesn't allow items to be inserted.

public class OutputSlot extends Slot {
	public OutputSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean canInsert(ItemStack stack) {
		return false;
	}
}