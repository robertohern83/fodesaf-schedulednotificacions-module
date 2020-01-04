/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Functions;
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

import com.fodesaf.scheduledtask.module.model.Campanas;
import com.fodesaf.scheduledtask.module.model.ControlNotificacionesDiarias;
import com.fodesaf.scheduledtask.module.model.ControlNotificacionesDiariasPK;
import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.ControlNotificacionesDiariasRepository;
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
	private ControlNotificacionesDiariasRepository controlRepo;
	
	public NotificationsWriter(NotificacionesRepository notificacionesRepository, ControlNotificacionesDiariasRepository controlRepository) {
		super();
		this.notificacionesRepo = notificacionesRepository;
		this.controlRepo = controlRepository;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution
		          .getJobExecution()
		          .getExecutionContext();
		this.notificaciones = (Page<Notificaciones>) executionContext.get("notificaciones");
		logger.debug("Iniciando actualización de Notificaciones...");

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

	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		//agrupar notificaciones por campaña
		Map<Campanas, Long> grouped = notificaciones.stream().collect(
				Collectors.groupingBy(
						x -> {return x.getPrimaryKey().getCampana();
							}, Collectors.counting()));		
		
		//Se restan al contador diario por campaña
		grouped.forEach((campana,cantidad)-> actualizarContador(campana,cantidad));
		
		logger.debug("Finalizadas las Notificaciones...");
        return ExitStatus.COMPLETED;
	}
	
	
	private void actualizarContador(Campanas campana, Long cantidad) {
		ControlNotificacionesDiariasPK pk = new ControlNotificacionesDiariasPK();
		pk.setCampana(campana);
		pk.setFechaControl(LocalDate.now());

		ControlNotificacionesDiarias control = controlRepo.findByPrimaryKey(pk);

		if (null != control) {
			control.setRestantes(control.getRestantes() - cantidad);
		} else {
			control = new ControlNotificacionesDiarias(pk, campana.getMaximoNotificacionesDiarias() - cantidad);
		}
		
		controlRepo.save(control);
	}

}
