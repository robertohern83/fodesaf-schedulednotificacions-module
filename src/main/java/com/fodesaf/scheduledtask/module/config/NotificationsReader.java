/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */
public class NotificationsReader implements Tasklet, StepExecutionListener {

	private static final String EN_PROCESO = "En Proceso";

	private static final int MAXIMO_POR_BLOQUE = 10;

	private static final String FECHA_CREACION = "FechaCreacion";

	private static final String PENDING_STATUS = "Pendiente";

	private final Logger logger = LoggerFactory.getLogger(NotificationsReader.class);

	private Page<Notificaciones> notificaciones;
	private NotificacionesRepository notificacionesRepo;
	
	public NotificationsReader(NotificacionesRepository notificacionesRepository) {
		super();
		this.notificacionesRepo = notificacionesRepository;
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		Pageable pageable = PageRequest.of(0, MAXIMO_POR_BLOQUE, Sort.by(Sort.Direction.ASC, FECHA_CREACION));
		//notificaciones = notificacionesRepo.findByEstatus(PENDING_STATUS, pageable);
		
		notificaciones = notificacionesRepo.findByEstatusAndPrimaryKeyCampanaEstado(PENDING_STATUS, EN_PROCESO, pageable);
	
        return RepeatStatus.FINISHED;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		//notificaciones = (Page<Notificaciones>) new ArrayList<Notificaciones>();
        logger.debug("Iniciando lectura de patronos a notificar...");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
          .getJobExecution()
          .getExecutionContext()
          .put("notificaciones", this.notificaciones);
        
        notificaciones.forEach(item -> {
			item.setEstatus(EN_PROCESO);
			notificacionesRepo.save(item);
		});
        
        logger.debug("Finalizada lectura de patronos a notificar");
        return ExitStatus.COMPLETED;
	}

}
