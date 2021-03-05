package net.emirikol.amm.entity;

import net.emirikol.amm.*;
import net.emirikol.amm.item.*;
import net.emirikol.amm.entity.goal.*;
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
import net.minecraft.nbt.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.server.world.*;

import java.util.*;

public abstract class AbstractGolemEntity extends TameableEntity {
	private String type;
	private int strength,agility,vigor,smarts;
	private BlockPos linkedBlockPos;
	
	private boolean golemWandFollow;
	
	public AbstractGolemEntity(EntityType<? extends AbstractGolemEntity> entityType, World world) {
		super(entityType, world);
		this.setTamed(false);
		this.stepHeight = 1.0F;
		this.golemWandFollow = false;
	}
	
	public static DefaultAttributeContainer.Builder createGolemAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
	}
	
	@Override 
	protected void initGoals() {
		this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(15, new GolemReturnHomeGoal(this, 1.0D));
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
		component.setLinkedBlockPos(this.linkedBlockPos);
	}
	
	public void fromComponent() {
		GolemComponent component = AriseMyMinionsComponents.GOLEM.get(this);
		this.type = component.getType();
		this.strength = component.getAttribute("strength");
		this.agility = component.getAttribute("agility");
		this.vigor = component.getAttribute("vigor");
		this.smarts = component.getAttribute("smarts");
		this.linkedBlockPos = component.getLinkedBlockPos();
	}
	
	
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.world.isClient()) {
			return super.interactMob(player, hand);
		}
		//Ignore the off-hand.
		if (hand == Hand.OFF_HAND) {
			return super.interactMob(player, hand);
		}
		//Ignore the golem wand.
		if (player.getStackInHand(hand).getItem() == AriseMyMinionsMod.GOLEM_WAND) {
			return super.interactMob(player, hand);
		}
		//The following functionality is only available to the golem's owner.
		if (this.isOwner(player)) {
			//Try to take items from the golem.
			if (!this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
				return tryTakeFromGolem(player);
			}
			//Try to give items to the golem.
			if ((this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) && (!player.getStackInHand(hand).isEmpty())) {
				return tryGiveToGolem(player, hand);
			}
		}
		return super.interactMob(player, hand);
	}
	
	private ActionResult tryTakeFromGolem(PlayerEntity player) {
		ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
		ServerWorld world = (ServerWorld) this.world;
		player.inventory.offerOrDrop(world, stack);
		this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		return ActionResult.SUCCESS;
	}
	
	private ActionResult tryGiveToGolem(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		this.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
		return ActionResult.SUCCESS;
	}
	
	public void updateAttributes() {
		this.fromComponent();
		EntityAttributeInstance entityAttributeInstance;
		//Update attack damage based on strength.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		entityAttributeInstance.setBaseValue(getAttackDamageFromStrength(this.strength));
		//Update movement speed based on agility.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		entityAttributeInstance.setBaseValue(getMovementSpeedFromAgility(this.agility));
		//Update health based on vigor.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		entityAttributeInstance.setBaseValue(getHealthFromVigor(this.vigor));
		this.heal(20.0F);
	}
	
	public void setGolemType(String type) {
		this.type = type;
		this.toComponent();
	}
	
	public String getGolemType() {
		this.fromComponent();
		return this.type;
	}
	
	public void setGolemStats(int str, int agi, int vig, int sma) {
		this.strength = str;
		this.agility = agi;
		this.vigor = vig;
		this.smarts	= sma;
		this.toComponent();
	}
	
	public Integer getGolemSmarts() {
		this.fromComponent();
		return this.smarts;
	}
	
	public BlockPos getLinkedBlockPos() {
		this.fromComponent();
		return this.linkedBlockPos;
	}
	
	public void linkToBlockPos(BlockPos pos) {
		this.linkedBlockPos = pos;
		this.toComponent();
	}
	
	public boolean isFollowingWand() {
		return this.golemWandFollow;
	}
	
	public void toggleFollowingWand() {
		this.golemWandFollow = !this.golemWandFollow;
	}
	
	public double getAttackDamageFromStrength(int strength) {
		switch(strength) {
			case 0:
				return 1.0D;
			case 1:
				return 2.0D;
			case 2:
				return 3.0D;
			case 3:
				return 4.0D;
			default:
				return 0.0D;
		}
	}
	
	public double getMovementSpeedFromAgility(int agility) {
		switch(agility) {
			case 0:
				return 0.2D;
			case 1:
				return 0.25D;
			case 2:
				return 0.3D;
			case 3:
				return 0.5D;
			default:
				return 0.0D;
		}
	}
	
	public double getHealthFromVigor(int vigor) {
		switch(vigor) {
			case 0:
				return 8.0D;
			case 1:
				return 10.0D;
			case 2:
				return 12.0D;
			case 3:
				return 16.0D;
			default:
				return 0.0D;
		}
	}
}