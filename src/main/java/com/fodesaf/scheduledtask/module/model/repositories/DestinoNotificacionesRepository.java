/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.DestinoNotificaciones;
import com.fodesaf.scheduledtask.module.model.DestinoNotificacionesPK;

/**
 * @author geanque
 *
 */
public interface DestinoNotificacionesRepository extends CrudRepository<DestinoNotificaciones, Integer>, JpaSpecificationExecutor<DestinoNotificaciones> {
	
	DestinoNotificaciones findByPrimaryKey(DestinoNotificacionesPK pk);
	
	//Page<Notificaciones> findByEstatus(String estatus, Pageable pageable);
	
	//Page<Notificaciones> findByEstatusAndPrimaryKeyCampanaEstado(String estatus, String estado, Pageable pageable);
	
	//Page<Notificaciones> findAll();
	
	

}
