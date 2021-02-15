package net.emirikol.amm.genetics.mutation;

import net.emirikol.amm.*;

import java.util.*;

public class Mutations {
	/*
	public static final Mutation TEST = new Mutation(AriseMyMinionsMod.SOULSTONE_ENDERMAN, 0.5) {{
		addParents(AriseMyMinionsMod.SOULSTONE_ZOMBIE, AriseMyMinionsMod.SOULSTONE_SKELETON);
	}};
	*/
	
	public static final MutationNatural CLOUDY = new MutationNatural(AriseMyMinionsMod.SOULSTONE_CLOUDY, 0.1);
	
	public static final List<Mutation> MUTATIONS = new ArrayList<Mutation>() {{
		add(CLOUDY);
	}};
}