package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.network.Particles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public abstract class GolemMoveToBreakGoal extends GolemMoveGoal {
    protected static final double BREAK_RANGE = 5.0D;

    protected int breakProgress;
    protected int prevBreakProgress;

    public GolemMoveToBreakGoal(AbstractGolemEntity entity, float maxYDifference) {
        super(entity, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public void tick() {
        //Attempt to look at block.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Attempt to break block.
        if (this.isWithinBreakRange(this.targetPos)) {
            if (this.canBreak(this.targetPos)) {
                if (this.entity.getRandom().nextInt(5) == 0) this.entity.trySwing();

                this.breakProgress++;
                int i = (int) ((float) this.breakProgress / (float) this.getMaxProgress() * 10.0F);
                if (i != this.prevBreakProgress) {
                    this.entity.world.setBlockBreakingInfo(this.entity.getId(), this.targetPos, i);
                    this.prevBreakProgress = i;
                }

                if (this.breakProgress == this.getMaxProgress()) {
                    this.entity.world.breakBlock(this.targetPos, true);
                    this.breakProgress = 0;
                    this.prevBreakProgress = 0;
                    this.entity.world.syncWorldEvent(2001, this.targetPos, Block.getRawIdFromState(this.entity.world.getBlockState(this.targetPos)));
                }
            } else if (this.entity.getRandom().nextInt(20) == 0) {
                //Frustration particle if block can't be broken.
                Particles.smokeParticle(this.entity);
            }
        }
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        //Override in subclasses to determine exactly what kinds of block are broken.
        return super.isTargetPos(pos);
    }

    @Override
    public boolean canReachPos(BlockPos pos) {
        //Check if we can path to any block within BREAK_RANGE.
        EntityNavigation nav = this.entity.getNavigation();
        for (BlockPos curPos : BlockPos.iterateOutwards(pos, (int) BREAK_RANGE, (int) BREAK_RANGE, (int) BREAK_RANGE)) {
            if (nav.findPathTo(curPos, 0) != null) return true;
        }
        return false;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return BREAK_RANGE;
    }

    public boolean isWithinBreakRange(BlockPos pos) {
        //Check if a block is close enough to break.
        return pos.isWithinDistance(this.entity.getBlockPos(), BREAK_RANGE);
    }

    public boolean canBreak(BlockPos pos) {
        //Check if we are strong enough to break a block.
        BlockState state = this.entity.world.getBlockState(pos);
        if (state == null) {
            return false;
        }
        float hardness = state.getHardness(this.entity.world, pos);
        return (hardness >= 0) && (hardness <= getBreakingStrength()) && !state.isAir();
    }

    public float getBreakingStrength() {
        return this.entity.getBlockBreakHardnessFromStrength();
    }

    protected abstract int getMaxProgress();
}