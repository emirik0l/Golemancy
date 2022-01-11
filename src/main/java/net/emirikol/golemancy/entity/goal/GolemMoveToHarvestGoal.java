package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.block.*;
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
        boolean isCrop = block instanceof CropBlock && ((CropBlock) block).isMature(state);
        boolean isGourd = block instanceof GourdBlock;
        boolean isCane = block instanceof SugarCaneBlock && this.entity.world.getBlockState(pos.down()).getBlock() instanceof SugarCaneBlock;
        return isCrop || isGourd || isCane;
    }

    @Override
    protected int getMaxProgress() {
        return 40;
    }
}
