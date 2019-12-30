package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Consecutivos;

/**
 * @author geanque
 *
 */
public interface ConsecutivosRepository extends CrudRepository<Consecutivos, Integer>{
	Consecutivos findById(int id);
}
