package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemTakeItemGoal extends Goal {
	private static final List<String> VALID_TYPES = Arrays.asList(new String[]{"Hungry"});
	
	private final ClayEffigyEntity entity;
	private final float searchRadius;
	
	public GolemTakeItemGoal(ClayEffigyEntity entity, float searchRadius) {
		this.entity = entity;
		this.searchRadius = searchRadius;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}
	
	public boolean canStart() {
		String golemType = entity.getGolemType();
		if (!VALID_TYPES.contains(golemType)) {
			return false;
		}
		float r = this.searchRadius;
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		return !list.isEmpty() && entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
	
	public void tick() {
		//Check for any ItemEntity within 1 block.
		List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(1.0D, 1.0D, 1.0D), null);
		if (!list.isEmpty()) {
			ItemEntity itemEntity = list.get(0);
			ItemStack stack = itemEntity.getStack();
			entity.equipStack(EquipmentSlot.MAINHAND, stack);
			itemEntity.remove();
			return;
		}
		//Check for any ItemEntity within search radius, and move towards it.
		float r = this.searchRadius;
		list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(r,r,r), null);
		if ((entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) && (!list.isEmpty())) {
			entity.getNavigation().startMovingTo((Entity) list.get(0), 1);
		}
	}
}