package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class GolemFollowWandGoal extends GolemFollowOwnerGoal {

	public GolemFollowWandGoal(AbstractGolemEntity entity, double speed, float minDistance, float maxDistance) {
		super(entity, speed, minDistance, maxDistance);
	}
	
	@Override
	public boolean canStart() {
		return this.entity.isFollowingWand() && super.canStart();
	}
}
