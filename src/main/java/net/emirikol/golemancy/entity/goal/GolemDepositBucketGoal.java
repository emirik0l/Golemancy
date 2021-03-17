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
		return !hasEmptyBucket() && super.linkedBlockCanInsert();
	}
	
	public boolean hasEmptyBucket() {
		ItemStack stack = this.entity.getEquippedStack(EquipmentSlot.MAINHAND);
		return stack.getItem() == Items.BUCKET;
	}
}