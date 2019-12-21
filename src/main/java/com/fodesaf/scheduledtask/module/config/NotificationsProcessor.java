/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.fodesaf.scheduledtask.module.model.repositories.CampanaCanalesRepository;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationFactory;

/**
 * @author geanque
 *
 */
public class NotificationsProcessor implements Tasklet, StepExecutionListener {
	
	
	NotificationFactory factory;

	private static final String VOZ = "VOZ";

	private static final String EMAIL = "EMAIL";

	private static final String SMS = "SMS";

	private Logger logger = LoggerFactory.getLogger(NotificationsProcessor.class);

	private Page<Notificaciones> notificaciones;
	
	private NotificacionesRepository notificacionesRepo;
	private CampanaCanalesRepository campanaCanalesRepo;
	
	
	
	public NotificationsProcessor(NotificacionesRepository notificacionesRepository, CampanaCanalesRepository campanaCanalesRepository, NotificationFactory _factory) {
		super();
		this.factory = _factory;
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
		
		//int idCampana = item.getPrimaryKey().getCampana().getId();
		
		notificarCanal(item, item.getPrimaryKey().getCanal());
		
		/*Campanas campana = new Campanas();
		campana.setId(idCampana);
		
		List<CampanaCanales> canales = campanaCanalesRepo.findByPrimaryKeyCampana(campana);
		
		canales.forEach(canal -> {
			notificarCanal(item, canal);
		});*/
		
		
	}

	private void notificarCanal(Notificaciones notificacion, String canal) {
		
		Integer tipoCampana = notificacion.getPrimaryKey().getCampana().getTipo().getId();
		Notification notification = factory.getCaseService(tipoCampana);
		Map<String, Object> notificationData = loadParams(notificacion);
		try {
			switch (canal) {
				case SMS:		
					System.out.println("NOTIFICANDO POR CANAL SMS AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					notification.sendNotification(notificationData, NotificationChannel.SMS);
					break;
				case EMAIL:
					System.out.println("NOTIFICANDO POR CANAL EMAIL AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					notification.sendNotification(notificationData, NotificationChannel.EMAIL);
					break;
				case VOZ:
					System.out.println("NOTIFICANDO POR CANAL VOZ AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					notification.sendNotification(notificationData, NotificationChannel.VOICE);
					break;
				default:
					break;
			}
		//Se captura toda excepción, para impedir que el proceso de notificación de campañas se detenga por un error en una notificación	
		} catch (Exception e) {
			// FIXME: Almacenar en bitácora error generado
			e.printStackTrace();
		}
		
		
	}

	private Map<String, Object> loadParams(Notificaciones notificacion) {
		Map<String, Object> notificationData = new HashMap<String, Object>();
		notificationData.put("Patrono", notificacion.getPrimaryKey().getPatrono());
		//FIXME manejar consecutivo
		notificationData.put("Consecutive", 9999);
		notificationData.put("Attemp", 1);
		return notificationData;
	}

}
