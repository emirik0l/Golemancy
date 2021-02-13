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

public class SoulstoneGuardian extends Soulstone implements FilledSoulstone,NaturalSoulstone {
	
	public SoulstoneGuardian(Settings settings) {
		super(settings);
	}
	
	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "GUARDIAN";
	}
	
	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return EntityType.GUARDIAN;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);
		playerEntity.playSound(SoundEvents.ENTITY_GUARDIAN_AMBIENT, 1.0F, 1.0F);
		return TypedActionResult.pass(stack);
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(getSoulName(), GenomeAttributes.GUARDIAN_POTENCY, GenomeAttributes.GUARDIAN_DAMAGE, GenomeAttributes.GUARDIAN_KNOCKBACK, GenomeAttributes.GUARDIAN_ARMOR, GenomeAttributes.GUARDIAN_MOVEMENT_SPEED);
		genome.applyStack(stack);
	}
}