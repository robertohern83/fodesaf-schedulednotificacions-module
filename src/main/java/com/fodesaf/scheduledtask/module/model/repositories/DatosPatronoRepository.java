/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.DatosPatrono;

/**
 * @author geanque
 *
 */
public interface DatosPatronoRepository  extends CrudRepository<DatosPatrono, Integer>{
	
	DatosPatrono findByCedula(String cedula);

}
