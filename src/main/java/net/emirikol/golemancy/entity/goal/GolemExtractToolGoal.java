package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.AbstractGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class GolemExtractToolGoal extends GolemExtractItemGoal {
    public GolemExtractToolGoal(AbstractGolemEntity entity) {
        super(entity);
    }

    @Override
    protected boolean canTake(ItemStack stack) {
        if (stack.getItem() instanceof ToolItem) {
            return super.canTake(stack);
        } else {
            return false;
        }
    }
}