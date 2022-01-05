package net.emirikol.golemancy.entity;

import net.emirikol.golemancy.*;
import net.emirikol.golemancy.entity.goal.*;
import net.emirikol.golemancy.network.*;
import net.emirikol.golemancy.component.*;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public abstract class AbstractGolemEntity extends TameableEntity {
	private int strength,agility,vigor,smarts;
	private BlockPos linkedBlockPos;
	private boolean baked;
	
	private boolean golemWandFollow;
	private int attackTicksLeft;
	private int swingTicksLeft;
	
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
		this.goalSelector.add(16, new GolemFollowWandGoal(this, 1.0D, 6.0F, 500.0F));
	}
	
	@Override
	protected void dropInventory() {
		super.dropInventory();
		ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
		if (stack != null && !stack.isEmpty()) {
			this.dropStack(stack);
		}
	}
	
	@Override
	public boolean canBreatheInWater() {
		return true;
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}
	
	public void toComponent() {
		GolemComponent component = GolemancyComponents.GOLEM.get(this);
		component.setAttribute("strength", this.strength);
		component.setAttribute("agility", this.agility);
		component.setAttribute("vigor", this.vigor);
		component.setAttribute("smarts", this.smarts);
		component.setLinkedBlockPos(this.linkedBlockPos);
		component.setBaked(this.baked);
	}
	
	public void fromComponent() {
		GolemComponent component = GolemancyComponents.GOLEM.get(this);
		this.strength = component.getAttribute("strength");
		this.agility = component.getAttribute("agility");
		this.vigor = component.getAttribute("vigor");
		this.smarts = component.getAttribute("smarts");
		this.linkedBlockPos = component.getLinkedBlockPos();
		this.baked = component.isBaked();
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
		if (player.getStackInHand(hand).getItem() == Golemancy.GOLEM_WAND) {
			return super.interactMob(player, hand);
		}
		//The following functionality is only available to the golem's owner.
		if (this.isOwner(player)) {
			//Try to heal the golem.
			if (player.getStackInHand(hand).getItem() == Items.CLAY_BALL) {
				return tryHealGolem(player, hand);
			}
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
	
	private ActionResult tryHealGolem(PlayerEntity player, Hand hand) {
		if (this.getHealth() >= this.getMaxHealth()) {
			return ActionResult.PASS;
		}
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() != Items.CLAY_BALL) {
			return ActionResult.PASS;
		}
		stack.decrement(1);
		this.heal(2.0F);
		Particles.healParticle(this);
		return ActionResult.CONSUME;
	}
	
	private ActionResult tryTakeFromGolem(PlayerEntity player) {
		ItemStack stack = this.getEquippedStack(EquipmentSlot.MAINHAND);
		player.getInventory().offerOrDrop(stack);
		this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		return ActionResult.SUCCESS;
	}
	
	protected ActionResult tryGiveToGolem(PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		this.equipStack(EquipmentSlot.MAINHAND, stack.split(1));
		return ActionResult.SUCCESS;
	}
	
	protected void updateAttributes() {
		//Updates the golem's attributes based on its golemancy stats.
		//Call whenever the golem's stats are updated.
		this.fromComponent();
		EntityAttributeInstance entityAttributeInstance;
		//Update attack damage based on strength.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		entityAttributeInstance.setBaseValue(getAttackDamageFromStrength());
		//Update movement speed based on agility.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		entityAttributeInstance.setBaseValue(getMovementSpeedFromAgility());
		//Update health based on vigor.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		entityAttributeInstance.setBaseValue(getHealthFromVigor());
		this.setHealth(this.getMaxHealth());
		//Update follow range based on smarts.
		entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);
		entityAttributeInstance.setBaseValue(getFollowRangeFromSmarts(this.smarts));
		//Update armor based on whether this golem is made of terracotta.
		if (this.isBaked()) {
			entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
			double armorValue = GolemancyConfig.getTerracottaArmorValue();
			entityAttributeInstance.setBaseValue(armorValue);
		}
	}
	
	public void setGolemStats(int str, int agi, int vig, int sma) {
		this.strength = str;
		this.agility = agi;
		this.vigor = vig;
		this.smarts	= sma;
		this.toComponent();
		this.updateAttributes();
	}

	public void setBaked(boolean flag) {
		this.baked = flag;
		this.toComponent();
		this.updateAttributes();
	}

	public void linkToBlockPos(BlockPos pos) {
		this.linkedBlockPos = pos;
		this.toComponent();
	}

	public Integer getGolemStrength() {
		this.fromComponent();
		return this.strength;
	}

	public Integer getGolemAgility() {
		this.fromComponent();
		return this.agility;
	}

	public Integer getGolemVigor() {
		this.fromComponent();
		return this.vigor;
	}

	public Integer getGolemSmarts() {
		this.fromComponent();
		return this.smarts;
	}
	
	public BlockPos getLinkedBlockPos() {
		this.fromComponent();
		return this.linkedBlockPos;
	}

	public boolean isBaked() {
		this.fromComponent();
		return this.baked;
	}
	
	public boolean isFollowingWand() {
		return this.golemWandFollow;
	}
	
	public void toggleFollowingWand() {
		this.golemWandFollow = !this.golemWandFollow;
	}
	
	public double getAttackDamageFromStrength() {
		int strength = this.getGolemStrength();
		switch(strength) {
			case 0:
				return 2.0D;
			case 1:
				return 3.0D;
			case 2:
				return 4.0D;
			case 3:
				return 5.0D;
			default:
				return 0.0D;
		}
	}

	public float getBlockBreakHardnessFromStrength() {
		int strength = this.getGolemStrength();
		switch (strength) {
			case 0:
				//low strength = can break dirt, wood, smooth stone
				return 2.0F;
			case 1:
				//average strength = can break ores
				return 4.0F;
			case 2:
				//high strength = can break metal blocks
				return 5.0F;
			case 3:
				//perfect strength = can break obsidian
				return 50.0F;
			default:
				return 0.0F;
		}
	}
	
	public double getMovementSpeedFromAgility() {
		int agility = this.getGolemAgility();
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
	
	public double getHealthFromVigor() {
		int vigor = this.getGolemVigor();
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
	
	public double getFollowRangeFromSmarts(int smarts) {
		switch(smarts) {
			case 0:
				return 16.0D;
			case 1:
				return 24.0D;
			case 2:
				return 32.0D;
			case 3:
				return 48.0D;
			default:
				return 0.0D;
		}
	}
	
	@Override
	public boolean tryAttack(Entity target) {
		this.attackTicksLeft = 5;
		this.world.sendEntityStatus(this, (byte)4);
		return super.tryAttack(target);
	}
	
	public boolean trySwing() {
		if (this.swingTicksLeft > 0) {
			return false;
		}
		this.swingTicksLeft = 10;
		this.world.sendEntityStatus(this, (byte)66);
		return true;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
		if (this.swingTicksLeft > 0) {
			--this.swingTicksLeft;
		}
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		switch(status) {
			case 4:
				this.attackTicksLeft = 5;
				break;
			case 66:
				this.swingTicksLeft = 10;
				break;
			default:
				super.handleStatus(status);
				break;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public int getAttackTicksLeft() {
		return this.attackTicksLeft;
	}
	
	@Environment(EnvType.CLIENT)
	public int getSwingTicksLeft() {
		return this.swingTicksLeft;
	}
}