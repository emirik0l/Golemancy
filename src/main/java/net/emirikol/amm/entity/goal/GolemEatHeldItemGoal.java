package net.emirikol.amm.entity.goal;

import net.emirikol.amm.entity.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemEatHeldItemGoal extends Goal {
	private final AbstractGolemEntity entity;
	private final List<String> validTypes;
	
	private int eatingTimer;
	
	public GolemEatHeldItemGoal(AbstractGolemEntity entity, String[] validTypes) {
		this.entity = entity;
		this.validTypes = Arrays.asList(validTypes);
	}
	
	public boolean canStart() {
		//Check if the golem is the correct type for this behaviour.
		String golemType = entity.getGolemType();
		if (!this.validTypes.contains(golemType)) {
			return false;
		}
		return !entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
	}
	
	@Override
	public void start() {
		this.setEating();
	}
	
	@Override
	public void tick() {
		if (!this.isEating()) {
			entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			entity.world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), entity.getEatSound(entity.getEquippedStack(EquipmentSlot.MAINHAND)), SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
		} else {
			this.eatingTimer--;
		}
	}
	
	private boolean isEating() {
		return (this.eatingTimer > 0);
	}
	
	private void setEating() { 
		this.eatingTimer = 10; 
	}
}