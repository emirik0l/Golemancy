package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;


public class GolemFillVesselGoal extends Goal {
    private static final int FILL_RANGE = 3;

    private final AbstractGolemEntity entity;
    protected int cooldown;

    private BlockPos fluidPos;

    public GolemFillVesselGoal(AbstractGolemEntity entity) {
        this.entity = entity;
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = ConfigurationHandler.getGolemCooldown();
        return isFluidNearby() && GolemHelper.hasEmptyVessel(this.entity);
    }

    @Override
    public void tick() {
        ItemStack stack = this.drainFluid(this.fluidPos);
        if (stack != ItemStack.EMPTY) {
            entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
            this.entity.equipStack(EquipmentSlot.MAINHAND, stack);
        }
    }

    public boolean isFluidNearby() {
        BlockPos pos = this.entity.getBlockPos();
        ServerWorld world = (ServerWorld) this.entity.world;

        for (BlockPos curPos : BlockPos.iterateOutwards(pos, FILL_RANGE, FILL_RANGE, FILL_RANGE)) {
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

    public ItemStack drainFluid(BlockPos pos) {
        ServerWorld world = (ServerWorld) this.entity.world;
        BlockState state = world.getBlockState(pos);
        FluidDrainable fluidBlock = (FluidDrainable) state.getBlock();
        ItemStack vessel = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);

        if (vessel.getItem() == Items.BUCKET) {
            return fluidBlock.tryDrainFluid(world, pos, state);
        }
        if (vessel.getItem() == Items.GLASS_BOTTLE) {
            FluidState fluidState = state.getFluidState();
            if (fluidState.getFluid() == Fluids.WATER) {
                fluidBlock.tryDrainFluid(world, pos, state);
                return PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
            }
        }

        return ItemStack.EMPTY;
    }
}