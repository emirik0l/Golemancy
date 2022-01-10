package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GolemMoveToHeldBlockGoal extends GolemMoveGoal {

    public GolemMoveToHeldBlockGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity, searchRadius, maxYDifference);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        //Can only search for the block in your hand if you're actually holding something.
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) { return false; }
        return stack.getItem() instanceof BlockItem && super.canStart();
    }

    @Override
    public void tick() {
        //Attempt to look at block.
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
        //Continue towards targetPos.
        super.tick();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) { return false; }
        BlockItem item = (BlockItem) stack.getItem();
        Block block = item.getBlock();
        return block == this.entity.world.getBlockState(pos).getBlock();
    }

    @Override
    public boolean canReachPos(BlockPos pos) {
        // Curious golems are supposed to path towards blocks even if they can't actually reach them.
        return this.entity.isInWalkTargetRange(pos);
    }
}
