package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.goal.*;

import java.util.*;

public class GolemLookAtEntityGoal extends LookAtEntityGoal {
	private static final List<String> VALID_TYPES = Arrays.asList(new String[]{"Restless", "Curious", "Hungry"});
	
	public GolemLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
		super(mob, targetType, range);
	}
	
	@Override
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) this.mob;
		String golemType = clayEffigyEntity.getGolemType();
		if (!VALID_TYPES.contains(golemType)) {
			return false;
		}
		return super.canStart();
	}
}