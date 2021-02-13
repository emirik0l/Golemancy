package net.emirikol.amm.entity;

import net.emirikol.amm.*;
import net.emirikol.amm.item.*;
import net.emirikol.amm.genetics.*;
import net.emirikol.amm.component.*;

import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.server.world.*;

public class ClayEffigyEntity extends PathAwareEntity {
	public ClayEffigyEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
		this.setHealth(1.0F);
	}
	
	public static DefaultAttributeContainer.Builder createClayEffigyAttributes() {
		return ClayEffigyEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
	}
	
	@Override
	public boolean canBreatheInWater() {
		return true;
	}
	
	//Spawning mobs from soulstones.
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.world.isClient) {
			return ActionResult.PASS;
		}
		ItemStack stack = player.getStackInHand(hand);
		ServerWorld world = (ServerWorld) this.world;
		if (stack.getItem() instanceof Soulstone) {
			//Load genome from soulstone.
			Soulstone stone = (Soulstone) stack.getItem();
			Genome genome = new Genome(stack);
			//Spawn a mob from the soulstone.
			EntityType entityType = stone.getEntityType();
			if (stone.filled()) {
				//Remove the clay effigy and decrement the itemstack.
				stack.decrement(1);
				this.remove();
				//Create entity.
				BlockPos pos = this.getBlockPos();
				LivingEntity entity = (LivingEntity) entityType.create(world, null, null, null, pos, SpawnReason.SPAWN_EGG, true, true);
				//Load attributes from genome.
				genome.applyEntity(entity);
				//Mark entity as summoned.
				AriseMyMinionsComponents.SUMMONED.get(entity).setValue(true);
				//Spawn entity.
				world.spawnEntityAndPassengers(entity);
				return ActionResult.SUCCESS;
			} else {
				//Empty soulstone, pass.
				return ActionResult.PASS;
			}
		} else {
			return ActionResult.PASS;
		}
	}
}