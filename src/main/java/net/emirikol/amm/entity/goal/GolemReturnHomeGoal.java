package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemReturnHomeGoal extends MoveToTargetPosGoal {
	
	public GolemReturnHomeGoal(ClayEffigyEntity entity, double speed, int range, int maxYDifference) {
		super((PathAwareEntity) entity, speed, range, maxYDifference);
	}
	
	@Override
	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		ClayEffigyEntity entity = (ClayEffigyEntity) this.mob;
		BlockPos linkedPos = entity.getLinkedBlockPos();
		if (linkedPos == null) { return false; }
		return (pos.getX() == linkedPos.getX()) && (pos.getY() == linkedPos.getY()) && (pos.getZ() == linkedPos.getZ());
	}
	
	@Override
	protected boolean findTargetPos() {
		ClayEffigyEntity entity = (ClayEffigyEntity) this.mob;
		BlockPos linkedPos = entity.getLinkedBlockPos();
		if (linkedPos == null) { return false; }
		this.targetPos = linkedPos;
		return entity.world.getBlockState(linkedPos) != null;
	}
	
	@Override
	protected int getInterval(PathAwareEntity mob) {
		return 20;
	}
}