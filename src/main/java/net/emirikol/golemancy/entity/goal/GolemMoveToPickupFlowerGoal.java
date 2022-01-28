package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class GolemMoveToPickupFlowerGoal extends GolemMoveToPickupGoal{
    public GolemMoveToPickupFlowerGoal(AbstractGolemEntity entity, float searchRadius, float maxYDifference) {
        super(entity, searchRadius, maxYDifference);
    }

    @Override
    public boolean canPickUp(ItemEntity entity) {
        Item item = entity.getStack().getItem();
        if (item instanceof BlockItem) {
            BlockItem blockItem = (BlockItem) item;
            return blockItem.getBlock() instanceof FlowerBlock;
        }
        return false;
    }
}
