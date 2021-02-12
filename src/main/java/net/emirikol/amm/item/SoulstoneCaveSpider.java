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

public class SoulstoneCaveSpider extends Soulstone implements FilledSoulstone,NaturalSoulstone {
	
	public SoulstoneCaveSpider(Settings settings) {
		super(settings);
	}

	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "CAVE SPIDER";
	}

	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return AriseMyMinionsMod.SUMMONED_CAVE_SPIDER;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		ItemStack stack = playerEntity.getStackInHand(hand);
		playerEntity.playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 0.5F, 1.0F);
		return TypedActionResult.pass(stack);
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(getSoulName(), GenomeAttributes.CAVE_SPIDER_POTENCY, GenomeAttributes.CAVE_SPIDER_DAMAGE, GenomeAttributes.CAVE_SPIDER_KNOCKBACK, GenomeAttributes.CAVE_SPIDER_ARMOR, GenomeAttributes.CAVE_SPIDER_MOVEMENT_SPEED);
		genome.applyStack(stack);
	}
}