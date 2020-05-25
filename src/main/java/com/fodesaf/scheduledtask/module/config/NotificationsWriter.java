/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.fodesaf.scheduledtask.module.model.NotificacionesPK;
import com.fodesaf.scheduledtask.module.model.repositories.ControlNotificacionesDiariasRepository;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;

/**
 * @author geanque
 *
 */
public class NotificationsWriter implements Tasklet, StepExecutionListener {

	

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
			if(null != item.getPrimaryKey().getPatrono()) {
				if(1 == item.getPrimaryKey().getIntento()) {
					insertarSegundoIntento(item);
				}
				System.out.println("NOTIFICACION ENVIADA A PATRONO -> " + item.getPrimaryKey().getPatrono().getNombre());
			} else {
				System.out.println("PATRONO NO SE ENCUENTRA EN LA BD, SE OMITE NOTIFICACION -> " + item.getPrimaryKey().getCedula());
				
			}
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
	
	/**
	 * Inserta el segundo intento a notificar
	 * @param notificacion
	 */
	private void insertarSegundoIntento(Notificaciones notificacion) {
		
		getCanalesPorTipoCampaña2(notificacion.getPrimaryKey().getCampana()).forEach(canal -> {
			Notificaciones newNotificacion = generarNotificacion2(notificacion, canal, false);			
			notificacionesRepo.save(newNotificacion);
			
		});
		
	}
	
	/**
	 * Genera una notificacion
	 * @param notificacion
	 * @param canal
	 * @param primerIntento
	 * @return
	 */
	private Notificaciones generarNotificacion2(Notificaciones notificacion, String canal, boolean primerIntento) {
		Notificaciones newNotificacion = new Notificaciones();
		newNotificacion.setEstatus("PENDIENTE");
		newNotificacion.setFechaCreacion(new Date());
		NotificacionesPK primaryKey = new NotificacionesPK();
		primaryKey.setPatrono(notificacion.getPrimaryKey().getPatrono());
		primaryKey.setCedula(notificacion.getPrimaryKey().getCedula());
		primaryKey.setCanal(canal);
		primaryKey.setCampana(notificacion.getPrimaryKey().getCampana());
		primaryKey.setSegmento(notificacion.getPrimaryKey().getSegmento());
		primaryKey.setIntento(2);
		newNotificacion.setPrimaryKey(primaryKey);
		if(!primerIntento) {
			newNotificacion.setFechaProgramada(getFechaProgramada(notificacion.getPrimaryKey().getCampana()));
		}
		return newNotificacion;
	}
	
	/**
	 * Obtiene la fecha programada para el siguiente intento por tipo de campaña
	 * @param campana
	 * @return
	 */
	private LocalDate getFechaProgramada(Campanas campana) {
		
		LocalDate fecha = null;
		switch (campana.getTipo().getId()) {
		case 1: // 7dias
			fecha = addDaysSkippingWeekends(LocalDate.now(), 7);
			break;
		case 3: // 15 dias
			fecha = addDaysSkippingWeekends(LocalDate.now(), 15);
			break;
		case 4: case 5: // 10 dias
			fecha = addDaysSkippingWeekends(LocalDate.now(), 10);
			break;

		default:
			break;
		}

		
		return fecha;
	}
	
	/**
	 * Agrega dias laborales a una fecha
	 * @param date
	 * @param days
	 * @return
	 */
	private static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
	    LocalDate result = date;
	    int addedDays = 0;
	    while (addedDays < days) {
	        result = result.plusDays(1);
	        if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
	            ++addedDays;
	        }
	    }
	    return result;
	}
	
	/**
	 * Obtiene los canales disponibles por tipo de campaña para intento 2
	 * @param campana
	 * @return
	 */
	private ArrayList<String> getCanalesPorTipoCampaña2(Campanas campana) {
		ArrayList<String> canales = new ArrayList<String>();
		
		switch (campana.getTipo().getId()) {
		case 1: case 3:
			canales.add("SMS");
			canales.add("EMAIL");
			break;
		case 4: case 5:
			canales.add("SMS");
			break;

		default:
			break;
		}
		return canales;
	}

	
	/**
	 * Actualiza en contador diario de notificaciones por campaña
	 * @param campana
	 * @param cantidad
	 */
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
