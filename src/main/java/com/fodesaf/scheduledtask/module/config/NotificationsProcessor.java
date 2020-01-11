/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

import com.fodesaf.scheduledtask.module.model.GestionCobro;
import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.repositories.CampanaCanalesRepository;
import com.fodesaf.scheduledtask.module.model.repositories.GestionCobroRepository;
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
	
	private static final String PAGO_GESTIONADO = "PAGO_GESTIONADO";
	
	private static final String ENVIADA = "ENVIADA";
	
	private static final List<String> RAZONES = Collections.unmodifiableList(Arrays.asList(
			"Reporte de su pago"
			));
	/*
	"Cédula jurídica de la Desaf.",
	"Reporte de su pago",
	"Certificación de patrono al día",
	"Requisitos sobre trámites administrativos",
	"Información general",
	"Trámites administrativos",
	"Requisitos de arreglo de pago",
	"Monto de su deuda",
	"Estado de cuenta",
	"Medios de Pago"
	*/

	private Logger logger = LoggerFactory.getLogger(NotificationsProcessor.class);

	private Page<Notificaciones> notificaciones;
	
	private NotificacionesRepository notificacionesRepo;
	
	private GestionCobroRepository gestionCobroRepo;
	
	public NotificationsProcessor(NotificacionesRepository notificacionesRepository, CampanaCanalesRepository campanaCanalesRepository, GestionCobroRepository gestionCobroRepository, NotificationFactory _factory) {
		super();
		this.factory = _factory;
		this.notificacionesRepo = notificacionesRepository;
		this.gestionCobroRepo = gestionCobroRepository;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void beforeStep(StepExecution stepExecution) {
		ExecutionContext executionContext = stepExecution
		          .getJobExecution()
		          .getExecutionContext();
		this.notificaciones = (Page<Notificaciones>) executionContext.get("notificaciones");
		logger.debug("Iniciando procesamiento de Notificaciones...");

	}
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		notificaciones.forEach(item -> {
			if(!pagoRealizado(item)) {
				String messageId = notificarCanal(item);
				item.setMessageId(messageId);
				item.setFechaEnvio(new Date());
				item.setEstatus(ENVIADA);
				System.out.println("NOTIFICAR A PATRONO -> " + item.getPrimaryKey().getPatrono().getNombre());
			} else {
				item.setEstatus(PAGO_GESTIONADO);
			}
			notificacionesRepo.save(item);
		});
		return RepeatStatus.FINISHED;
	}

	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		logger.debug("Lines Processor ended.");
        return ExitStatus.COMPLETED;
	}
	
	/**
	 * Valida si ya tiene un pago gestionado
	 * @param item
	 * @return 
	 * @return 
	 * @return
	 */
	private boolean pagoRealizado(Notificaciones item) {
		AtomicReference<Boolean> result = new AtomicReference<Boolean>(false);
		
		if(item.getPrimaryKey().getIntento() > 1) {
			
			List<GestionCobro> cobro = gestionCobroRepo
					.findBySegregado(item.getPrimaryKey().getPatrono().getSegregado());
			
			cobro.forEach(c -> {
				if(c.getFechaContacto().after(item.getFechaCreacion()) && razonLlamadaCobro(c)) {
					result.set(true);
				}
			});
			
		}
		
		return result.get();
	}
	

	/**
	 * Verifica que la razón de la gestion del cobro sea un reporte de pago
	 * @param c
	 * @return
	 */
	private boolean razonLlamadaCobro(GestionCobro c) {
		return RAZONES.contains(c.getRazonLlamada());
	}

	/**
	 * Envia una notificacion dependiendo del canal
	 * @param notificacion
	 * @return
	 */
	private String notificarCanal(Notificaciones notificacion) {
		String messageId = null;
		Integer tipoCampana = notificacion.getPrimaryKey().getCampana().getTipo().getId();
		Notification notification = factory.getCaseService(tipoCampana);
		Map<String, Object> notificationData = loadParams(notificacion);
		try {
			switch (notificacion.getPrimaryKey().getCanal()) {
				case SMS:		
					System.out.println("NOTIFICANDO POR CANAL SMS AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					messageId = notification.sendNotification(notificationData, NotificationChannel.SMS);
					break;
				case EMAIL:
					System.out.println("NOTIFICANDO POR CANAL EMAIL AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					messageId = notification.sendNotification(notificationData, NotificationChannel.EMAIL);
					break;
				case VOZ:
					System.out.println("NOTIFICANDO POR CANAL VOZ AL PATRONO -> " + notificacion.getPrimaryKey().getPatrono().getNombre());
					messageId = notification.sendNotification(notificationData, NotificationChannel.VOICE);
					break;
				default:
					break;
			}
			
		
		//Se captura toda excepción, para impedir que el proceso de notificación de campañas se detenga por un error en una notificación	
		} catch (Exception e) {
			// FIXME: Almacenar en bitácora error generado
			e.printStackTrace();
		}
		
		return messageId;
	}

	private Map<String, Object> loadParams(Notificaciones notificacion) {
		Map<String, Object> notificationData = new HashMap<String, Object>();
		notificationData.put("Patrono", notificacion.getPrimaryKey().getPatrono());
		notificationData.put("Attemp", notificacion.getPrimaryKey().getIntento());
		return notificationData;
	}

}
