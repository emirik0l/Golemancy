package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;

public class GolemDepositBucketGoal extends GolemDepositHeldItemGoal {
	
	public GolemDepositBucketGoal(AbstractGolemEntity entity) {
		super(entity);
	}
	
	@Override
	protected boolean linkedBlockCanInsert() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		ItemStack emptyBucket = new ItemStack(Items.BUCKET);
		return !ItemStack.areEqual(stack, emptyBucket) && super.linkedBlockCanInsert();
	}
}