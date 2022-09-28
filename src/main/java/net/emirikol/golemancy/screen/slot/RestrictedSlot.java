package net.emirikol.golemancy.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

//A slot that only allows one type of item to be inserted.

public class RestrictedSlot extends Slot {
    private final Item item;

    public RestrictedSlot(Inventory inventory, Item item, int index, int x, int y) {
        super(inventory, index, x, y);
        this.item = item;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() == this.item;
    }
}