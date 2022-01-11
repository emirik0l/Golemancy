package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;


public class GolemFillBucketGoal extends Goal {
	private static final int FILL_RANGE = 3;

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
		FluidDrainable fluidBlock = (FluidDrainable) state.getBlock();
		ItemStack stack = fluidBlock.tryDrainFluid(world, this.fluidPos, state);
		if (stack != ItemStack.EMPTY) {
			SoundEvent sound = fluidBlock.getBucketFillSound().orElse(SoundEvents.ITEM_BUCKET_FILL);
			entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
			this.entity.equipStack(EquipmentSlot.MAINHAND, stack);
		}
	}
	
	public boolean isFluidNearby() {
		BlockPos pos = this.entity.getBlockPos();
		ServerWorld world = (ServerWorld) this.entity.world;

		for (BlockPos curPos: BlockPos.iterateOutwards(pos, FILL_RANGE, FILL_RANGE, FILL_RANGE)) {
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