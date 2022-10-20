package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GolemMoveToMineGoal extends GolemMoveToBreakGoal {

    public GolemMoveToMineGoal(AbstractGolemEntity entity, float maxYDifference) {
        super(entity, maxYDifference);
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        //Must match the Block type of the golem's linked block.
        BlockState state = this.entity.world.getBlockState(pos);
        if (state == null) {
            return false;
        }
        Block linkedBlock = this.entity.getLinkedBlock();
        if (linkedBlock == null) {
            return false;
        }

        return (state.getBlock() == linkedBlock) && super.isTargetPos(pos);
    }

    protected int getMaxProgress() {
        return 120;
    }
}
