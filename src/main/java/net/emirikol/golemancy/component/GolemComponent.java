package net.emirikol.golemancy.component;

import net.emirikol.golemancy.*;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.block.Block;
import net.minecraft.nbt.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class GolemComponent implements ComponentV3,AutoSyncedComponent {
	
	private Map<String,Integer> attributes = new HashMap<String,Integer>() {{
		put("strength", 0);
		put("agility", 0);
		put("vigor", 0);
		put("smarts", 0);
	}};
	private BlockPos linkedBlockPos = null;
	private Block linkedBlock = null;
	private Boolean baked = false;
	private String color = "";
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
	public Block getLinkedBlock() { return this.linkedBlock; }
	
	public void setLinkedBlockPos(BlockPos pos) {
		this.linkedBlockPos = pos;
		GolemancyComponents.GOLEM.sync(this.provider);
	}

	public void setLinkedBlock(Block block) {
		this.linkedBlock = block;
		GolemancyComponents.GOLEM.sync(this.provider);
	}

	public boolean isBaked() { return this.baked; }

	public void setBaked(boolean flag) {
		this.baked = flag;
		GolemancyComponents.GOLEM.sync(this.provider);
	}

	public String getColor() { return this.color; }

	public void setColor(String color) {
		this.color = color;
		GolemancyComponents.GOLEM.sync(this.provider);
	}
	
	@Override
	public void readFromNbt(NbtCompound nbt) { 
		this.attributes.put("strength", nbt.getInt("golemancy_strength"));
		this.attributes.put("agility", nbt.getInt("golemancy_agility"));
		this.attributes.put("vigor", nbt.getInt("golemancy_vigor"));
		this.attributes.put("smarts", nbt.getInt("golemancy_smarts"));
		
		int[] linkCoords = nbt.getIntArray("golemancy_linked");
		if (linkCoords.length == 3) {
			this.linkedBlockPos = new BlockPos(linkCoords[0], linkCoords[1], linkCoords[2]);
		}

		String linkIdString = nbt.getString("golemancy_linked_block");
		Identifier linkId = new Identifier(linkIdString);
		this.linkedBlock = Registry.BLOCK.get(linkId);

		this.baked = nbt.getBoolean("golemancy_baked");
		this.color = nbt.getString("golemancy_color");
	}
	
	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt("golemancy_strength", this.attributes.get("strength"));
		nbt.putInt("golemancy_agility", this.attributes.get("agility"));
		nbt.putInt("golemancy_vigor", this.attributes.get("vigor"));
		nbt.putInt("golemancy_smarts", this.attributes.get("smarts"));
		
		if (this.linkedBlockPos != null) {
			int[] linkCoords = {this.linkedBlockPos.getX(), this.linkedBlockPos.getY(), this.linkedBlockPos.getZ()};
			nbt.putIntArray("golemancy_linked", linkCoords);
		}

		if (this.linkedBlock != null) {
			Identifier linkId = Registry.BLOCK.getId(this.linkedBlock);
			String linkIdString = linkId.toString();
			nbt.putString("golemancy_linked_block", linkIdString);
		}

		nbt.putBoolean("golemancy_baked", this.baked);
		nbt.putString("golemancy_color", this.color);
	}
} 