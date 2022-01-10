package net.emirikol.golemancy.entity.goal;

import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

public class GolemHelper {
	public static boolean hasEmptyBucket(LivingEntity entity) {
		ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
		return stack.getItem() == Items.BUCKET;
	}
	
	// Helper function to get inventory from a BlockPos; supports double chests.
	public static Inventory getInventory(BlockPos pos, ServerWorld world) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		BlockState state = world.getBlockState(pos);
		if (blockEntity == null || state == null) { return null; }
		if (blockEntity instanceof ChestBlockEntity) {
			ChestBlock block = (ChestBlock) state.getBlock();
			return ChestBlock.getInventory(block, state, world, pos, true);
		}
		if (blockEntity instanceof Inventory) {
			return (Inventory) blockEntity;
		}
		return null;
	}
}