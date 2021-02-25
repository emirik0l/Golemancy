package net.emirikol.amm.screen;

import net.emirikol.amm.*;
import net.emirikol.amm.block.entity.*;
import net.emirikol.amm.screen.slot.*;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;

public class SoulGrafterScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	PropertyDelegate propertyDelegate;

	//Server Constructor
	//This is called from the SoulGrafterBlockEntity on the server, which can provide the Inventory object directly.
	public SoulGrafterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(AriseMyMinionsMod.SOUL_GRAFTER_SCREEN_HANDLER, syncId);
		this.propertyDelegate = propertyDelegate;
		this.addProperties(propertyDelegate);
		
		checkSize(inventory, 10);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);

		//Place the parent slots.
		Slot parent_a = new FilledSoulstoneSlot(inventory, 0, 8, 21);
		Slot parent_b = new FilledSoulstoneSlot(inventory, 1, 26, 21);
		this.addSlot(parent_a);
		this.addSlot(parent_b);
		//Place the empty soulstone slot.
		Slot empty_stones = new RestrictedSlot(inventory, AriseMyMinionsMod.SOULSTONE_EMPTY, 2, 16, 53);
		this.addSlot(empty_stones);
		//Place the fuel slot.
		Slot fuel = new RestrictedSlot(inventory, Items.BONE_MEAL, 3, 80, 53);
		this.addSlot(fuel);
		//Place the output slots.
		Slot output_a = new OutputSlot(inventory, 4, 116, 23);
		this.addSlot(output_a);
		Slot output_b = new OutputSlot(inventory, 5, 134, 23);
		this.addSlot(output_b);
		Slot output_c = new OutputSlot(inventory, 6, 152, 23);
		this.addSlot(output_c);
		Slot output_d = new OutputSlot(inventory, 7, 116, 41);
		this.addSlot(output_d);
		Slot output_e = new OutputSlot(inventory, 8, 134, 41);
		this.addSlot(output_e);
		Slot output_f = new OutputSlot(inventory, 9, 152, 41);
		this.addSlot(output_f);
		
		int m,l;
		
		//Player inventory
		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		//Player hotbar
		for (m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
		}

	}
	
	//Client Constructor
	//The client doesn't have access to the Inventory object, so we create an empty inventory and sync it later.
	public SoulGrafterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(10), new ArrayPropertyDelegate(2));
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	//Shift + Player Inv Slot
	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}
	
	@Environment(EnvType.CLIENT)
	public int getGraftTime() {
		return this.propertyDelegate.get(0);
	}
	
	@Environment(EnvType.CLIENT)
	public int getFuelTime() {
		return this.propertyDelegate.get(1);
	}
}