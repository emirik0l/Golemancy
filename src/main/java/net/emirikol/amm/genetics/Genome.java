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
		setItemStack(stack);
		this.dominant = new Genepool();
		this.recessive = new Genepool();
		loadTags();
	}
	
	public void setItemStack(ItemStack stack) {
		this.soulstone = stack;
		this.name = ((Soulstone) stack.getItem()).getSoulName();
		this.tag = soulstone.getOrCreateTag();
	}
	
	public String getName() {
		return this.name;
	}
	
	//Initialises the Genome with identical dominant and recessive genepools based on the supplied attributes.
	//Called when you receive a fresh soulstone from killing a mob or via mutation.
	public void createGenome(String species, double potency, double damage, double knockback, double armor, double movement_speed) {
		this.dominant = new Genepool(species, potency, damage, knockback, armor, movement_speed);
		this.recessive = new Genepool(species, potency, damage, knockback, armor, movement_speed);
	}
	
	//Load NBT data from the ItemStack into the Genome.
	public void loadTags() {
		this.dominant.putString("species", tag.getString("species_dom"));
		this.recessive.putString("species", tag.getString("species_rec"));
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
		tag.putString("species_dom", this.dominant.getString("species"));
		tag.putString("species_rec", this.recessive.getString("species"));
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
			if (livingEntity instanceof SlimeEntity) {
				//Slime attack damage is based on their size.
				SlimeEntity slimeEntity = (SlimeEntity) livingEntity;
				entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				entityAttributeInstance.setBaseValue(this.dominant.getDouble("damage") + (double)slimeEntity.getSize());
			} else {
				entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
				entityAttributeInstance.setBaseValue(this.dominant.getDouble("damage"));
			}
			//Set knockback.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("knockback"));
			//Set armor.
			entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
			entityAttributeInstance.setBaseValue(this.dominant.getDouble("armor"));			
			//Set movement speed.
			if (livingEntity instanceof SlimeEntity) {
				//Slime movement speed is based on their size.
				SlimeEntity slimeEntity = (SlimeEntity) livingEntity;
				entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
				entityAttributeInstance.setBaseValue(this.dominant.getDouble("movement_speed") + 0.1F * (float)slimeEntity.getSize());
			} else {
				entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
				entityAttributeInstance.setBaseValue(this.dominant.getDouble("movement_speed"));
			}
		}
	}
	
	//The Genepool keeps track of one complete set of genes, dominant or recessive.
	public class Genepool {
		public Map<String,Object> alleles = new HashMap<String,Object>() {{
			put("species", "");
			put("potency", 0.00D);
			put("damage", 0.00D);
			put("knockback", 0.00D);
			put("armor", 0.00D);
			put("movement_speed", 0.00D);
		}};
		
		public Genepool() {}
		
		public Genepool(String species, double potency, double damage, double knockback, double armor, double movement_speed) {
			this.putString("species", species);
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
		
		public void putString(String key, String value) {
			this.alleles.put(key, value);
		}
		
		public String getString(String key) {
			return String.valueOf(this.alleles.get(key));
		}
	}
}
