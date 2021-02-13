package net.emirikol.amm.genetics;

import net.emirikol.amm.item.*;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.mob.*;

import java.util.*;

public class Genome {
	private Map<String,Gene> genes;
	
	//Create a blank Genome.
	//Called when creating new genomes from scratch, such as when breeding.
	public Genome() {
		genes = new HashMap<String,Gene>();
	}
	
	//Create a new Genome from an ItemStack.
	//Called when loading the genome from a soulstone.
	public Genome(ItemStack stack) {
		genes = new HashMap<String,Gene>();
		CompoundTag tag = stack.getOrCreateTag();
		//Check if the soulstone is initialised; if not, call defaultGenes().
		//Note that defaultGenes() calls Genome.applyStack(), which will set the initialised tag for us.
		boolean initialised = tag.getBoolean("initialised");
		if (!initialised) {
			Soulstone stone = (Soulstone) stack.getItem();
			stone.defaultGenes(stack);
		}
		//Load genes from NBT.
		genes.put("species", new Gene<String>(tag.getString("species_active"), tag.getString("species_dormant")));
		genes.put("potency", new Gene<Double>(tag.getDouble("potency_active"), tag.getDouble("potency_dormant")));
		genes.put("damage", new Gene<Double>(tag.getDouble("damage_active"), tag.getDouble("damage_dormant")));
		genes.put("knockback", new Gene<Double>(tag.getDouble("knockback_active"), tag.getDouble("knockback_dormant")));
		genes.put("armor", new Gene<Double>(tag.getDouble("armor_active"), tag.getDouble("armor_dormant")));
		genes.put("movement_speed", new Gene<Double>(tag.getDouble("movement_speed_active"), tag.getDouble("movement_speed_dormant")));
	}
	
	//Create a new Genome with identical active and dormant genepools based on the supplied attributes.
	//Called when you receive a fresh soulstone from killing a mob or via mutation.
	public Genome(String species, double potency, double damage, double knockback, double armor, double movement_speed) {
		genes = new HashMap<String,Gene>();
		genes.put("species", new Gene<String>(species));
		genes.put("potency", new Gene<Double>(potency));
		genes.put("damage", new Gene<Double>(damage));
		genes.put("knockback", new Gene<Double>(knockback));
		genes.put("armor", new Gene<Double>(armor));
		genes.put("movement_speed", new Gene<Double>(movement_speed));
	}
	
	//Get the species name by retrieving the active species gene from the genome.
	//This is related to Soulstone.getName() and should return compatible values.
	public String getName() {
		Gene<String> species = genes.get("species");
		return species.getActive();
	}
	
	public Gene getGene(String key) {
		return genes.get(key);
	}
	
	public void putGene(String key, Gene gene) {
		genes.put(key, gene);
	}
	
	public Set<String> getKeys() {
		return genes.keySet();
	}
	
	//Applies this genome to an ItemStack, updating its NBT data.
	public void applyStack(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		Gene<String> species = genes.get("species");
		tag.putString("species_active", species.getActive());
		tag.putString("species_dormant", species.getDormant());
		Gene<Double> potency = genes.get("potency");
		tag.putDouble("potency_active", potency.getActive());
		tag.putDouble("potency_dormant", potency.getDormant());
		Gene<Double> damage = genes.get("damage");
		tag.putDouble("damage_active", damage.getActive());
		tag.putDouble("damage_dormant", damage.getDormant());
		Gene<Double> knockback = genes.get("knockback");
		tag.putDouble("knockback_active", knockback.getActive());
		tag.putDouble("knockback_dormant", knockback.getDormant());
		Gene<Double> armor = genes.get("armor");
		tag.putDouble("armor_active", armor.getActive());
		tag.putDouble("armor_dormant", armor.getDormant());
		Gene<Double> movementSpeed = genes.get("movement_speed");
		tag.putDouble("movement_speed_active", movementSpeed.getActive());
		tag.putDouble("movement_speed_dormant", movementSpeed.getDormant());
		
		tag.putBoolean("initialised", true);
	}
	
	//Applies this genome to an Entity, updating its attributes.
	public void applyEntity(LivingEntity entity) {
		EntityAttributeInstance entityAttributeInstance;
		Gene<Double> gene;
		LivingEntity livingEntity = (LivingEntity) entity;
		//Set damage.
		gene = genes.get("damage");
		if (livingEntity instanceof SlimeEntity) {
			//Slime attack damage is based on their size.
			SlimeEntity slimeEntity = (SlimeEntity) livingEntity;
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			entityAttributeInstance.setBaseValue(gene.getActive() + (double)slimeEntity.getSize());
		} else {
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			entityAttributeInstance.setBaseValue(gene.getActive());
		}
		//Set knockback.
		gene = genes.get("knockback");
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		entityAttributeInstance.setBaseValue(gene.getActive());
		//Set armor.
		gene = genes.get("armor");
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
		entityAttributeInstance.setBaseValue(gene.getActive());
		//Set movement speed.
		gene = genes.get("movement_speed");
		if (livingEntity instanceof SlimeEntity) {
			//Slime movement speed is based on their size.
			SlimeEntity slimeEntity = (SlimeEntity) livingEntity;
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			entityAttributeInstance.setBaseValue(gene.getActive() + 0.1F * (float)slimeEntity.getSize());
		} else {
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			entityAttributeInstance.setBaseValue(gene.getActive());
		}
	}
}
