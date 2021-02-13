package net.emirikol.amm.genetics;

import java.util.*;

public class SerializedGenome {
	public String name;
	public HashMap<String,String> dominantAlleles;
	public HashMap<String,String> recessiveAlleles;
	
	//Create a SerializedGenome from a Genome (serialization).
	public SerializedGenome(Genome genome) {
		name = genome.getName();
		dominantAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			dominantAlleles.put(key, genome.getGene(key).getDom().toString());
		}
		recessiveAlleles = new HashMap<String,String>();
		for (String key: genome.getKeys()) {
			recessiveAlleles.put(key, genome.getGene(key).getRec().toString());
		}
	}
	
	//Create a SerializedGenome from a String (deserialization).
	public SerializedGenome(String genomeString) {
		String entries[] = genomeString.split("\\|");
		if (entries.length != 3) {
			return;
		}
		name = entries[0];
		dominantAlleles = new HashMap<String,String>();
		for (String part: entries[1].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				dominantAlleles.put(keyval[0], keyval[1]);
			}
		}
		recessiveAlleles = new HashMap<String,String>();
		for (String part: entries[2].split(";")) {
			String keyval[] = part.split(",");
			if (keyval.length == 2) {
				recessiveAlleles.put(keyval[0], keyval[1]);
			}
		}
	}
	
	//Generated a serialized string from the SerializedGenome.
	public String toString() {
		String entries[] = {"", "", ""};
		entries[0] = this.name;
		
		int i = 0;
		String dom[] = new String[this.dominantAlleles.size()];
		for (String key: this.dominantAlleles.keySet()) {
			dom[i] = key + "," + this.dominantAlleles.get(key);
			i++;
		}
		entries[1] = String.join(";", dom);
		
		i = 0;
		String rec[] =  new String[this.recessiveAlleles.size()];
		for (String key: this.recessiveAlleles.keySet()) {
			rec[i] = key + "," + this.recessiveAlleles.get(key);
			i++;
		}
		entries[2] = String.join(";", rec);
		
		return String.join("|", entries);
	}
}