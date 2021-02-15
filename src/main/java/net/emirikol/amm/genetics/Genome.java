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
	//Called when you receive a fresh soulstone from killing a mob.
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
	
	//Splice this genome with another, randomly replacing one set of genes (active or dormant);
	public void splice(Genome otherGenome) {
		Random rand = new Random();
		//Decide which of our genepools to replace.
		int thisIndex = rand.nextInt(2);
		//Decide which of the other genome's genepools to use.
		int thatIndex = rand.nextInt(2);
		//Commence the splice.
		for (String key : getKeys()) {
			List thisGene = getGene(key).toList();
			List thatGene = otherGenome.getGene(key).toList();
			thisGene.set(thisIndex, thatGene.get(thatIndex));
			Gene newGene = new Gene(thisGene.get(0), thisGene.get(1));
			putGene(key, newGene);
		}
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
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		entityAttributeInstance.setBaseValue(gene.getActive());
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
		entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		entityAttributeInstance.setBaseValue(gene.getActive());
		//Special logic for slimes.
		if (entity.getType() == EntityType.SLIME) { applySlimeAttributes((SlimeEntity) entity); }
		//Special logic for magma cubes.
		if (entity.getType() == EntityType.MAGMA_CUBE) { applyMagmaCubeAttributes((MagmaCubeEntity) entity); }
	}
	
	public void applySlimeAttributes(SlimeEntity entity) {
		EntityAttributeInstance entityAttributeInstance;
		Gene<Double> gene;
		//Slime attack damage is normally equal to their size value.
		//The damage gene is added to their size (0 for slimes, 2 for magma cubes).
		gene = genes.get("damage");
		Double slimeDamage = gene.getActive() + (float) entity.getSize();
		entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		entityAttributeInstance.setBaseValue(slimeDamage);
		//Slime movement speed is normally equal to {0.2F + 0.1F * (float)size}.
		//The movement speed gene replaces the 0.2F constant.
		gene = genes.get("movement_speed");
		Double slimeMovementSpeed = gene.getActive() + 0.1F * (float) entity.getSize();
		entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		entityAttributeInstance.setBaseValue(slimeMovementSpeed);
	}
	
	public void applyMagmaCubeAttributes(MagmaCubeEntity entity) {
		EntityAttributeInstance entityAttributeInstance;
		Gene<Double> gene;
		//Magma cubes use the same logic for attack damage and movement speed, though their base attack is 2 higher.
		applySlimeAttributes((SlimeEntity) entity);
		//Magma cube armor value is normally equal to 3 times their size value.
		//The armor gene replaces the multiplier.
		gene = genes.get("armor");
		Double magmaCubeArmor = gene.getActive() * (float) entity.getSize();
		entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
		entityAttributeInstance.setBaseValue(magmaCubeArmor);
	}
}
