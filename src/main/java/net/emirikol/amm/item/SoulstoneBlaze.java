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

public class SoulstoneBlaze extends Soulstone implements FilledSoulstone,NaturalSoulstone {
	
	public SoulstoneBlaze(Settings settings) {
		super(settings);
	}

	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "BLAZE";
	}

	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return EntityType.BLAZE;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);
		playerEntity.playSound(SoundEvents.ENTITY_BLAZE_AMBIENT, 0.5F, 1.0F);
		return TypedActionResult.pass(stack);
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(getSoulName(), GenomeAttributes.BLAZE_POTENCY, GenomeAttributes.BLAZE_DAMAGE, GenomeAttributes.BLAZE_KNOCKBACK, GenomeAttributes.BLAZE_ARMOR, GenomeAttributes.BLAZE_MOVEMENT_SPEED);
		genome.applyStack(stack);
	}
}