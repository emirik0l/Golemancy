package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.*;
import net.minecraft.tag.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;


public class GolemFillBucketGoal extends Goal {
	private final AbstractGolemEntity entity;
	
	private BlockPos fluidPos;
	
	public GolemFillBucketGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
		return isFluidNearby() && GolemHelper.hasEmptyBucket(this.entity);
	}

	@Override
	public void tick() {
		BlockState state = this.entity.world.getBlockState(this.fluidPos);
		ServerWorld world = (ServerWorld) this.entity.world;
		ItemStack stack = ((FluidDrainable)state.getBlock()).tryDrainFluid(world, this.fluidPos, state);
		if (stack != ItemStack.EMPTY) {
			SoundEvent sound = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL;
			entity.world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
			this.entity.equipStack(EquipmentSlot.MAINHAND, stack);
		}
	}
	
	public boolean isFluidNearby() {
		BlockPos pos = this.entity.getBlockPos();
		ServerWorld world = (ServerWorld) this.entity.world;

		for (BlockPos curPos: BlockPos.iterateOutwards(pos, 3, 3, 3)) {
			if (isFluidDrainable(curPos, world)) {
				this.fluidPos = curPos;
				return true;
			}
		}
		return false;
	}
	
	public boolean isFluidDrainable(BlockPos pos, ServerWorld world) {
		Block block = world.getBlockState(pos).getBlock();
		FluidState fluidState = world.getBlockState(pos).getFluidState();
		return fluidState.isStill() && !fluidState.isEmpty() && block instanceof FluidDrainable;
	}
}