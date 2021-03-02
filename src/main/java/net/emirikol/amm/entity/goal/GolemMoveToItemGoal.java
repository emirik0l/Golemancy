package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemMoveToItemGoal extends Goal {
	private final ClayEffigyEntity entity;
	private final float searchRadius;
	private final List<String> validTypes;
	
	public GolemMoveToItemGoal(ClayEffigyEntity entity, float searchRadius, String[] validTypes) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.validTypes = Arrays.asList(validTypes);
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		String golemType = entity.getGolemType();
		if (!this.validTypes.contains(golemType)) {
			return false;
		}
		//Check if there is an ItemEntity in the search radius and the golem's hand is empty.
		float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		if ((list.isEmpty()) || !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			return false;
		}
		//Return true if any of the nearby items can be reached.
		for (ItemEntity itemEntity: list) {
			if (canNavigateToEntity(itemEntity)) {
				return true;
			}
		}
		//Otherwise, return false.
		return false;
	}
	
	public void tick() {
		//Check if there is an item within 1.5 blocks and the golem's hand is empty.
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(1.5F,1.5F,1.5F), null);
		if (!list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			//Take 1 item from the stack.
			ItemStack stack = list.get(0).getStack();
			entity.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
		}
		//Check if there is an ItemEntity in the search radius and the golem's hand is empty.
		float r = this.searchRadius + (10.0F * entity.getGolemSmarts());
		list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		if (!list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
			//Iterate through nearby items until one is found that is reachable, and set it as the target.
			for (ItemEntity itemEntity: list) {
				if (canNavigateToEntity(itemEntity)) {
					entity.getNavigation().startMovingTo(itemEntity, 1);
					return;
				}
			}
		}
	}
	

	private boolean canNavigateToEntity(Entity entity) {
		Path path = this.entity.getNavigation().findPathTo((Entity)entity, 0);
		if (path == null) {
			return false;
		} else {
			PathNode pathNode = path.getEnd();
			if (pathNode == null) {
				return false;
			} else {
				int i = pathNode.x - MathHelper.floor(entity.getX());
				int j = pathNode.z - MathHelper.floor(entity.getZ());
				int k = pathNode.y - MathHelper.floor(entity.getY());
				return (double)(i * i + j * j + k * k) <= 3.375;
			}
		}
	}
}