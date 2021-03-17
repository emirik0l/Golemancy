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
		return !GolemHelper.hasEmptyBucket(this.entity) && super.linkedBlockCanInsert();
	}
}