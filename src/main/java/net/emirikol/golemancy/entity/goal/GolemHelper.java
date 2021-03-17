package net.emirikol.golemancy.entity.goal;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.*;

public class GolemHelper {
	public static boolean canReach(PathAwareEntity entity, BlockPos pos) {
		Path path = entity.getNavigation().findPathTo(pos, 0);
		if (path == null) { return false; }
		PathNode pathNode = path.getEnd();
		if (pathNode == null) { return false; }
		return entity.isInWalkTargetRange(pos);
	}
}