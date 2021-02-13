package net.emirikol.amm.genetics;

import java.util.*;

public class SerializedGenome {
	public String name;
	public HashMap<String,String> activeAlleles;
	public HashMap<String,String> dormantAlleles;
	
	//Create a SerializedGenome from a Genome (serialization).
	public SerializedGenome(Genome genome) {
		name = genome.getName();
		activeAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			activeAlleles.put(key, genome.getGene(key).getActive().toString());
		}
		dormantAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			dormantAlleles.put(key, genome.getGene(key).getDormant().toString());
		}
	}
	
	//Create a SerializedGenome from a String (deserialization).
	public SerializedGenome(String genomeString) {
		String entries[] = genomeString.split("\\|");
		if (entries.length != 3) {
			return;
		}
		name = entries[0];
		activeAlleles = new HashMap<String,String>();
		for (String part: entries[1].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				activeAlleles.put(keyval[0], keyval[1]);
			}
		}
		dormantAlleles = new HashMap<String,String>();
		for (String part: entries[2].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				dormantAlleles.put(keyval[0], keyval[1]);
			}
		}
	}
	
	//Generated a serialized string from the SerializedGenome.
	public String toString() {
		String entries[] = {"", "", ""};
		entries[0] = this.name;
		
		int i = 0;
		String active[] = new String[this.activeAlleles.size()];
		for (String key: this.activeAlleles.keySet()) {
			active[i] = key + "," + this.activeAlleles.get(key);
			i++;
		}
		entries[1] = String.join(";", active);
		
		i = 0;
		String dormant[] =  new String[this.dormantAlleles.size()];
		for (String key: this.dormantAlleles.keySet()) {
			dormant[i] = key + "," + this.dormantAlleles.get(key);
			i++;
		}
		entries[2] = String.join(";", dormant);
		
		return String.join("|", entries);
	}
}