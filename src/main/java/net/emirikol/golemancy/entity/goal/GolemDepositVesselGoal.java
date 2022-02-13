package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

public class GolemDepositVesselGoal extends GolemDepositHeldItemGoal {
	
	public GolemDepositVesselGoal(AbstractGolemEntity entity) {
		super(entity);
	}
	
	@Override
	protected boolean linkedBlockCanInsert() {
		//If the golem has anything OTHER than an empty bucket, they can insert.
		return !GolemHelper.hasEmptyVessel(this.entity) && super.linkedBlockCanInsert();
	}
}