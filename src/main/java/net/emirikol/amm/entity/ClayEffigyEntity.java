package net.emirikol.amm.entity;

import net.emirikol.amm.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

import java.util.*;

public class ClayEffigyEntity extends TameableEntity {
	public ClayEffigyEntity(EntityType<? extends ClayEffigyEntity> entityType, World world) {
		super(entityType, world);
		this.setTamed(false);
	}
   
	public static DefaultAttributeContainer.Builder createClayEffigyAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
	}
	
	@Override
	protected void initGoals() {
		//Temporary to test effigy model.
		this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.75D));
	}
	
	public ClayEffigyEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		ClayEffigyEntity clayEffigyEntity = (ClayEffigyEntity) AriseMyMinionsMod.CLAY_EFFIGY_ENTITY.create(serverWorld);
		UUID uUID = this.getOwnerUuid();
		
		if (uUID != null) {
			clayEffigyEntity.setOwnerUuid(uUID);
			clayEffigyEntity.setTamed(true);
		}
		return clayEffigyEntity;
	}
}