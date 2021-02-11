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
}