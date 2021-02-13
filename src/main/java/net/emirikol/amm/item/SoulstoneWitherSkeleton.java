package net.emirikol.amm.item;

import net.emirikol.amm.*;
import net.emirikol.amm.genetics.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

public class SoulstoneWitherSkeleton extends Soulstone implements FilledSoulstone,NaturalSoulstone {
	
	public SoulstoneWitherSkeleton(Settings settings) {
		super(settings);
	}
	
	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "WITHER SKELETON";
	}
	
	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return EntityType.WITHER_SKELETON;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);
		playerEntity.playSound(SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT, 0.5F, 1.0F);
		return TypedActionResult.pass(stack);
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(getSoulName(), GenomeAttributes.WITHER_SKELETON_POTENCY, GenomeAttributes.WITHER_SKELETON_DAMAGE, GenomeAttributes.WITHER_SKELETON_KNOCKBACK, GenomeAttributes.WITHER_SKELETON_ARMOR, GenomeAttributes.WITHER_SKELETON_MOVEMENT_SPEED);
		genome.applyStack(stack);
	}
}