package net.emirikol.amm.genetics;

import net.emirikol.amm.item.*;
import net.emirikol.amm.genetics.mutation.*;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.mob.*;

import java.util.*;

//A collection of static methods related to breeding and mutating soulstones.
public class Breeding {
	
	//Create a new "child" soulstone by breeding two parents.
	//Uses mendelian inheritance to randomly assign genes from the parents to the child.
	public static ItemStack breed(ItemStack parent1, ItemStack parent2) {
		Random rand = new Random();
		Genome newGenome = new Genome();
		//Extract genomes from parents
		Genome genomes[] = {new Genome(parent1), new Genome(parent2)};
		//Generate new genes.
		for (String key : genomes[0].getKeys()) {
			Gene left = genomes[0].getGene(key);
			Gene right = genomes[1].getGene(key);
			Gene newGene = left.breed(right);
			newGenome.putGene(key, newGene);
		}
		//Figure out what species the child should be and create a new itemstack.
		Gene<String> speciesGene = newGenome.getGene("species");
		Soulstone childSoulstone = Soulstones.get(speciesGene.getActive());
		Soulstone mutatedSoulstone = mutate(genomes[0], genomes[1]);
		ItemStack child;
		if (mutatedSoulstone == null) {
			child = new ItemStack(childSoulstone);
			newGenome.applyStack(child);
		} else {
			child = new ItemStack(mutatedSoulstone);
			mutatedSoulstone.defaultGenes(child);
		}
		return child;
	}
	
	//Attempt to generate a mutation based on parent genomes.
	//Returns null if no mutation was generated; otherwise, returns a Soulstone for the new species that should be used.
	public static Soulstone mutate(Genome genome1, Genome genome2) {
		//Get random species to mutate from each soulstone.
		Soulstone soulstone1 = Soulstones.get(((Gene<String>) genome1.getGene("species")).getRandom());
		Soulstone soulstone2 = Soulstones.get(((Gene<String>) genome2.getGene("species")).getRandom());
		//Attempt mutation and return result.
		//If mutation failed, result will be null.
		for (Mutation mutation : Mutations.MUTATIONS) {
			Soulstone result = mutation.attemptMutation(soulstone1, soulstone2);
			if (result != null) { return result; }
		}
		return null;
	}
}