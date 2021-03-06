package net.emirikol.amm.block.entity;

import net.emirikol.amm.*;
import net.emirikol.amm.block.*;
import net.emirikol.amm.item.*;
import net.emirikol.amm.genetics.*;
import net.emirikol.amm.inventory.*;
import net.emirikol.amm.screen.*;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.screen.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.collection.*;

import java.util.*;
import org.jetbrains.annotations.Nullable;

public class SoulGrafterBlockEntity extends BlockEntity implements ImplementedSidedInventory,NamedScreenHandlerFactory,Tickable {
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(10, ItemStack.EMPTY);
	public static final int[] PARENT_SLOTS = {0,1};
	public static final int[] EMPTYSTONE_SLOTS = {2};
	public static final int[] FUEL_SLOTS = {3};
	public static final int[] OUTPUT_SLOTS = {4,5,6,7,8,9};
	public static final int[] ALL_SLOTS = {0,1,2,3,4,5,6,7,8,9};
	
	public static final int FUEL_VALUE = 600; //each piece of bonemeal burns for 600 ticks, or 30 seconds
	public static final int GRAFT_DURATION = 2400; //grafting souls takes 2400 ticks or 2 minutes
	
	private int graft_time; 
	private int fuel_time; 
	
	private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			switch (index) {
				case 0:
					return graft_time;
				case 1:
					return fuel_time;
				default:
					return 0;
			}
		}
		
		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					graft_time = value;
					break;
				case 1:
					fuel_time = value;
					break;
			}
		}
		
		@Override
		public int size() {
			return 2;
		}
	};
	
	public SoulGrafterBlockEntity() {
		super(AriseMyMinionsMod.SOUL_GRAFTER_ENTITY);
	}
	
	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		super.toTag(tag);
		Inventories.toTag(tag, items);
		tag.putInt("graft_time", graft_time);
		tag.putInt("fuel_time", fuel_time);
		return tag;
	}
	
	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, items);
		graft_time = tag.getInt("graft_time");
		fuel_time = tag.getInt("fuel_time");
	}
	
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		//We provide *this* to the screenHandler as our class implements Inventory
		return new SoulGrafterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}
	
	@Override
	public int[] getAvailableSlots(Direction side) {
		return ALL_SLOTS;
	}
	
	@Override 
	public boolean isValid(int slot, ItemStack stack) {
		//You can insert filled soulstones into parent slots.
		for (int i: PARENT_SLOTS) {
			if ((slot == i) && (stack.isItemEqual(new ItemStack(AriseMyMinionsMod.SOULSTONE_FILLED)))) {
				return true;
			}
		}
		//You can insert empty soulstones into empty soulstone slots.
		for (int i: EMPTYSTONE_SLOTS) {
			if ((slot == i) && (stack.isItemEqual(new ItemStack(AriseMyMinionsMod.SOULSTONE_EMPTY)))) {
				return true;
			}
		}
		//You can insert fuel into fuel slots.
		for (int i: FUEL_SLOTS) {
			if ((slot == i) && (stack.isItemEqual(new ItemStack(Items.BONE_MEAL)))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return isValid(slot, stack);
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		//Only the output slots can be extracted from.
		for (int i: OUTPUT_SLOTS) {
			if (slot == i) {
				return true;
			}
		}
		return false;
	}
	
	//Helper function; dumps a stack into the next available output.
	//Returns false if it can't find a valid output slot.
	public boolean graftOutput(ItemStack stack) {
		for(int i: OUTPUT_SLOTS) {
			ItemStack outputStack = items.get(i);
			//Check if the given output slot is empty.
			if(outputStack.isEmpty()) {
				this.setStack(i, stack);
				this.markDirty();
				return true;
			}
			//Check if the given output slot matches and has room.
			if ((ItemStack.areTagsEqual(outputStack, stack)) && (ItemStack.areItemsEqual(outputStack, stack)) && (outputStack.getCount() < outputStack.getMaxCount())) {
				outputStack.increment(1);
				this.markDirty();
				return true;
			}
		}
		return false;
	}
	
	public boolean checkGraft() {
		//Check if both parent slots contain a filled soulstone.
		for(int i: PARENT_SLOTS) {
			Item item = items.get(i).getItem();
			if (!(item instanceof SoulstoneFilled)) { return false; }
		}
		//Check if the empty soulstone slot contains at least one empty soulstone.
		Item item = items.get(EMPTYSTONE_SLOTS[0]).getItem();
		if (!(item == AriseMyMinionsMod.SOULSTONE_EMPTY)) { return false; }
		//Check if there is at least one empty output slot.
		boolean empty_slot = false;
		for (int i: OUTPUT_SLOTS) {
			ItemStack outputStack = items.get(i);
			if (outputStack.isEmpty()) {
				empty_slot = true;
				break;
			}
		}
		if (!empty_slot) { return false; }
		//If all conditions are filled, graft can proceed.
		return true;
	}
	
	public boolean isGrafting() {
		if (graft_time > 0) { return true; }
		return false;
	}
	
	public boolean isBurning() {
		if (fuel_time > 0) { return true; }
		return false;
	}
	
	@Override
	public void tick() {
		boolean dirty = false;
		boolean grafting = isGrafting();
		//If the grafter is burning, decrement the fuel timer.
		if (isBurning()) {
			fuel_time -= 1;
			dirty = true;
		}
		//Check if there is something to graft.
		if (checkGraft()) {
			//If the fuel is empty, try to refill it.
			if (fuel_time == 0) {
				ItemStack stack = items.get(FUEL_SLOTS[0]);
				Item item = stack.getItem();
				if (item == Items.BONE_MEAL) {
					stack.decrement(1);
					fuel_time = FUEL_VALUE;
					dirty = true;
				}
			}
			//After attempting to refill, check if there is fuel.
			if (isBurning()) {
				//Check if the graft timer is currently running.
				if (isGrafting()) {
					//Decrement the graft timer and perform the graft if this reduces it to 0.
					graft_time -= 1;
					dirty = true;
					if (graft_time == 0) {
						graft();
					}
				} else {
					//If the graft timer is not running, start it.
					graft_time = GRAFT_DURATION;
					dirty = true;
				}
			} else if (graft_time > 0) {
				//If there is no fuel, reset the graft timer.
				graft_time = 0;
				dirty = true;
			}
		} else if (graft_time > 0) {
			//If there is nothing to graft, reset the graft timer.
			graft_time = 0;
			dirty = true;
		}
		//Check if the blockstate needs to be updated for particles.
		if (isGrafting() != grafting) {
			this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(SoulGrafterBlock.GRAFTING, isGrafting()), 3);
			dirty = true;
		}
		if (dirty) {
			this.markDirty();
		}
	}
	
	//Called when the soulstone grafting process completes; performs the actual grafting and breeding logic.
	public void graft() {
		Random rand = new Random();
		int x;
		//Get parent itemstacks.
		ItemStack[] parents = { null, null };
		for (int i = 0; i < PARENT_SLOTS.length; i++) {
			parents[i] = items.get(PARENT_SLOTS[i]);
		}
		//Get itemstack of empty soulstones.
		ItemStack emptySoulstones = items.get(EMPTYSTONE_SLOTS[0]);
		//Randomly choose which parent to use for the potency.
		x = rand.nextInt(2);
		Genome potencyGenome = new Genome(parents[x]);
		Gene<Integer> potencyGene = potencyGenome.get("potency");
		int potency = potencyGene.getActive();
		for (int i = 0; i < potency; i++) {
			//Check if there are empty soulstones available to fill.
			if (emptySoulstones.getCount() > 0) {
				//Breed the parents to create a new soulstone with a new genome.
				Genome childGenome = Genome.breed(new Genome(parents[0]), new Genome(parents[1]));
				ItemStack child = new ItemStack(AriseMyMinionsMod.SOULSTONE_FILLED);
				childGenome.toItemStack(child);
				//Output new soulstone and decrement empty soulstones.
				graftOutput(child);
				emptySoulstones.decrement(1);
			}
		}
		//Decrement the parent soulstones.
		for (ItemStack parent: parents) {
			parent.decrement(1);
		}
	}
}