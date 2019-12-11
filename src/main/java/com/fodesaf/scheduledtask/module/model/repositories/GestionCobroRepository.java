/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.GestionCobro;

/**
 * @author geanque
 *
 */
public interface GestionCobroRepository extends CrudRepository<GestionCobro, Integer> {
	
	GestionCobro findById(int id);
	
	List<GestionCobro> findBySegregado(String segregado);
	

}
