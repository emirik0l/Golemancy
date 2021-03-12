package net.emirikol.golemancy.component;

import net.emirikol.golemancy.*;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.AutoSyncedComponent;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;

import java.util.*;

public class GolemComponent implements ComponentV3,AutoSyncedComponent {
	
	private Map<String,Integer> attributes = new HashMap<String,Integer>() {{
		put("strength", 0);
		put("agility", 0);
		put("vigor", 0);
		put("smarts", 0);
	}};
	private BlockPos linkedBlockPos;
	private Object provider;
	
	public GolemComponent(Object provider) {
		this.provider = provider;
	}
	
	public Integer getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setAttribute(String key, int value) {
		attributes.put(key, value);
		GolemancyComponents.GOLEM.sync(this.provider);
		
	}
	
	public BlockPos getLinkedBlockPos() {
		return this.linkedBlockPos;
	}
	
	public void setLinkedBlockPos(BlockPos pos) {
		this.linkedBlockPos = pos;
		GolemancyComponents.GOLEM.sync(this.provider);
	}
	
	@Override
	public void readFromNbt(CompoundTag tag) { 
		this.attributes.put("strength", tag.getInt("golemancy_strength"));
		this.attributes.put("agility", tag.getInt("golemancy_agility"));
		this.attributes.put("vigor", tag.getInt("golemancy_vigor"));
		this.attributes.put("smarts", tag.getInt("golemancy_smarts"));
		
		int[] linkCoords = tag.getIntArray("golemancy_linked");
		if (linkCoords.length == 3) {
			this.linkedBlockPos = new BlockPos(linkCoords[0], linkCoords[1], linkCoords[2]);
		}
	}
	
	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putInt("golemancy_strength", this.attributes.get("strength"));
		tag.putInt("golemancy_agility", this.attributes.get("agility"));
		tag.putInt("golemancy_vigor", this.attributes.get("vigor"));
		tag.putInt("golemancy_smarts", this.attributes.get("smarts"));
		
		if (this.linkedBlockPos != null) {
			int[] linkCoords = {this.linkedBlockPos.getX(), this.linkedBlockPos.getY(), this.linkedBlockPos.getZ()};
			tag.putIntArray("golemancy_linked", linkCoords);
		}
	}
} 