package net.emirikol.amm.genetics;

import java.util.*;

public class Gene<T> {
	private T dominant;
	private T recessive;
	
	public Gene(T dominant, T recessive) {
		this.dominant = dominant;
		this.recessive = recessive;
	}
	
	public Gene(T value) {
		this.dominant = value;
		this.recessive = value;
	}
	
	public T getDom() {
		return dominant;
	}
	
	public T getRec() {
		return recessive;
	}
	
	public T getRandom() {
		Random rand = new Random();
		Boolean bool = rand.nextBoolean();
		if (bool) { return dominant; } else { return recessive; }
	}
	
	public void setDom(T value) {
		dominant = value;
	}
	
	public void setRec(T value) {
		recessive = value;
	}
	
	public void setBoth(T value) {
		dominant = value;
		recessive = value;
	}
	
	public Gene<T> breed(Gene<T> gene) {
		Random rand = new Random();
		T dom,rec;
		if (rand.nextBoolean()) { dom = this.getRandom(); } else { dom = gene.getRandom(); }
		if (rand.nextBoolean()) { rec = this.getRandom(); } else { rec = gene.getRandom(); }
		return new Gene<T>(dom, rec);
	}
}