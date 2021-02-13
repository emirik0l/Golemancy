package net.emirikol.amm.genetics;

import net.emirikol.amm.item.*;

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
		ItemStack child = new ItemStack(childSoulstone);
		//Apply the genome to the new itemstack, and save tags.
		newGenome.applyStack(child);
		return child;
	}
}