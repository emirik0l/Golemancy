package net.emirikol.amm.genetics;

import net.emirikol.amm.item.*;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.mob.*;

import java.util.*;

public class Genome {
	private ItemStack soulstone;
	private String name;
	private CompoundTag tag;
	
	public Genepool dominant;
	public Genepool recessive;
	
	//Constructor; initialises genome and loads tags from an itemstack.
	public Genome(ItemStack stack) {
		this.soulstone = stack;
		this.name = ((Soulstone) stack.getItem()).getSoulName();
		this.tag = soulstone.getOrCreateTag();
		this.dominant = new Genepool();
		this.recessive = new Genepool();
		loadTags();
	}
	
	public String getName() {
		return this.name;
	}
	
	//Initialises the Genome with identical dominant and recessive genepools based on the supplied attributes.
	//Called when you receive a fresh soulstone from killing a mob or via mutation.
	public void createGenome(double potency, double damage, double knockback, double armor, double movement_speed) {
		this.dominant = new Genepool(potency, damage, knockback, armor, movement_speed);
		this.recessive = new Genepool(potency, damage, knockback, armor, movement_speed);
	}
	
	//Load NBT data from the ItemStack into the Genome.
	public void loadTags() {
		this.dominant.putDouble("potency", tag.getDouble("potency_dom"));
		this.recessive.putDouble("potency", tag.getDouble("potency_rec"));
		this.dominant.putDouble("damage", tag.getDouble("damage_dom"));
		this.recessive.putDouble("damage", tag.getDouble("damage_rec"));
		this.dominant.putDouble("knockback", tag.getDouble("knockback_dom"));
		this.recessive.putDouble("knockback", tag.getDouble("knockback_rec"));
		this.dominant.putDouble("armor", tag.getDouble("armor_dom"));
		this.recessive.putDouble("armor", tag.getDouble("armor_rec"));
		this.dominant.putDouble("movement_speed", tag.getDouble("movement_speed_dom"));
		this.recessive.putDouble("movement_speed", tag.getDouble("movement_speed_rec"));
	}
	
	//Save NBT data from the Genome into the Itemstack.
	public void saveTags() {
		tag.putDouble("potency_dom", this.dominant.getDouble("potency"));
		tag.putDouble("potency_rec", this.recessive.getDouble("potency"));
		tag.putDouble("damage_dom", this.dominant.getDouble("damage"));
		tag.putDouble("damage_rec", this.recessive.getDouble("damage"));
		tag.putDouble("knockback_dom", this.dominant.getDouble("knockback"));
		tag.putDouble("knockback_rec", this.recessive.getDouble("knockback"));
		tag.putDouble("armor_dom", this.dominant.getDouble("armor"));
		tag.putDouble("armor_rec", this.recessive.getDouble("armor"));
		tag.putDouble("movement_speed_dom", this.dominant.getDouble("movement_speed"));
		tag.putDouble("movement_speed_rec", this.recessive.getDouble("movement_speed"));
	}
	
	//Apply the dominant genepool to an entity.
	public void applyGenes(Entity entity) {
		EntityAttributeInstance entityAttributeInstance;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			//Set damage.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("damage"));
			//Set knockback.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("knockback"));
			//Set armor.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("armor"));			
			//Set movement speed.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("movement_speed"));
		}
	}
	
	//Create a new genome for a soulstone by breeding two parents.
	//Uses mendelian inheritance to randomly assign genes from the parents to the child.
	public static ItemStack breed(ItemStack parent1, ItemStack parent2) {
		//Randomly choose which parent to use for the species.
		Random rand = new Random();
		int x = rand.nextInt(2);
		ItemStack parents[] = {parent1, parent2};
		Soulstone soulstone = (Soulstone) parents[x].getItem();
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
		newGenome.saveTags();
		return stack;
	}
	
	//The Genepool keeps track of one complete set of genes, dominant or recessive.
	public class Genepool {
		public Map<String,Double> alleles = new HashMap<String,Double>() {{
			put("potency", 0.00D);
			put("damage", 0.00D);
			put("knockback", 0.00D);
			put("armor", 0.00D);
			put("movement_speed", 0.00D);
		}};
		
		public Genepool() {}
		
		public Genepool(double potency, double damage, double knockback, double armor, double movement_speed) {
			this.putDouble("potency", potency);
			this.putDouble("damage", damage);
			this.putDouble("knockback", knockback);
			this.putDouble("armor", armor);
			this.putDouble("movement_speed", movement_speed);
		}
		
		public void putDouble(String key, double value) {
			this.alleles.put(key, value);
		}
		
		public double getDouble(String key) {
			return (double) this.alleles.get(key);
		}
		
		public String getString(String key) {
			return String.valueOf(this.alleles.get(key));
		}
	}
}
