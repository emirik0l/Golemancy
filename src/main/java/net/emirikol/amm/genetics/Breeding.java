package net.emirikol.amm.genetics;

import net.emirikol.amm.item.*;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.mob.*;

import java.util.*;

public class Breeding {
	//Create a new genome for a soulstone by breeding two parents.
	//Uses mendelian inheritance to randomly assign genes from the parents to the child.
	public static ItemStack breed(ItemStack parent1, ItemStack parent2) {
		Random rand = new Random();
		//Create a placeholder ItemStack to hold genes until we figure out what species the child should be.
		Soulstone soulstone = (Soulstone) parent1.getItem();
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
		//Figure out what species the child should be and create a new itemstack.
		Soulstone childSoulstone = GenomeAttributes.SPECIES_SOULSTONES.get(newGenome.dominant.getString("species"));
		ItemStack child = new ItemStack(childSoulstone);
		childSoulstone.getGenome(child);
		//Update the genome to use the new itemstack, and save tags.
		newGenome.setItemStack(child);
		newGenome.saveTags();
		return child;
	}
}