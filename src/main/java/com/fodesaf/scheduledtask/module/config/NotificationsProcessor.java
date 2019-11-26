/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import java.util.Date;
import java.util.List;

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

import com.fodesaf.scheduledtask.module.model.CampanaCanales;
import com.fodesaf.scheduledtask.module.model.Campanas;
import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.CampanaCanalesRepository;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */
public class NotificationsProcessor implements Tasklet, StepExecutionListener {

	private static final String VOZ = "VOZ";

	private static final String EMAIL = "EMAIL";

	private static final String SMS = "SMS";

	private Logger logger = LoggerFactory.getLogger(NotificationsProcessor.class);

	private Page<Notificaciones> notificaciones;
	
	private NotificacionesRepository notificacionesRepo;
	private CampanaCanalesRepository campanaCanalesRepo;
	
	
	
	public NotificationsProcessor(NotificacionesRepository notificacionesRepository, CampanaCanalesRepository campanaCanalesRepository) {
		super();
		this.notificacionesRepo = notificacionesRepository;
		this.campanaCanalesRepo = campanaCanalesRepository;
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		notificaciones.forEach(item -> {
			notificar(item);
			System.out.println("NOTIFICAR A PATRONO -> " + item.getPrimaryKey().getPatrono().getNombre());
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
		logger.debug("Lines Processor initialized.");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		notificaciones.forEach(item -> {
			item.setFechaEnvio(new Date());
			notificacionesRepo.save(item);
		});
		logger.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
	}
	
	private void notificar(Notificaciones item) {
		
		int idCampana = item.getPrimaryKey().getCampana().getId();
		
		Campanas campana = new Campanas();
		campana.setId(idCampana);
		
		List<CampanaCanales> canales = campanaCanalesRepo.findByPrimaryKeyCampana(campana);
		
		canales.forEach(canal -> {
			notificarCanal(item, canal);
		});
		
		
	}

	private void notificarCanal(Notificaciones notificacion, CampanaCanales canal) {
		

		switch (canal.getPrimaryKey().getCanal()) {
		case SMS:
			System.out.println("NOTIFICANDO POR CANAL SMS AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
			//TODO: IMPLEMENTAR
			break;
		case EMAIL:
			System.out.println("NOTIFICANDO POR CANAL EMAIL AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
			//TODO: IMPLEMENTAR
			break;
		case VOZ:
			System.out.println("NOTIFICANDO POR CANAL VOZ AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
			//TODO: IMPLEMENTAR
			break;
		default:
			break;
		}
		
	}

}
