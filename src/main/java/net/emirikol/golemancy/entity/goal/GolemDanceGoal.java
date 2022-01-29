package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GolemDanceGoal extends Goal {
    protected final AbstractGolemEntity entity;

    protected float searchRadius;

    public GolemDanceGoal(AbstractGolemEntity entity, float searchRadius) {
        this.entity = entity;
        this.searchRadius = searchRadius;
        this.setControls(EnumSet.of(Goal.Control.MOVE,Goal.Control.LOOK));
    }

    public boolean canStart() {
        return true;
    }

    @Override
    public void tick() {
        this.entity.tryDance();
    }
}
