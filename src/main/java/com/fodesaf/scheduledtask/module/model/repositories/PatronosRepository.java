package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fodesaf.scheduledtask.module.model.Patronos;

/**
 * @author geanque
 *
 */

@RepositoryRestResource(collectionResourceRel = "Patronos", path = "patronos")
public interface PatronosRepository extends CrudRepository<Patronos, Integer>{
	
	List<Patronos> findByCedula(String cedula);
	
	Patronos findBySegregado(String segregado);
	
	@Query("SELECT DISTINCT(TRIM(both from p.regimen)) FROM Patronos p")
	List<String> getRegimen();
	
	@Query("SELECT DISTINCT(TRIM(both from p.alerta)) FROM Patronos p")
	List<String> getAlertas();
	
	
	@Override
    @RestResource(exported = true)
    <S extends Patronos> S save(S s);
	

}
