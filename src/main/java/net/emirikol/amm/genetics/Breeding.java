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
		//Attempt mutation.
		mutate(newGenome, genomes[0], genomes[1]);
		//Figure out what species the child should be and create a new itemstack.
		Gene<String> speciesGene = newGenome.getGene("species");
		Soulstone childSoulstone = Soulstones.get(speciesGene.getActive());
		ItemStack child  = new ItemStack(childSoulstone);
		newGenome.applyStack(child);
		return child;
	}
	
	public static void mutate(Genome child, Genome parent1, Genome parent2) {
		Random rand = new Random();
		boolean  alreadyMutated = false;
		//Convert species genes into soulstones and construct possible combinations.
		Soulstone combinations[][] = {
			{Soulstones.get(((Gene<String>) parent1.getGene("species")).getActive()), Soulstones.get(((Gene<String>) parent2.getGene("species")).getActive())},
			{Soulstones.get(((Gene<String>) parent1.getGene("species")).getActive()), Soulstones.get(((Gene<String>) parent2.getGene("species")).getDormant())},
			{Soulstones.get(((Gene<String>) parent1.getGene("species")).getDormant()), Soulstones.get(((Gene<String>) parent2.getGene("species")).getActive())},
			{Soulstones.get(((Gene<String>) parent1.getGene("species")).getDormant()), Soulstones.get(((Gene<String>) parent2.getGene("species")).getDormant())}
		};
		//Iterate over all mutations and combinations.
		for (Mutation mutation : Mutations.MUTATIONS) {
			for (Soulstone combination[] : combinations) {
				//Attempt to generate a mutation.
				Soulstone result = mutation.attemptMutation(combination[0], combination[1]);
				if (result != null) {
					//If successful, generate a default genome for the mutation.
					ItemStack tempStack = new ItemStack(result);
					result.defaultGenes(tempStack);
					Genome newGenome = new Genome(tempStack);
					//Choose whether to replace the child's active or dormant genepool.
					//Mutations replace dormant genes first.
					for (String key : child.getKeys()) {
						Gene childGene = child.getGene(key);
						Gene newGene = newGenome.getGene(key);
						if (alreadyMutated) {
							childGene.setActive(newGene.getActive());
						} else {
							childGene.setDormant(newGene.getDormant());
						}
						child.putGene(key, childGene);
					}
					alreadyMutated = true;
				}
			}
		}
	}
}