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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.domain.Page;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */
public class NotificationsWriter implements Tasklet, StepExecutionListener {

	private static final String ENVIADA = "ENVIADA";

	private final Logger logger = LoggerFactory.getLogger(NotificationsWriter.class);

	private Page<Notificaciones> notificaciones;
	private NotificacionesRepository notificacionesRepo;
	
	public NotificationsWriter(NotificacionesRepository notificacionesRepository) {
		super();
		this.notificacionesRepo = notificacionesRepository;
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		notificaciones.forEach(item -> {
			item.setEstatus(ENVIADA);
			notificacionesRepo.save(item);
			//TODO: Pasar al histórico
			System.out.println("NOTIFICACION ENVIADA A PATRONO -> " + item.getPrimaryKey().getPatrono().getNombre());
		});
		
        return RepeatStatus.FINISHED;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution
		          .getJobExecution()
		          .getExecutionContext();
		this.notificaciones = (Page<Notificaciones>) executionContext.get("notificaciones");
		logger.debug("Lines Writer initialized.");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		logger.debug("Lines Writer ended.");
        return ExitStatus.COMPLETED;
	}

}
