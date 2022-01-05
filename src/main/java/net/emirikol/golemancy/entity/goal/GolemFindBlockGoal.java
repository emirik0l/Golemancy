package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class GolemFindBlockGoal extends GolemMoveToBlockGoal {

    public GolemFindBlockGoal(AbstractGolemEntity entity, float searchRadius) {
        super(entity, searchRadius);
    }

    @Override
    public boolean canStart() {
        //Can only search for the block in your hand if you're actually holding something.
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) { return false; }
        return stack.getItem() instanceof BlockItem && super.canStart();
    }

    @Override
    public boolean isTargetPos(BlockPos pos) {
        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) { return false; }
        BlockItem item = (BlockItem) stack.getItem();
        Block block = item.getBlock();
        return block == this.entity.world.getBlockState(pos).getBlock();
    }

}
