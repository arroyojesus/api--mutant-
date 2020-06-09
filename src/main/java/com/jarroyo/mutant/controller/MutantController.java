package com.jarroyo.mutant.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jarroyo.mutant.model.Estadistica;
import com.jarroyo.mutant.model.Persona;
import com.jarroyo.mutant.service.MutantService;


@RestController
@RequestMapping("/mutant")
public class MutantController {
	
	/**
	 * @author Jesús Arroyo.
	 */

	
	private static final Log LOGGER = LogFactory.getLog(MutantController.class);

	@Autowired
	@Qualifier("mutantService")
	private MutantService mutantService;
	
	
	@PostMapping("/mutant")
	public ResponseEntity<String> consultarADN(@RequestBody Persona persona) {
		LOGGER.info("METHOD: 'consultarADN' -- PARAMS: '" + persona.getDna() + "'");
		if(mutantService.consultarADN(persona)) {
			return new ResponseEntity<>("is Mutant", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("is not Mutant", HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/stats")
	public ResponseEntity<Estadistica>  getEstadisticas(){
		LOGGER.info("METHOD: 'getEstadisticas'");
		return new ResponseEntity<>(mutantService.consultarEstadisticas(), HttpStatus.OK);
	}
	
	
}