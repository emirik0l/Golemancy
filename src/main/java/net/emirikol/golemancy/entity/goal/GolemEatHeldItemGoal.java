package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.entity.*;
import net.emirikol.golemancy.network.*;

import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.*;

public class GolemEatHeldItemGoal extends Goal {
	private final AbstractGolemEntity entity;
	
	private int eatingTimer;
	
	public GolemEatHeldItemGoal(AbstractGolemEntity entity) {
		this.entity = entity;
	}
	
	public boolean canStart() {
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
			entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), entity.getEatSound(entity.getEquippedStack(EquipmentSlot.MAINHAND)), SoundCategory.NEUTRAL, 1.0F, 1.0F + (entity.world.random.nextFloat() - entity.world.random.nextFloat()) * 0.4F);
		} else {
			this.eatingTimer--;
			if (this.eatingTimer % 5 == 0) {
				Particles.foodParticle(this.entity);
			}
		}
	}
	
	private boolean isEating() {
		return (this.eatingTimer > 0);
	}
	
	private void setEating() { 
		this.eatingTimer = 10; 
	}
}