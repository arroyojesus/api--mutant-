package com.jarroyo.mutant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="persona")
public class Persona {


	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="cadena")	
	private String cadenaDNA;
	
	@Column(name="adn")
	private String [] dna;
	
	@Column(name="tipo")
	private boolean tipo;
	
	@Transient
	private int regMutante;

	@Transient
	private int regPersona;
	
	public int getRegPersona() {
		return regPersona;
	}

	public void setRegPersona(int regPersona) {
		this.regPersona = regPersona;
	}

	public Persona() {

	}
	
	public int getRegMutante() {
		return regMutante;
	}

	public void setRegMutante(int regMutante) {
		this.regMutante = regMutante;
	}

	public Persona(int id, String[] dna) {
		this.id = id;
		this.dna = dna;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCadenaDNA() {
		return cadenaDNA;
	}

	public void setCadenaDNA(String cadenaDNA) {
		this.cadenaDNA = cadenaDNA;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	

}
