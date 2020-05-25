/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fodesaf.scheduledtask.module.model.Notificaciones;

/**
 * @author geanque
 *
 */
public interface NotificacionesRepository extends CrudRepository<Notificaciones, Integer>, JpaSpecificationExecutor<Notificaciones> {
	
	//Notificaciones findByPrimaryKey(NotificacionesPK pk);
	
	Page<Notificaciones> findByEstatus(String estatus, Pageable pageable);
	
	//Page<Notificaciones> findByEstatusAndPrimaryKeyCampanaEstado(String estatus, String estado, Pageable pageable);
	
	Page<Notificaciones> findAll();
	
	@Modifying
	@Query("UPDATE Notificaciones n "
			+ "SET n.estatus = 'PATRONO NO EXISTE' "
			+ "WHERE n.estatus = 'PENDIENTE' "
			+ "AND n.primaryKey.patrono NOT IN (SELECT p FROM Patronos p) ")
	@Transactional(readOnly = false)
	int cleanPatronos();

}
