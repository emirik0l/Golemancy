package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.event.ConfigurationHandler;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class GolemMoveGoal extends Goal {
    protected final AbstractGolemEntity entity;
    protected final float maxYDifference;
    protected BlockPos targetPos;
    protected int cooldown;
    private final List<BlockPos> failedTargets;
    private int idleTime;

    public GolemMoveGoal(AbstractGolemEntity entity, float maxYDifference) {
        this.entity = entity;
        this.maxYDifference = maxYDifference;
        this.failedTargets = new ArrayList<>();
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = ConfigurationHandler.getGolemCooldown();
        if (this.findTargetPos() && this.canReachPos(this.targetPos)) {
            return true;
        } else {
            //If you can't find any targetPos at all, empty out the failed target list.
            this.failedTargets.clear();
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        //Continue as long as targetPos is valid and less than 40 ticks of idling have occurred.
        return this.idleTime < 40 && this.isTargetPos(this.targetPos);
    }

    @Override
    public void start() {
        this.entity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1);
        this.idleTime = 0;
    }

    @Override
    public void tick() {
        if (!this.targetPos.isWithinDistance(this.entity.getPos(), this.getDesiredDistanceToTarget())) {
            //Continue towards targetPos.
            if (this.entity.getNavigation().isIdle()) this.idleTime++;

            if (this.idleTime >= 40) {
                //Give up after 40 ticks of idling, and add the targetPos to the list of failed targets.
                this.failedTargets.add(this.targetPos);
            } else {
                this.entity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1);
            }
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    public double getDesiredDistanceToTarget() {
        return 1.0D;
    }

    public boolean findTargetPos() {
        float searchRadius = ConfigurationHandler.getGolemRadius();
        BlockPos pos = this.entity.getLinkedBlockPos();
        if (pos == null) {
            pos = this.entity.getBlockPos();
        }

        float r = searchRadius + (searchRadius * entity.getGolemSmarts());
        for (BlockPos curPos : BlockPos.iterateOutwards(pos, (int) r, (int) this.maxYDifference, (int) r)) {
            if (this.entity.isInWalkTargetRange(curPos) && isTargetPos(curPos)) {
                this.targetPos = curPos;
                return true;
            }
        }
        return false;
    }

    public boolean isTargetPos(BlockPos pos) {
        //Used to determine whether a given BlockPos qualifies to be our targetPos.
        //By default, just disallows any targetPos we have already tried and failed to reach.
        return !this.failedTargets.contains(pos);
    }

    public boolean canReachPos(BlockPos pos) {
        //For most golems, we want to be able to path to the targetPos itself in order to consider it reachable.
        //Override for certain golems where this isn't the case (i.e. you want to get within X blocks of targetPos).
        return this.entity.getNavigation().findPathTo(pos, 0) != null;
    }
}