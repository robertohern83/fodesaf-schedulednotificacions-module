package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Patronos;

/**
 * @author geanque
 *
 */
public interface PatronosRepository extends CrudRepository<Patronos, Integer>{
	
	List<Patronos> findByCedula(String cedula);
	Patronos findBySegregado(String segregado);
	

}
