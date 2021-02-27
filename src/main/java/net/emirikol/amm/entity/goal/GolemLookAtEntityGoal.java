package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.goal.*;

public class GolemLookAtEntityGoal extends LookAtEntityGoal {
	public GolemLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
		super(mob, targetType, range);
	}
	
	@Override
	public boolean canStart() {
		ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) this.mob;
		if (!clayEffigyEntity.canLookAround()) {
			return false;
		}
		return super.canStart();
	}
}