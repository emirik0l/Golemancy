package net.emirikol.golemancy.entity.goal;

import net.emirikol.golemancy.event.ConfigurationHandler;
import net.emirikol.golemancy.entity.*;

import net.emirikol.golemancy.network.Particles;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.*;

public class GolemMoveToHealGoal extends GolemMoveGoal {
	private static final float HEAL_AMOUNT = 2.0F;

	private TameableEntity friend;
	private int healingTimer;

	public GolemMoveToHealGoal(AbstractGolemEntity entity, float maxYDifference) {
		super(entity, maxYDifference);
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	public void tick() {
		//Look at friend.
		this.entity.getLookControl().lookAt(this.friend, 10.0F, (float)this.entity.getMaxLookPitchChange());
		//Try to heal friend.
		if (this.isHealing()) {
			this.healingTimer--;
			if (this.healingTimer == 0) {
				this.friend.heal(this.getHealAmount());
				Particles.healParticle(this.friend);
				friend.world.playSound(null, friend.getBlockPos(), SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.NEUTRAL, 1F, 1F);
			}
		} else {
			if (canHeal(friend)) {
				this.setHealing();
				this.entity.tryPray();
			}
		}
		//Continue towards targetPos.
		super.tick();
	}

	@Override
	public void stop() {
		this.healingTimer = 0;
	}

	@Override
	public boolean findTargetPos() {
		float searchRadius = ConfigurationHandler.getGolemRadius();
		float r = searchRadius + (searchRadius * this.entity.getGolemSmarts());
		List<TameableEntity> list = entity.world.getEntitiesByClass(TameableEntity.class, entity.getBoundingBox().expand(r,this.maxYDifference,r), (entity) -> true);
		for (TameableEntity tameable: list) {
			if (wounded(tameable) && (this.entity.getOwnerUuid() != null) && (this.entity.getOwnerUuid().equals(tameable.getOwnerUuid()))) {
				this.friend = tameable;
				this.targetPos = tameable.getBlockPos();
				return true;
			}
		}
		return false;
	}

	public boolean canHeal(TameableEntity healTarget) {
		float searchRadius = ConfigurationHandler.getGolemRadius();
		float r = searchRadius + (searchRadius * this.entity.getGolemSmarts());
		List<TameableEntity> list = entity.world.getEntitiesByClass(TameableEntity.class, entity.getBoundingBox().expand(r,r,r), (entity) -> true);
		for (TameableEntity tameable: list) {
			if (tameable == healTarget && this.wounded(tameable)) {
				return true;
			}
		}
		return false;
	}

	public float getHealAmount() {
		return HEAL_AMOUNT + this.entity.getGolemSmarts();
	}
	
	public boolean wounded(LivingEntity entity) {
		return entity.getHealth() < entity.getMaxHealth();
	}
	public boolean isHealing() {
		return this.healingTimer > 0;
	}
	public void setHealing() {
		this.healingTimer = 80;
	}
}
