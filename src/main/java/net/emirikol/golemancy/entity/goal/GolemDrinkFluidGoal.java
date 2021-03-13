package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;


public class GolemDrinkFluidGoal extends Goal {
	private final AbstractGolemEntity entity;
	
	private List<Block> filter;
	private BlockPos fluidPos;
	private int drinkingTimer;
	
	public GolemDrinkFluidGoal(AbstractGolemEntity entity) {
		this.entity = entity;
		this.filter = new ArrayList<Block>();
	}
	
	public boolean canStart() {
		return isFluidNearby();
	}
	
	@Override
	public void start() {
		this.setDrinking();
	}
	
	@Override
	public void tick() {
		if (!this.isDrinking()) {
			ServerWorld world = (ServerWorld) this.entity.world;
			BlockState state = world.getBlockState(this.fluidPos);
			FluidDrainable block = (FluidDrainable) state.getBlock();
			block.tryDrainFluid(world, this.fluidPos, state);
			this.fluidPos = null;
		} else {
			this.drinkingTimer--;
			entity.world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
		}
	}
	
	public void add(Block... blocks) {
		//Adds blocks to the filter, marking them as "allowed" to drink.
		//If the filter is empty, anything can be drunk.
		for (Block block: blocks) {
			this.filter.add(block);
		}
	}
	
	public boolean isFluidNearby() {
		BlockPos.Mutable mutable = this.entity.getBlockPos().mutableCopy();
		ServerWorld world = (ServerWorld) this.entity.world;
		for(int i = -1; i <= 1; ++i) {
			for(int j = -1; j <= 1; ++j) {
				for(int k = -1; k <= 1; ++k) {
					mutable.set(this.entity.getX() + (double)i, this.entity.getY() + (double)j, this.entity.getZ() + (double)k);
					if (isFluidDrinkable(mutable, world)) {
						this.fluidPos = mutable;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isFluidDrinkable(BlockPos pos, ServerWorld world) {
		Block block = world.getBlockState(pos).getBlock();
		FluidState fluidState = world.getBlockState(pos).getFluidState();
		if (this.filter.isEmpty()) {
			return fluidState.isStill() && !fluidState.isEmpty();
		} else {
			return  fluidState.isStill() && !fluidState.isEmpty() && this.filter.contains(block);
		}
	}
	
	private boolean isDrinking() {
		return this.drinkingTimer > 0;
	}
	
	private void setDrinking() { 
		this.drinkingTimer = 20; 
	}
}