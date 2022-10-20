package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;

import java.util.EnumSet;

public class GolemFollowOwnerGoal extends Goal {

    protected final AbstractGolemEntity entity;
    private final double speed;
    private final EntityNavigation navigation;
    private final float minDistance;
    private LivingEntity owner;
    private int updateCountdownTicks;
    private float oldWaterPathfindingPenalty;

    public GolemFollowOwnerGoal(AbstractGolemEntity entity, double speed, float minDistance) {
        this.entity = entity;
        this.speed = speed;
        this.navigation = this.entity.getNavigation();
        this.minDistance = minDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        if (!(this.entity.getNavigation() instanceof MobNavigation) && !(this.entity.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for GolemFollowOwnerGoal");
        }
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.entity.getOwner();
        if (livingEntity == null) return false;
        if (livingEntity.isSpectator()) return false;
        if (this.entity.isSitting()) return false;
        if (this.entity.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) return false;
        if (!this.entity.isFollowingWand()) return false;

        this.owner = livingEntity;
        return true;
    }

    public boolean shouldContinue() {
        return !this.navigation.isIdle() && this.canStart();
    }

    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.entity.getPathfindingPenalty(PathNodeType.WATER);
        this.entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.entity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    public void tick() {
        if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            this.navigation.startMovingTo(this.owner, this.speed);
        }
    }
}
