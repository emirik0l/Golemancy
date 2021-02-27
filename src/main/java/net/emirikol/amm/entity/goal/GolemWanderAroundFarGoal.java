package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.goal.*;

public class GolemWanderAroundFarGoal extends WanderAroundFarGoal {
	public GolemWanderAroundFarGoal(PathAwareEntity pathAwareEntity, double d) {
		super(pathAwareEntity, d);
	}
	
	@Override
	public boolean canStart() {
		ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) this.mob;
		if (!clayEffigyEntity.canWanderAroundFar()) {
			return false;
		}
		return super.canStart();
	}
}