/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.DetallePatronos;
import com.fodesaf.scheduledtask.module.model.DetallePatronosPK;

/**
 * @author geanque
 *
 */
public interface DetallePatronosRepository extends CrudRepository<DetallePatronos, Integer>, JpaSpecificationExecutor<DetallePatronos> {
	
	DetallePatronos findByPrimaryKey(DetallePatronosPK primaryKey);
	
	
	//List<DetallePatronos> findAll(Example<DetallePatronos> partialPK);
	List<DetallePatronos> findByPrimaryKeyCedula(String cedula);

	@Query ("SELECT DISTINCT(TRIM(both from dp.estado)) FROM DetallePatronos dp")
	List<String> getEstados();
	
	List<DetallePatronos> findAll(); 
	
	

}
