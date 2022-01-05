package net.emirikol.golemancy.screen.slot;

import net.emirikol.golemancy.item.*;

import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.slot.*;

//A slot that only accepts filled soulstones.

public class FilledSoulstoneSlot extends Slot {
	public FilledSoulstoneSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean canInsert(ItemStack stack) {
		return stack.getItem() instanceof SoulstoneFilled;
	}
}