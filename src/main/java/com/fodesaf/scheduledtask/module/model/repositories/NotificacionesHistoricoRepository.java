/**
 * 
 */
package com.fodesaf.scheduledtask.module.model.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fodesaf.scheduledtask.module.model.NotificacionesHistorico;
import com.fodesaf.scheduledtask.module.model.NotificacionesHistoricoPK;

/**
 * @author geanque
 *
 */
public interface NotificacionesHistoricoRepository extends CrudRepository<NotificacionesHistorico, Integer> {
	
	NotificacionesHistorico findByPrimaryKey(NotificacionesHistoricoPK pk);

}
