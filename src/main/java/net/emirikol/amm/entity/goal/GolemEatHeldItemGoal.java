package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemEatHeldItemGoal extends Goal {
	private static final List<String> VALID_TYPES = Arrays.asList(new String[]{"Hungry"});
	
	private final ClayEffigyEntity entity;
	
	public GolemEatHeldItemGoal(ClayEffigyEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		String golemType = entity.getGolemType();
		if (!VALID_TYPES.contains(golemType)) {
			return false;
		}
		return !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
	
	public void tick() {
		//TODO - add some kind of animation
		entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
	}
}