package net.emirikol.amm.component;

import net.emirikol.amm.*;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.AutoSyncedComponent;
import net.minecraft.nbt.*;

import java.util.*;

public class GolemComponent implements ComponentV3,AutoSyncedComponent {
	
	private String type = "";
	private Map<String,Integer> attributes = new HashMap<String,Integer>() {{
		put("strength", 0);
		put("agility", 0);
		put("vigor", 0);
		put("smarts", 0);
	}};
	private Object provider;
	
	public GolemComponent(Object provider) {
		this.provider = provider;
	}
	
	public String getType() { 
		return this.type; 
	}
	
	public void setType(String type) { 
		this.type = type; 
		AriseMyMinionsComponents.GOLEM.sync(this.provider);
	}
	
	public Integer getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setAttribute(String key, int value) {
		attributes.put(key, value);
		AriseMyMinionsComponents.GOLEM.sync(this.provider);
		
	}
	
	@Override
	public void readFromNbt(CompoundTag tag) { 
		this.type = tag.getString("amm_type");
		this.attributes.put("strength", tag.getInt("amm_strength"));
		this.attributes.put("agility", tag.getInt("amm_agility"));
		this.attributes.put("vigor", tag.getInt("amm_vigor"));
		this.attributes.put("smarts", tag.getInt("amm_smarts"));
	}
	
	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putString("amm_type", this.type);
		tag.putInt("amm_strength", this.attributes.get("strength"));
		tag.putInt("amm_agility", this.attributes.get("agility"));
		tag.putInt("amm_vigor", this.attributes.get("vigor"));
		tag.putInt("amm_smarts", this.attributes.get("smarts"));
	}
} 