package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

public class GolemDepositBucketGoal extends GolemDepositHeldItemGoal {
	
	public GolemDepositBucketGoal(AbstractGolemEntity entity) {
		super(entity);
	}
	
	@Override
	protected boolean linkedBlockCanInsert() {
		//If the golem has anything OTHER than an empty bucket, they can insert.
		return !GolemHelper.hasEmptyBucket(this.entity) && super.linkedBlockCanInsert();
	}
}