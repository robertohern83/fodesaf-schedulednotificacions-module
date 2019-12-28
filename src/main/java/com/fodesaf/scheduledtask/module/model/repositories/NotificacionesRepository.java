/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.NotificacionesPK;

/**
 * @author geanque
 *
 */
public interface NotificacionesRepository extends CrudRepository<Notificaciones, Integer>, JpaSpecificationExecutor<Notificaciones> {
	
	Notificaciones findByPrimaryKey(NotificacionesPK pk);
	
	Page<Notificaciones> findByEstatus(String estatus, Pageable pageable);
	
	Page<Notificaciones> findByEstatusAndPrimaryKeyCampanaEstado(String estatus, String estado, Pageable pageable);
	
	Page<Notificaciones> findAll();

}
