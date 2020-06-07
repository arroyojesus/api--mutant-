package com.jarroyo.mutant.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jarroyo.mutant.exception.ApiRequestException;
import com.jarroyo.mutant.model.Estadistica;
import com.jarroyo.mutant.model.Persona;
import com.jarroyo.mutant.repository.PersonaJpaRepository;

@Service("mutantService")
public class MutantServiceImpl implements MutantService {

	/**
	 * @author Jesús Arroyo.
	 */

	private static final Log LOGGER = LogFactory.getLog(MutantServiceImpl.class);
	static int R, C;
	static int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
	static int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

	@Autowired
	@Qualifier("personaRepository")
	private PersonaJpaRepository personaJpaRepository;

	@Override
	public boolean consultarADN(Persona persona) {
		boolean str = isMutant(persona.getDna());

		List<com.jarroyo.mutant.entity.Persona> ListpersonaEntity = findCadenaADN(Arrays.toString(persona.getDna()));
		try {
			if (ListpersonaEntity == null || ListpersonaEntity.isEmpty()) {
				com.jarroyo.mutant.entity.Persona personaEntity = new com.jarroyo.mutant.entity.Persona();
				personaEntity.setDna(persona.getDna());
				personaEntity.setCadenaDNA(Arrays.toString(persona.getDna()));
				personaEntity.setTipo(str);
				addPersona(personaEntity);
				LOGGER.info("ADN registrado");
			} else {
				LOGGER.info("ADN registrado anteriormente");
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			LOGGER.info("No se pudo guardar el ADN consultado");
		}
		return str;
	}

	public boolean isMutant(String[] dna) {

		char[][] matrizT = sortADN(dna);
		return patternSearch(matrizT);
	}

	public char[][] sortADN(String[] dna) {

		LOGGER.info("Validando caracteres del ADN");
		ArrayList<String> listADN = new ArrayList<String>();
		R = dna.length;
		for (String c : dna) {
			char[] caracterADN = c.toCharArray();
			for (int i = 0; i < caracterADN.length; i++) {
				if (caracterADN[i] == 'A' || caracterADN[i] == 'T' || caracterADN[i] == 'C' || caracterADN[i] == 'G') {
					C = caracterADN.length;
					String s = String.valueOf(caracterADN[i]);
					listADN.add(s);
				} else {
					throw new ApiRequestException("Caracterer no valido en la cadena de ADN. " + caracterADN[i]);
				}
			}
		}
		if (listADN.size() % R != 0) {
			throw new ApiRequestException("Cadena de ADN incompleta.");
		}

		LOGGER.info("Ordenando cadena ADN");
		char[][] ADN2d = new char[R][C];
		int count = 0;

		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (count == listADN.size())
					break;
				ADN2d[i][j] = listADN.get(count).charAt(0);
				count++;
			}
		}
		// Obtengo Matriz Transpuesta
		char[][] ADNT = new char[ADN2d[0].length][ADN2d.length];

		for (int x = 0; x < ADN2d.length; x++) {
			for (int y = 0; y < ADN2d[x].length; y++) {
				ADNT[y][x] = ADN2d[x][y];
			}
		}

		// Actualizar valores C y R por la matriz transpuesta
		int auxR = R;
		int auxC = C;
		R = auxC;
		C = auxR;

		return ADNT;
	}

	public boolean patternSearch(char[][] grid) {

		// Itera para cada coordenada de la matriz nxn.
		boolean encontrado = false;
		LOGGER.info("Buscando patrón ADN mutante");
		for (int row = 0; row < R; row++) {
			for (int col = 0; col < C; col++) {
				encontrado = search2D(grid, row, col);
				if (encontrado) {
					LOGGER.info("Mutante ADN encontrado en la coordenada: '" + grid[row][col]);
					break;
				}
			}
			if (encontrado) {
				break;
			}
		}
		return encontrado;
	}

	public boolean search2D(char[][] grid, int row, int col) {

		// Secuencia de 4 caracteres iguales para que el ADN sea mutante.
		int len = 4;

		for (int dir = 0; dir < 8; dir++) {
			// Inicializa el punto para la posicion actual. x, y contienen 8 posiciones
			// posibleas para el patrón.
			int k, rd = row + x[dir], cd = col + y[dir];

			for (k = 1; k < len; k++) {
				// Verifica limites de la matriz.
				if (rd >= R || rd < 0 || cd >= C || cd < 0)
					break;

				// Si no hace match con el caracterer
				if (grid[rd][cd] != grid[row][col])
					break;

				// Mueve a la siguiente dirección.
				rd += x[dir];
				cd += y[dir];
			}
			// Si los caracteres coinciden hasta que haya un patrón de 4 consecutivos.
			if (k == len)
				return true;
		}
		return false;
	}

	public void addPersona(com.jarroyo.mutant.entity.Persona persona) {
		LOGGER.info("METHOD: 'addPersona' -- PARAMS: '" + persona + "'");
		personaJpaRepository.save(persona);
	}

	public List<com.jarroyo.mutant.entity.Persona> findCadenaADN(String cadena) {
		return personaJpaRepository.findByADN(cadena);

	}
	
	public Estadistica consultarEstadisticas(){
		com.jarroyo.mutant.entity.Persona personaEntity = new com.jarroyo.mutant.entity.Persona();
		personaEntity.setRegMutante(personaJpaRepository.countMuntant());
		personaEntity.setRegPersona(personaJpaRepository.countPerson());
		LOGGER.info("METHOD: 'Cantidad de registros mutantes: '" + personaEntity.getRegMutante() + "'");
		LOGGER.info("METHOD: 'Cantidad de registros mutantes: '" + personaEntity.getRegPersona() + "'");
		
		Estadistica est =  new Estadistica();
		if(est.getCount_human_dna()!= 0) {
			est.setCount_mutant_dna(personaEntity.getRegMutante());
			est.setCount_human_dna(personaEntity.getRegPersona());
			est.setRatio(personaEntity.getRegMutante()/personaEntity.getRegPersona());
		}
		return est;
		
	}
}
