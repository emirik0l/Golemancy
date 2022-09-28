package net.emirikol.golemancy.screen.slot;

import net.emirikol.golemancy.item.SoulstoneFilled;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

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