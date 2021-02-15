package net.emirikol.amm.genetics.mutation;

import net.emirikol.amm.item.*;

import java.util.*;

public class Mutation {
	private Soulstone output;
	private double chance;
	
	private List<Soulstone> validParentsA;
	private List<Soulstone> validParentsB;

	public Mutation(Soulstone output, double chance) {
		this.output = output;
		this.chance = chance;
		this.validParentsA = new ArrayList<Soulstone>();
		this.validParentsB = new ArrayList<Soulstone>();
	}
	
	public Soulstone getSoulstone() {
		return this.output;
	}
	
	public void setSoulstone(Soulstone output) {
		this.output = output;
	}
	
	public double getChance() {
		return this.chance;
	}
	
	public void setChance(double chance) {
		this.chance = chance;
	}
	
	//Add a pair of soulstones as valid parents for this mutation.
	public void addParents(Soulstone parent1, Soulstone parent2) {
		this.validParentsA.add(parent1);
		this.validParentsB.add(parent2);
	}
	
	//Check if a pair of soulstones are valid parents for this mutation.
	public boolean checkParents(Soulstone parent1, Soulstone parent2) {
		if (validParentsA.contains(parent1) && validParentsB.contains(parent2)) { return true; }
		if (validParentsA.contains(parent2) && validParentsB.contains(parent1)) { return true; }
		return false;
	}
	
	//Attempt to mutate two soulstones using this mutation.
	//Returns null on failure, or the resulting Soulstone on success.
	public Soulstone attemptMutation(Soulstone parent1, Soulstone parent2) {
		if (!checkParents(parent1, parent2)) { return null; }
		Double roll = Math.random();
		if (roll <= this.chance) {
			return this.output;
		} else {
			return null;
		}
	}
	
}