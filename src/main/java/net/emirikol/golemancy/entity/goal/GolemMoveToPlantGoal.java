package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.util.ModSupport;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GolemMoveToPlantGoal extends GolemMoveGoal {
    protected static final double PLANT_RANGE = 5.0D;

    public GolemMoveToPlantGoal(AbstractGolemEntity entity, float maxYDifference) {
        super(entity, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.hasSeed() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return this.hasSeed() && super.shouldContinue();
    }

    @Override
    public void tick() {
        //Attempt to look at block.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Attempt to plant block.
        if (this.canPlant(this.targetPos)) {
            this.plant(this.targetPos);
            this.entity.world.playSound(null, this.entity.getBlockPos(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.NEUTRAL, 1F, 1F);
            this.entity.tryAttack();
        }
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        //We can plant seeds on a block if it is farmland and has nothing above it (including existing crops).
        ServerWorld world = (ServerWorld) this.entity.world;
        BlockState state = world.getBlockState(pos);
        return (state.getBlock() == Blocks.FARMLAND) && world.getBlockState(pos.up()).isAir() && super.isTargetPos(pos);
    }

    public boolean hasSeed() {
        //Check whether a given ItemStack is a "seed", i.e. an AliasedBlockItem that places something which extends CropBlock or StemBlock.
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!(stack.getItem() instanceof BlockItem)) return false;

        BlockItem item = (BlockItem) stack.getItem();
        boolean crop = item.getBlock() instanceof CropBlock;
        boolean stem = item.getBlock() instanceof StemBlock;
        boolean modSeed = ModSupport.isModdedSeed(stack);

        return crop || stem || modSeed;
    }

    public boolean canPlant(BlockPos pos) {
        //Check if a block is close enough to plant, and if it can be planted on.
        if (pos.isWithinDistance(this.entity.getBlockPos(), PLANT_RANGE)) {
            return this.hasSeed() && this.isTargetPos(pos);
        }
        return false;
    }

    public void plant(BlockPos pos) {
        //Plant the equipped seed on a piece of farmland.
        ServerWorld world = (ServerWorld) this.entity.world;
        ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
        BlockItem item = (BlockItem) stack.getItem(); //we already checked this is OK in hasSeed()
        world.setBlockState(pos.up(), item.getBlock().getDefaultState());
        stack.decrement(1);
    }
}
