package net.emirikol.amm.genetics;

import java.util.*;

public class SerializedGenome {
	public HashMap<String,String> activeAlleles;
	public HashMap<String,String> dormantAlleles;
	
	//Create a SerializedGenome from a Genome (serialization).
	public SerializedGenome(Genome genome) {
		activeAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			activeAlleles.put(key, genome.get(key).getActive().toString());
		}
		dormantAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			dormantAlleles.put(key, genome.get(key).getDormant().toString());
		}
	}
	
	//Create a SerializedGenome from a String (deserialization).
	public SerializedGenome(String genomeString) {
		String entries[] = genomeString.split("\\|");
		if (entries.length != 2) {
			return;
		}
		
		activeAlleles = new HashMap<String,String>();
		for (String part: entries[0].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				activeAlleles.put(keyval[0], keyval[1]);
			}
		}
		dormantAlleles = new HashMap<String,String>();
		for (String part: entries[1].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				dormantAlleles.put(keyval[0], keyval[1]);
			}
		}
	}
	
	//Generated a serialized string from the SerializedGenome.
	public String toString() {
		String entries[] = {"", ""};
		
		int i = 0;
		String active[] = new String[this.activeAlleles.size()];
		for (String key: this.activeAlleles.keySet()) {
			active[i] = key + "," + this.activeAlleles.get(key);
			i++;
		}
		entries[0] = String.join(";", active);
		
		i = 0;
		String dormant[] =  new String[this.dormantAlleles.size()];
		for (String key: this.dormantAlleles.keySet()) {
			dormant[i] = key + "," + this.dormantAlleles.get(key);
			i++;
		}
		entries[1] = String.join(";", dormant);
		
		return String.join("|", entries);
	}
}