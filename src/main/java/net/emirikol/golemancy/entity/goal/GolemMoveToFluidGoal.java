package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GolemMoveToFluidGoal extends GolemMoveGoal {
    private static final int FILL_RANGE = 3;

    public GolemMoveToFluidGoal(AbstractGolemEntity entity, float maxYDifference) {
        super(entity, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return GolemHelper.hasEmptyVessel(this.entity) && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return GolemHelper.hasEmptyVessel(this.entity) && super.shouldContinue();
    }

    @Override
    public void tick() {
        //Attempt to look at fluid.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        ServerWorld world = (ServerWorld) this.entity.world;
        FluidState fluidState = world.getBlockState(pos).getFluidState();
        ItemStack vessel = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);

        if (vessel.getItem() == Items.BUCKET) {
            //Buckets should be able to drain any fluid.
            return fluidState.isStill() && !fluidState.isEmpty() && super.isTargetPos(pos);
        }
        if (vessel.getItem() == Items.GLASS_BOTTLE) {
            //Glass bottles can only drain water.
            return fluidState.getFluid() == Fluids.WATER && fluidState.isStill() && !fluidState.isEmpty() && super.isTargetPos(pos);
        }

        return false;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return FILL_RANGE;
    }
}