package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Patronos;

/**
 * @author geanque
 *
 */
public interface PatronosRepository extends CrudRepository<Patronos, Integer>{
	
	Patronos findByCedula(String cedula);
	Patronos findBySerie(int serie);
	

}
