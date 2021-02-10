package net.emirikol.amm.item;

import net.emirikol.amm.*;
import net.emirikol.amm.genetics.*;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class SoulstoneSkeleton extends Soulstone implements FilledSoulstone {
	
	public SoulstoneSkeleton(Settings settings) {
		super(settings);
	}

	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "SKELETON";
	}

	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return AriseMyMinionsMod.SUMMONED_SKELETON;
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(stack);
		genome.loadTags();
		genome.createGenome(GenomeAttributes.SKELETON_POTENCY, GenomeAttributes.SKELETON_DAMAGE, GenomeAttributes.SKELETON_KNOCKBACK, GenomeAttributes.SKELETON_ARMOR, GenomeAttributes.SKELETON_MOVEMENT_SPEED);
		genome.saveTags();
	}
}