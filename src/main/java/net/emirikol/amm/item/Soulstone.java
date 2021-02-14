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
	
	//Initialises NBT data of a soulstone with defaults for that species.
	//Override for functionality.
	public void defaultGenes(ItemStack stack) {
	}
}