package net.emirikol.golemancy.genetics;

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
	
	public List<T> toList() {
		return new ArrayList<T>() {{
			add(active);
			add(dormant);
		}};
	}
	
	public T getActive() { return active; }
	public T getDormant() {
		return dormant;
	}
	
	public T getRandom() {
		Random rand = new Random();
		boolean bool = rand.nextBoolean();
		if (bool) { return active; } else { return dormant; }
	}
	
	public void setActive(T value) {
		active = value;
	}
	public void setDormant(T value) {
		dormant = value;
	}
	
	public void setBoth(T value) {
		active = value;
		dormant = value;
	}
	
	public Gene<T> breed(Gene<T> otherGene) {
		Random rand = new Random();
		T a,d;
		if (rand.nextBoolean()) { a = this.getRandom(); } else { a = otherGene.getRandom(); }
		if (rand.nextBoolean()) { d = this.getRandom(); } else { d = otherGene.getRandom(); }
		return new Gene<>(a, d);
	}
}