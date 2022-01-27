package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.GolemancyConfig;
import net.emirikol.golemancy.entity.AbstractGolemEntity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class GolemLookAtHeldBlockGoal extends Goal {
    protected final AbstractGolemEntity entity;
    protected float searchRadius;
    protected float maxYDifference;
    protected int cooldown;

    protected BlockPos targetPos;

    private int lookModifier = 1;

    public GolemLookAtHeldBlockGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        this.entity = entity;
        this.searchRadius = searchRadius;
        this.maxYDifference = maxYDifference;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = GolemancyConfig.getGolemCooldown();
        //Can only search for the block in your hand if you're actually holding something.
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        return !stack.isEmpty() && stack.getItem() instanceof BlockItem && this.findTargetPos();
    }

    @Override
    public boolean shouldContinue() {
        //Ignore cooldown on shouldContinue() to stop golem looking away from targetPos during the cooldown.
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        return !stack.isEmpty() && stack.getItem() instanceof BlockItem && this.findTargetPos();
    }

    @Override
    public void tick() {
        //Attempt to look at block.
        //Look modifier is applied to make golems bob their heads when they have found something.
        if (this.entity.getRandom().nextInt(5) == 0) lookModifier = -lookModifier;
        this.entity.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY() + lookModifier, this.targetPos.getZ());
    }

    public boolean findTargetPos() {
        BlockPos pos = this.entity.getBlockPos();
        float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
        for (BlockPos curPos: BlockPos.iterateOutwards(pos, (int)r, (int) this.maxYDifference, (int)r)) {
            if (isTargetPos(curPos)) {
                this.targetPos = curPos;
                return true;
            }
        }
        return false;
    }

    public boolean isTargetPos(BlockPos pos) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) { return false; }
        BlockItem item = (BlockItem) stack.getItem();
        return item.getBlock() == this.entity.world.getBlockState(pos).getBlock();
    }
}
