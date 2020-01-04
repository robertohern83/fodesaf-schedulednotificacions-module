/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.ControlNotificacionesDiarias;
import com.fodesaf.scheduledtask.module.model.ControlNotificacionesDiariasPK;

/**
 * @author geanque
 *
 */

public interface ControlNotificacionesDiariasRepository extends CrudRepository<ControlNotificacionesDiarias, ControlNotificacionesDiariasPK> {
	
	ControlNotificacionesDiarias findByPrimaryKey(ControlNotificacionesDiariasPK pk);
	
	List<ControlNotificacionesDiarias> findByPrimaryKeyFechaControl(LocalDate fecha);
	
}
