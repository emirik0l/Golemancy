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
	
	public String getName() {
		return this.name;
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
