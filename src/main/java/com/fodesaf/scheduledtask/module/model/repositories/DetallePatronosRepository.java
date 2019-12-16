/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.DetallePatronos;
import com.fodesaf.scheduledtask.module.model.DetallePatronosPK;

/**
 * @author geanque
 *
 */
public interface DetallePatronosRepository extends CrudRepository<DetallePatronos, Integer> {
	
	DetallePatronos findByPrimaryKey(DetallePatronosPK primaryKey);
	
	
	//List<DetallePatronos> findAll(Example<DetallePatronos> partialPK);
	List<DetallePatronos> findByPrimaryKeyCedula(String cedula);

	
	
	
	
	

}
