package net.emirikol.amm.item;

import net.emirikol.amm.*;
import net.emirikol.amm.genetics.*;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class SoulstoneZombie extends Soulstone implements FilledSoulstone {
	
	public SoulstoneZombie(Settings settings) {
		super(settings);
	}
	
	@Override
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "ZOMBIE";
	}
	
	@Override
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return AriseMyMinionsMod.SUMMONED_ZOMBIE;
	}
	
	@Override
	//Initialises NBT data of a soulstone with defaults for that species.
	public void defaultGenes(ItemStack stack) {
		super.defaultGenes(stack);
		Genome genome = new Genome(stack);
		genome.loadTags();
		genome.createGenome(GenomeAttributes.ZOMBIE_POTENCY, GenomeAttributes.ZOMBIE_DAMAGE, GenomeAttributes.ZOMBIE_KNOCKBACK, GenomeAttributes.ZOMBIE_ARMOR, GenomeAttributes.ZOMBIE_MOVEMENT_SPEED);
		genome.saveTags();
	}
}