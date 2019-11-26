/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Campanas;

/**
 * @author geanque
 *
 */
public interface CampanasRepository extends CrudRepository<Campanas, Integer> {
	
	Campanas findById(int id);

}
