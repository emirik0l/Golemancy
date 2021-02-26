package net.emirikol.amm.entity;

import net.emirikol.amm.*;
import net.emirikol.amm.item.*;
import net.emirikol.amm.genetics.*;
import net.emirikol.amm.component.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.item.*;
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
		this.fromComponent();
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
	public boolean canBreatheInWater() {
		return true;
	}
	
	
	public void toComponent() {
		GolemComponent component = AriseMyMinionsComponents.GOLEM.get(this);
		component.setType(this.type);
		component.setAttribute("strength", this.strength);
		component.setAttribute("agility", this.agility);
		component.setAttribute("vigor", this.vigor);
		component.setAttribute("smarts", this.smarts);
	}
	
	public void fromComponent() {
		GolemComponent component = AriseMyMinionsComponents.GOLEM.get(this);
		this.type = component.getType();
		this.strength = component.getAttribute("strength");
		this.agility = component.getAttribute("agility");
		this.vigor = component.getAttribute("vigor");
		this.smarts = component.getAttribute("smarts");
	}
	

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.world.isClient()) {
			return ActionResult.PASS;
		}
		ItemStack stack = player.getStackInHand(hand);
		ServerWorld world = (ServerWorld) this.world;
		if (stack.getItem() instanceof SoulstoneFilled) {
			//Load genome from soulstone.
			Genome genome = new Genome(stack);
			Gene<String> typeGene = genome.get("type");
			Gene<Integer> strengthGene = genome.get("strength");
			Gene<Integer> agilityGene = genome.get("agility");
			Gene<Integer> vigorGene = genome.get("vigor");
			Gene<Integer> smartsGene = genome.get("smarts");
			//Update tracked values from genome.
			this.type = typeGene.getActive();
			this.strength = strengthGene.getActive();
			this.agility = agilityGene.getActive();
			this.vigor = vigorGene.getActive();
			this.smarts = smartsGene.getActive();
			//Save tracked values using Cardinal Components.
			this.toComponent();
			//Remove the soulstone.
			stack.decrement(1);
			return ActionResult.SUCCESS;
		} 
		return ActionResult.PASS;
	}
}