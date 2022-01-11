package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;

public class GolemMoveToHarvestGoal extends GolemMoveToBreakGoal {
    public GolemMoveToHarvestGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity, searchRadius, maxYDifference);
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        //Any mature crop block is a valid targetPos.
        BlockState state = this.entity.world.getBlockState(pos);
        Block block = state.getBlock();
        return block instanceof CropBlock && ((CropBlock) block).isMature(state);
    }

    @Override
    public boolean canBreak(BlockPos pos) {
        if (pos.isWithinDistance(this.entity.getPos(), BREAK_RANGE)) {
            BlockState state = this.entity.world.getBlockState(pos);
            Block block = state.getBlock();
            return block instanceof CropBlock && ((CropBlock) block).isMature(state);
        }
        return false;
    }

    @Override
    protected int getMaxProgress() {
        return 40;
    }
}
