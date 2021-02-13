package net.emirikol.amm.genetics;

import java.util.*;

public class Gene<T> {
	private T active;
	private T dormant;
	
	public Gene(T active, T dormant) {
		this.active = active;
		this.dormant = dormant;
	}
	
	public Gene(T value) {
		this.active = value;
		this.dormant = value;
	}
	
	public T getActive() {
		return active;
	}
	
	public T getDormant() {
		return dormant;
	}
	
	public T getRandom() {
		Random rand = new Random();
		Boolean bool = rand.nextBoolean();
		if (bool) { return active; } else { return dormant; }
	}
	
	public void setDom(T value) {
		active = value;
	}
	
	public void setRec(T value) {
		dormant = value;
	}
	
	public void setBoth(T value) {
		active = value;
		dormant = value;
	}
	
	public Gene<T> breed(Gene<T> gene) {
		Random rand = new Random();
		T a,d;
		if (rand.nextBoolean()) { a = this.getRandom(); } else { a = gene.getRandom(); }
		if (rand.nextBoolean()) { d = this.getRandom(); } else { d = gene.getRandom(); }
		return new Gene<T>(a, d);
	}
}