package net.emirikol.amm.genetics;

import java.util.*;

public class Genome {
	private Map<String,Gene> genes;
	
	public Genome() {
		genes = new HashMap<String,Gene>();
	}
	
	public Gene get(String key) {
		return genes.get(key);
	}
	
	public void put(String key, Gene gene) {
		genes.put(key, gene);
	}
}