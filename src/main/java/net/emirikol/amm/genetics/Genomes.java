package net.emirikol.amm.genetics;

public class Genomes {
	public static final Genome ZOMBIE = new Genome() {{
		put("type", new Gene<String>("NONE"));
		put("strength", new Gene<Integer>(1));
		put("agility", new Gene<Integer>(0));
		put("vigor", new Gene<Integer>(1));
		put("smarts", new Gene<Integer>(0));
	}};
}