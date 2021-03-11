package net.emirikol.amm.genetics;

import net.emirikol.amm.util.*;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class Genome {
	private Map<String,Gene> genes;
	
	public Genome() {
		genes = new HashMap<String,Gene>();
		genes.put("type", null);
		genes.put("potency", null);
		genes.put("strength", null);
		genes.put("agility", null);
		genes.put("vigor", null);
		genes.put("smarts", null);
	}
	
	public Genome(ItemStack stack) {
		this();
		this.fromItemStack(stack);
	};
	
	public Gene get(String key) {
		return genes.get(key);
	}
	
	public void put(String key, Gene gene) {
		genes.put(key, gene);
	}
	
	public Set<String> getKeys() {
		return genes.keySet();
	}
	
	//Check an NBT tag to ensure it contains a full and valid set of genes.
	public boolean validTag(CompoundTag tag) {
		String keys[] = {"type_active", "type_dormant", "potency_active", "potency_dormant", "strength_active", "strength_dormant", "agility_active", "agility_dormant", "vigor_active", "vigor_dormant", "smarts_active", "smarts_dormant"};
		for (String key : keys) {
			if (!tag.contains(key)) {
				return false;
			}
		}
		return true;
	}
	
	//Apply this genome to an ItemStack's NBT data.
	public void toItemStack(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		Gene<String> type = genes.get("type");
		tag.putString("type_active", type.getActive());
		tag.putString("type_dormant", type.getDormant());
		Gene<Integer> potency = genes.get("potency");
		tag.putInt("potency_active", potency.getActive());
		tag.putInt("potency_dormant", potency.getDormant());
		Gene<Integer> strength = genes.get("strength");
		tag.putInt("strength_active", strength.getActive());
		tag.putInt("strength_dormant", strength.getDormant());
		Gene<Integer> agility = genes.get("agility");
		tag.putInt("agility_active", agility.getActive());
		tag.putInt("agility_dormant", agility.getDormant());
		Gene<Integer> vigor = genes.get("vigor");
		tag.putInt("vigor_active", vigor.getActive());
		tag.putInt("vigor_dormant", vigor.getDormant());
		Gene<Integer> smarts = genes.get("smarts");
		tag.putInt("smarts_active", smarts.getActive());
		tag.putInt("smarts_dormant", smarts.getDormant());
		
		int variant = GolemHelper.getSoulstoneVariant(type.getActive());
		tag.putInt("CustomModelData", variant);
	}
	
	//Apply an ItemStack's NBT data to this genome.
	public void fromItemStack(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		if (validTag(tag)) {
			genes.put("type", new Gene<String>(tag.getString("type_active"), tag.getString("type_dormant")));
			genes.put("potency", new Gene<Integer>(tag.getInt("potency_active"), tag.getInt("potency_dormant")));
			genes.put("strength", new Gene<Integer>(tag.getInt("strength_active"), tag.getInt("strength_dormant")));
			genes.put("agility", new Gene<Integer>(tag.getInt("agility_active"), tag.getInt("agility_dormant")));
			genes.put("vigor", new Gene<Integer>(tag.getInt("vigor_active"), tag.getInt("vigor_dormant")));
			genes.put("smarts", new Gene<Integer>(tag.getInt("smarts_active"), tag.getInt("smarts_dormant")));
		} else {
			genes.put("type", Genomes.GENERIC.get("type"));
			genes.put("potency", Genomes.GENERIC.get("potency"));
			genes.put("strength", Genomes.GENERIC.get("strength"));
			genes.put("agility", Genomes.GENERIC.get("agility"));
			genes.put("vigor", Genomes.GENERIC.get("vigor"));
			genes.put("smarts", Genomes.GENERIC.get("smarts"));
		}
	}
	
	//Breed two genomes together.
	//Uses mendelian inheritance to randomly assign genes from the parents to the child.
	public static Genome breed(Genome left, Genome right) {
		Random rand = new Random();
		Genome newGenome = new Genome();
		Genome genomes[] = {left, right};
		//Generate new genes.
		for (String key : newGenome.getKeys()) {
			Gene leftGene = genomes[0].get(key);
			Gene rightGene = genomes[1].get(key);
			Gene newGene = leftGene.breed(rightGene);
			newGenome.put(key, newGene);
		}
		return newGenome;
	}
}