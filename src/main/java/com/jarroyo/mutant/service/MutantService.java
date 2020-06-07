package com.jarroyo.mutant.service;


import com.jarroyo.mutant.model.Estadistica;
import com.jarroyo.mutant.model.Persona;

public interface MutantService {

	public abstract boolean consultarADN(Persona persona);
	public abstract Estadistica consultarEstadisticas();
}
