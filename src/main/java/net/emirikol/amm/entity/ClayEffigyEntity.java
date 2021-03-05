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

public class ClayEffigyEntity extends PathAwareEntity {
	
	public ClayEffigyEntity(EntityType<? extends ClayEffigyEntity> entityType, World world) {
		super(entityType, world);
	}
   
	public static DefaultAttributeContainer.Builder createClayEffigyAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
	}
	
	@Override
	protected int getCurrentExperience(PlayerEntity player) {
		return 0;
	}
	
	@Override
	public boolean canBreatheInWater() {
		return true;
	}
	
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.world.isClient()) {
			return super.interactMob(player, hand);
		}
		return tryInsertSoulstone(player, hand);
	}
	
	private ActionResult tryInsertSoulstone(PlayerEntity player, Hand hand) {
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
			//Get entity type and replace this entity with the correct golem.
			EntityType golemType = Golems.get(typeGene.getActive());
			if (golemType == null) { return ActionResult.PASS; }
			BlockPos pos = this.getBlockPos();
			this.remove();
			AbstractGolemEntity entity = (AbstractGolemEntity) golemType.create(world, null, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
			world.spawnEntityAndPassengers(entity);
			//Update tracked values from genome.
			entity.setGolemType(typeGene.getActive());
			entity.setGolemStats(strengthGene.getActive(), agilityGene.getActive(), vigorGene.getActive(), smartsGene.getActive());
			//Remove the soulstone.
			stack.decrement(1);
			//Update golem attributes based on stats.
			entity.updateAttributes();
			//Set the golem as tamed.
			entity.setOwner(player);

			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}
}