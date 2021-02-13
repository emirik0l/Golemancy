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

public class SoulstoneMagmaCube extends Soulstone implements FilledSoulstone,NaturalSoulstone {
	
	public SoulstoneMagmaCube(Settings settings) {
		super(settings);
	}
	
	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "MAGMA CUBE";
	}
	
	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return EntityType.MAGMA_CUBE;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);
		playerEntity.playSound(SoundEvents.ENTITY_MAGMA_CUBE_SQUISH, 0.5F, 1.0F);
		return TypedActionResult.pass(stack);
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(getSoulName(), GenomeAttributes.MAGMA_CUBE_POTENCY, GenomeAttributes.MAGMA_CUBE_DAMAGE, GenomeAttributes.MAGMA_CUBE_KNOCKBACK, GenomeAttributes.MAGMA_CUBE_ARMOR, GenomeAttributes.MAGMA_CUBE_MOVEMENT_SPEED);
		genome.applyStack(stack);
	}
}