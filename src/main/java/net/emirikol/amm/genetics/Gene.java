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
	
	public T getDominant() {
		return dominant;
	}
	
	public T getRecessive() {
		return recessive;
	}
	
	public T getRandom() {
		Random rand = new Random();
		Boolean bool = rand.nextBoolean();
		if (bool) { return dominant; } else { return recessive; }
	}
	
	public void setDominant(T value) {
		dominant = value;
	}
	
	public void setRecessive(T value) {
		recessive = value;
	}
	
	public void setBoth(T value) {
		dominant = value;
		recessive = value;
	}
}