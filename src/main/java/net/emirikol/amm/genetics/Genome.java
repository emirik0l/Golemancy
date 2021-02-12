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
		genes.put("species", new Gene<String>(tag.getString("species_dom"), tag.getString("species_rec")));
		genes.put("potency", new Gene<Double>(tag.getDouble("potency_dom"), tag.getDouble("potency_rec")));
		genes.put("damage", new Gene<Double>(tag.getDouble("damage_dom"), tag.getDouble("damage_rec")));
		genes.put("knockback", new Gene<Double>(tag.getDouble("knockback_dom"), tag.getDouble("knockback_rec")));
		genes.put("armor", new Gene<Double>(tag.getDouble("armor_dom"), tag.getDouble("armor_rec")));
		genes.put("movement_speed", new Gene<Double>(tag.getDouble("movement_speed_dom"), tag.getDouble("movement_speed_rec")));
	}
	
	//Create a new Genome with identical dominant and recessive genepools based on the supplied attributes.
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
	
	//Get the species name by retrieving the dominant species gene from the genome.
	//This is related to Soulstone.getName() and should return compatible values.
	public String getName() {
		Gene<String> species = genes.get("species");
		return species.getDom();
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
		tag.putString("species_dom", species.getDom());
		tag.putString("species_rec", species.getRec());
		Gene<Double> potency = genes.get("potency");
		tag.putDouble("potency_dom", potency.getDom());
		tag.putDouble("potency_rec", potency.getRec());
		Gene<Double> damage = genes.get("damage");
		tag.putDouble("damage_dom", damage.getDom());
		tag.putDouble("damage_rec", damage.getRec());
		Gene<Double> knockback = genes.get("knockback");
		tag.putDouble("knockback_dom", knockback.getDom());
		tag.putDouble("knockback_rec", knockback.getRec());
		Gene<Double> armor = genes.get("armor");
		tag.putDouble("armor_dom", armor.getDom());
		tag.putDouble("armor_rec", armor.getRec());
		Gene<Double> movementSpeed = genes.get("movement_speed");
		tag.putDouble("movement_speed_dom", movementSpeed.getDom());
		tag.putDouble("movement_speed_rec", movementSpeed.getRec());
		
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
			entityAttributeInstance.setBaseValue(gene.getDom() + (double)slimeEntity.getSize());
		} else {
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			entityAttributeInstance.setBaseValue(gene.getDom());
		}
		//Set knockback.
		gene = genes.get("knockback");
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
		entityAttributeInstance.setBaseValue(gene.getDom());
		//Set armor.
		gene = genes.get("armor");
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
		entityAttributeInstance.setBaseValue(gene.getDom());
		//Set movement speed.
		gene = genes.get("movement_speed");
		if (livingEntity instanceof SlimeEntity) {
			//Slime movement speed is based on their size.
			SlimeEntity slimeEntity = (SlimeEntity) livingEntity;
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			entityAttributeInstance.setBaseValue(gene.getDom() + 0.1F * (float)slimeEntity.getSize());
		} else {
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			entityAttributeInstance.setBaseValue(gene.getDom());
		}
	}
}
