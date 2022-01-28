package net.emirikol.golemancy.genetics;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class Genome {
	private final Map<String,Gene> genes;
	
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
	}
	
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
	public boolean validNbt(NbtCompound nbt) {
		String[] keys = {"type_active", "type_dormant", "potency_active", "potency_dormant", "strength_active", "strength_dormant", "agility_active", "agility_dormant", "vigor_active", "vigor_dormant", "smarts_active", "smarts_dormant"};
		for (String key : keys) {
			if (!nbt.contains(key)) {
				return false;
			}
		}
		// Check if the type gene is valid.
		if (SoulTypes.get(nbt.getString("type_active")) == null) return false;
		if (SoulTypes.get(nbt.getString("type_dormant")) == null) return false;
		return true;
	}
	
	//Apply this genome to an ItemStack's NBT data.
	public void toItemStack(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		Gene<SoulType> type = genes.get("type");
		nbt.putString("type_active", type.getActive().getTypeString());
		nbt.putString("type_dormant", type.getDormant().getTypeString());
		Gene<Integer> potency = genes.get("potency");
		nbt.putInt("potency_active", potency.getActive());
		nbt.putInt("potency_dormant", potency.getDormant());
		Gene<Integer> strength = genes.get("strength");
		nbt.putInt("strength_active", strength.getActive());
		nbt.putInt("strength_dormant", strength.getDormant());
		Gene<Integer> agility = genes.get("agility");
		nbt.putInt("agility_active", agility.getActive());
		nbt.putInt("agility_dormant", agility.getDormant());
		Gene<Integer> vigor = genes.get("vigor");
		nbt.putInt("vigor_active", vigor.getActive());
		nbt.putInt("vigor_dormant", vigor.getDormant());
		Gene<Integer> smarts = genes.get("smarts");
		nbt.putInt("smarts_active", smarts.getActive());
		nbt.putInt("smarts_dormant", smarts.getDormant());
		
		Integer textureId = Genomes.TEXTURE_VARIANTS.get(type.getActive());
		if (textureId != null) {
			nbt.putInt("CustomModelData", textureId);
		}
	}
	
	//Apply an ItemStack's NBT data to this genome.
	public void fromItemStack(ItemStack stack) {
		NbtCompound nbt = stack.getOrCreateNbt();
		if (validNbt(nbt)) {
			genes.put("type", new Gene<SoulType>(SoulTypes.get(nbt.getString("type_active")), SoulTypes.get(nbt.getString("type_dormant"))));
			genes.put("potency", new Gene<Integer>(nbt.getInt("potency_active"), nbt.getInt("potency_dormant")));
			genes.put("strength", new Gene<Integer>(nbt.getInt("strength_active"), nbt.getInt("strength_dormant")));
			genes.put("agility", new Gene<Integer>(nbt.getInt("agility_active"), nbt.getInt("agility_dormant")));
			genes.put("vigor", new Gene<Integer>(nbt.getInt("vigor_active"), nbt.getInt("vigor_dormant")));
			genes.put("smarts", new Gene<Integer>(nbt.getInt("smarts_active"), nbt.getInt("smarts_dormant")));
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
		Genome newGenome = new Genome();
		Genome[] genomes = {left, right};
		//Generate new genes.
		for (String key : newGenome.getKeys()) {
			Gene leftGene = genomes[0].get(key);
			Gene rightGene = genomes[1].get(key);
			Gene newGene = leftGene.breed(rightGene);
			newGenome.put(key, newGene);
		}
		//Check for mutations.
		for (Mutation mutation: Mutations.MUTATIONS) {
			newGenome = mutation.applyMutation(newGenome);
		}
		return newGenome;
	}
}