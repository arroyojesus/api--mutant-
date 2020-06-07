package com.jarroyo.mutant.model;

public class Persona {
	
	String [] dna;
	
	public Persona() {

	}

	public Persona(String[] dna) {
		super();
		this.dna = dna;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}
	
}
