package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.emirikol.golemancy.util.ModSupport;
import net.minecraft.block.CropBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class GolemExtractSeedsGoal extends GolemExtractItemGoal {
    public GolemExtractSeedsGoal(AbstractGolemEntity entity) {
        super(entity);
    }

    @Override
    protected boolean canTake(ItemStack stack) {
        //An item is a seed if it is an AliasedBlockItem that places something which extends CropBlock or StemBlock.
        if (stack.getItem() instanceof BlockItem) {
            BlockItem item = (BlockItem) stack.getItem();
            boolean crop = item.getBlock() instanceof CropBlock;
            boolean stem = item.getBlock() instanceof StemBlock;
            boolean modSeed = ModSupport.isModdedSeed(stack);
            return (crop || stem || modSeed) && super.canTake(stack);
        }

        return false;
    }
}
