package net.emirikol.amm.entity;

import net.emirikol.amm.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.server.world.*;

import java.util.*;

public class ClayEffigyEntity extends TameableEntity {
	private String type;
	private int strength,agility,vigor,smarts;
	
	public ClayEffigyEntity(EntityType<? extends ClayEffigyEntity> entityType, World world) {
		super(entityType, world);
		this.setTamed(false);
		this.type = "";
		this.strength = 0;
		this.agility = 0;
		this.vigor = 0;
		this.smarts = 0;
	}
   
	public static DefaultAttributeContainer.Builder createClayEffigyAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
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
	
	@Override
	protected int getCurrentExperience(PlayerEntity player) {
		return 0;
	}
	
	@Override
	protected Text getDefaultName() {
		if (this.type == "") {
			Text name = super.getDefaultName();
			return name;
		} else {
			MutableText name = new LiteralText(type + " ");
			name.append(new TranslatableText("text.amm.golem"));
			return name;
		}
	}
	
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		this.type = "Test";
		return ActionResult.PASS;
	}
}