package net.emirikol.amm.item;

import net.emirikol.amm.genetics.*;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class Soulstone extends Item {
	
	public Soulstone(Settings settings) {
		super(settings);
	}
	
	//Return a name for the soul contained in this soulstone.
	public String getSoulName() {
		return "EMPTY";
	}
	
	//Return the EntityType that should be spawned from this soulstone.
	public EntityType getEntityType() {
		return null;
	}
	
	//Check if this soulstone contains a soul or not.
	public boolean filled() {
		if (this.getEntityType() == null) { return false; }
		return true;
	}

	//Check if this soulstone has been initialised with genes yet.
	//If false, you should probably call defaultGenes() before using it.
	public boolean initialised(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		return tag.getBoolean("initialised");
	}

	//Initialises NBT data of a soulstone with defaults for that species.
	//Override for functionality.
	public void defaultGenes(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("initialised", true);
	}

	//Loads the genome from a soulstone.
	//If the soulstone is empty, it returns null.
	//If the soulstone has not been initialised, it calls defaultGenes() first.
	public Genome getGenome(ItemStack stack) {
		if (!this.filled()) {
			return null;
		}
		if (!this.initialised(stack)) {
			this.defaultGenes(stack);
		}
		return new Genome(stack);
	}
	
	//Create a new genome for a soulstone by breeding two parents.
	//Uses mendelian inheritance to randomly assign genes from the parents to the child.
	public static ItemStack breed(ItemStack parent1, ItemStack parent2) {
		//Randomly choose which parent to use for the species.
		Random rand = new Random();
		int x = rand.nextInt(2);
		ItemStack parents[] = {parent1, parent2};
		Soulstone soulstone = (Soulstone) parents[x].getItem();
		ItemStack stack = new ItemStack(soulstone);
		//Extract genomes from parents and create a new genome for this stack.
		Genome genome1 = ((Soulstone) parent1.getItem()).getGenome(parent1);
		Genome genome2 = ((Soulstone) parent2.getItem()).getGenome(parent2);
		Genome newGenome = soulstone.getGenome(stack);
		Genome.Genepool[] options = {genome1.dominant, genome1.recessive, genome2.dominant, genome2.recessive};
		//Generate dominant genepool.
		for (String key : newGenome.dominant.alleles.keySet()) {
			//Randomly select a genepool to use.
			int choice = rand.nextInt(4);
			Genome.Genepool pool = options[choice];
			//Apply the selected gene.
			newGenome.dominant.alleles.put(key, pool.alleles.get(key));
		}
		//Generate recessive genepool.
		for (String key : newGenome.recessive.alleles.keySet()) {
			//Randomly select a genepool to use.
			int choice = rand.nextInt(4);
			Genome.Genepool pool = options[choice];
			//Apply the selected gene.
			newGenome.recessive.alleles.put(key, pool.alleles.get(key));
		}
		newGenome.saveTags();
		return stack;
	}
}