package com.jarroyo.mutant.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jarroyo.mutant.entity.Persona;

@Repository("personaRepository")
public interface PersonaJpaRepository extends JpaRepository<Persona, Serializable> {
	
	@Query(value="SELECT * FROM persona u WHERE u.cadena = :cadena",nativeQuery = true)
	public abstract List<Persona> findByADN(@Param("cadena") String cadena);
	
	@Query(value="SELECT COUNT(*) FROM persona u where u.tipo = true",nativeQuery = true)
	public abstract int countMuntant();
	
	@Query(value="SELECT COUNT(*) FROM persona u where u.tipo = false",nativeQuery = true)
	public abstract int countPerson();
}
