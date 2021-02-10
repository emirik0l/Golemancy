package net.emirikol.amm.item;

import net.emirikol.amm.*;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.server.world.*;

import java.util.*;

public class ClayEffigy extends Item {
	public ClayEffigy(Settings settings) {
		super(settings);
	}
	
	//Logic to spawn the entity when right-clicked.
	//Taken from SpawnEggItem in vanilla.
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			ItemStack itemStack = context.getStack();
			Direction direction = context.getSide();
			BlockPos blockPos = context.getBlockPos();
			BlockState blockState = world.getBlockState(blockPos);
			BlockPos blockPos2;
			if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
				blockPos2 = blockPos;
			} else {
				blockPos2 = blockPos.offset(direction);
			}
			if (AriseMyMinionsMod.CLAY_EFFIGY_ENTITY.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
				itemStack.decrement(1);
			}
			return ActionResult.CONSUME;
		}
	}
}