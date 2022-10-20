package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class GolemHelper {
    public static boolean hasEmptyVessel(LivingEntity entity) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        return stack.getItem() == Items.BUCKET || stack.getItem() == Items.GLASS_BOTTLE;
    }

    // Helper functions for golem inventory management.

    public static Inventory getInventory(BlockPos pos, ServerWorld world) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        BlockState state = world.getBlockState(pos);
        if (blockEntity == null || state == null) {
            return null;
        }
        if (blockEntity instanceof ChestBlockEntity) {
            ChestBlock block = (ChestBlock) state.getBlock();
            return ChestBlock.getInventory(block, state, world, pos, true);
        }
        if (state.getBlock() instanceof InventoryProvider) {
            InventoryProvider provider = (InventoryProvider) state.getBlock();
            return provider.getInventory(state, world, pos);
        }
        if (blockEntity instanceof Inventory) {
            return (Inventory) blockEntity;
        }
        return null;
    }

    public static boolean canInsertInSlot(ItemStack stack, Inventory inventory, int slot) {
        ItemStack slotStack = inventory.getStack(slot);
        boolean isValid = inventory.isValid(slot, stack);
        boolean isEmpty = slotStack.isEmpty();
        boolean canStack = (ItemStack.areItemsEqual(stack, slotStack)) && (ItemStack.areNbtEqual(stack, slotStack)) && (slotStack.getCount() < inventory.getMaxCountPerStack()) && (slotStack.getCount() < slotStack.getMaxCount());
        return isValid && (isEmpty || canStack);
    }

    public static boolean canInsert(ItemStack stack, Inventory inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (GolemHelper.canInsertInSlot(stack, inventory, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tryInsert(ItemStack stack, Inventory inventory) {
        if (stack.isEmpty()) return false;
        for (int i = 0; i < inventory.size(); i++) {
            //First attempt to find existing stacks we can add to.
            ItemStack slotStack = inventory.getStack(i);
            if (GolemHelper.canInsertInSlot(stack, inventory, i) && slotStack.getCount() > 0) {
                stack.decrement(1);
                slotStack.increment(1);
                return true;
            }
        }
        for (int i = 0; i < inventory.size(); i++) {
            //On the second pass, find empty stacks we can add to.
            ItemStack slotStack = inventory.getStack(i);
            if (GolemHelper.canInsertInSlot(stack, inventory, i) && slotStack.isEmpty()) {
                ItemStack newStack = stack.copy();
                newStack.setCount(1);
                inventory.setStack(i, newStack);
                stack.decrement(1);
                return true;
            }
        }
        return false;
    }

    public static boolean sameDoubleInventory(BlockPos left, BlockPos right, ServerWorld world) {
        //Check whether two BlockPos are part of the same DoubleInventory, i.e. in the case of a double chest.
        Inventory inventory = GolemHelper.getInventory(left, world);
        BlockEntity leftEntity = world.getBlockEntity(left);
        BlockEntity rightEntity = world.getBlockEntity(right);
        if (!(inventory instanceof DoubleInventory) || !(leftEntity instanceof Inventory) || !(rightEntity instanceof Inventory)) {
            return false;
        }
        DoubleInventory doubleInventory = (DoubleInventory) inventory;
        return (doubleInventory.isPart((Inventory) leftEntity)) && (doubleInventory.isPart((Inventory) rightEntity));
    }

    // Helper functions for golem teleportation.

    public static boolean tryTeleportTo(AbstractGolemEntity entity, LivingEntity target) {
        for (int i = 0; i < 10; ++i) {
            double x = target.getX() + getRandomInt((Random) entity.getRandom(), -3, 3);
            double y = target.getY() + getRandomInt((Random) entity.getRandom(), -1, 1);
            double z = target.getZ() + getRandomInt((Random) entity.getRandom(), -3, 3);
            BlockPos pos = new BlockPos(x, y, z);
            if (canTeleportTo(pos, entity)) {
                entity.teleport(x, y, z);
                entity.getNavigation().stop();
                return true;
            }
        }
        return false;
    }

    public static boolean canTeleportTo(BlockPos pos, AbstractGolemEntity entity) {
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(entity.world, pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        return entity.world.getBlockState(pos).isAir() && entity.world.getBlockState(pos.up()).isAir();
    }

    public static int getRandomInt(Random rand, int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}