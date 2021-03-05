package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.goal.*;

import java.util.*;

public class GolemLookAtEntityGoal extends LookAtEntityGoal {
	private final List<String> validTypes;
	
	public GolemLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range, String[] validTypes) {
		super(mob, targetType, range);
		this.validTypes = Arrays.asList(validTypes);
	}
	
	@Override
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		AbstractGolemEntity golemEntity = (AbstractGolemEntity) this.mob;
		String golemType = golemEntity.getGolemType();
		if (!this.validTypes.contains(golemType)) {
			return false;
		}
		return super.canStart();
	}
}