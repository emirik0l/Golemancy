package net.emirikol.amm.genetics.mutation;

import net.emirikol.amm.item.*;

import java.util.*;

//A version of the Mutation class which just checks whether the parents implement NaturalSoulstone.
//The parents must be different.

public class MutationNatural extends Mutation {
	
	public MutationNatural(Soulstone output, double chance) {
		super(output, chance);
	}
	
	@Override
	public boolean checkParents(Soulstone parent1, Soulstone parent2) {
		if ((parent1 instanceof NaturalSoulstone) && (parent2 instanceof NaturalSoulstone) && (parent1 != parent2)) {
			return true;
		}
		return false;
	}
	
}