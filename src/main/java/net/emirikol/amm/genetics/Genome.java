package net.emirikol.amm.genetics;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

import java.util.*;

public class Genome {
	private Map<String,Gene> genes;
	
	public Genome() {
		genes = new HashMap<String,Gene>();
		genes.put("type", null);
		genes.put("strength", null);
		genes.put("agility", null);
		genes.put("vigor", null);
		genes.put("smarts", null);
	}
	
	public Genome(ItemStack stack) {
		this();
		this.fromItemStack(stack);
	};
	
	public boolean initialised() {
		if (this.get("type") != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Gene get(String key) {
		return genes.get(key);
	}
	
	public void put(String key, Gene gene) {
		genes.put(key, gene);
	}
	
	//Apply this genome to an ItemStack's NBT data.
	public void toItemStack(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		Gene<String> type = genes.get("type");
		tag.putString("type_active", type.getActive());
		tag.putString("type_dormant", type.getDormant());
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
	}
	
	//Apply an ItemStack's NBT data to this genome.
	public void fromItemStack(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		genes.put("type", new Gene<String>(tag.getString("type_active"), tag.getString("type_dormant")));
		genes.put("strength", new Gene<Integer>(tag.getInt("strength_active"), tag.getInt("strength_dormant")));
		genes.put("agility", new Gene<Integer>(tag.getInt("agility_active"), tag.getInt("agility_dormant")));
		genes.put("vigor", new Gene<Integer>(tag.getInt("vigor_active"), tag.getInt("vigor_dormant")));
		genes.put("smarts", new Gene<Integer>(tag.getInt("smarts_active"), tag.getInt("smarts_dormant")));
	}
}