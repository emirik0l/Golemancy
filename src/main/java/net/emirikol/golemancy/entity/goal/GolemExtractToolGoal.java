package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;

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