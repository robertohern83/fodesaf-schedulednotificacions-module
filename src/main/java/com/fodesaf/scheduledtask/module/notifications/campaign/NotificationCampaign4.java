package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.EmailNotificationService;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;
import com.fodesaf.scheduledtask.module.service.ConsecutivosService;
import com.fodesaf.scheduledtask.module.service.NotificacionesService;
import com.fodesaf.scheduledtask.module.service.PatronosService;
import com.fodesaf.scheduledtask.module.util.Constants;

import net.sf.jasperreports.engine.JRException;

@Service
public class NotificationCampaign4 implements Notification {

	@Value("${fodesaf.notifications.email.sender}")
	private String emailSender;
	
	@Value("${fodesaf.notifications.sms.sender}")
	private String smsSender;
	
	@Autowired
	EmailNotificationService emailService;
	
	@Autowired
	SMSNotificationService smsService;
	
	@Autowired
    protected DataSource localDataSource;
	
	@Autowired
	PatronosService patronosService;
	
	@Autowired
	ConsecutivosService consecutivosService;
	
	@Autowired
	NotificacionesService notificacionesService;
	
	//TODO: Reversar cambio a mensaje
	//private static final String SMS_TEMPLATE_1 = "Señor Patrono, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Evítese cobros judiciales y gastos adicionales.";
	private static final String SMS_TEMPLATE_1 = "Señor Patrono <<CEDULA>>, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Se le solicita amortizar este pendiente.";
	
	private static final String SMS_TEMPLATE_2 = "Señor Patrono <<CEDULA>>, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Su deuda será trasladada a Cobro Judicial.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Prevención de cobro – FODESAF";

	//TODO: Reversar cambio a mensaje
	// The email body for recipients with non-HTML email clients.
	//private static final String BODY_TEXT_1 = "Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto.";
	private static final String BODY_TEXT_1 = "Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. Se le solicita amortizar este pendiente.";
	//TODO: Reversar cambio a mensaje
	//The email body for recipients with HTML email clients.
	private static final String BODY_HTML_1 = "<html>\n" + 
			"<head></head>\n" + 
			"<body>\n" +
			"<p>\n" + 
			//"Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. </p>\n" +
			"Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. Se le solicita amortizar este pendiente.\n" +
			"</p>\n" + 
			"</body>\n" + 
			"</html>";
	
	@Override
	public String sendNotification(Notificaciones notificacion, NotificationChannel channel) throws NotificationException{
		String messageIdResult = null;
		final List<String> messageIds = new ArrayList<String>();
		Patronos patrono = notificacion.getPrimaryKey().getPatrono();
		DecimalFormat df = new DecimalFormat(Constants.AMOUNT_FORMAT); 
		int attemp = notificacion.getPrimaryKey().getIntento();
		
		
		switch (channel) {
		case SMS:
			logger.info(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			
			String messageToSend = (1 == attemp)?
					SMS_TEMPLATE_1.replaceAll("<<MONTO>>", df.format(patrono.getDeudaTotal())).replaceAll("<<CEDULA>>", patrono.getCedula())
					:
					SMS_TEMPLATE_2.replaceAll("<<MONTO>>", df.format(patrono.getDeudaTotal())).replaceAll("<<CEDULA>>", patrono.getCedula());
			
			NotificationCampaignHelper.iterateOverPhonesAndInvokeFunction(patrono, 
					NotificationCampaignHelper.buildSMSMessagesConsumer(
							smsService, 
							smsSender,
							notificacionesService,
							messageIds, 
							notificacion, 
							messageToSend), 
					NotificationCampaignHelper.getPhonesFilter(patronosService, patrono, true), 
					logger, 
					this.getSupportedCampaign());
			
			break;
		case EMAIL:
			logger.info(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			List<String> emails = patronosService.obtenerCorreoPatrono(patrono);
			
			if(null != emails && !emails.isEmpty()) {
				DecimalFormat dfConsecutive = new DecimalFormat("00000"); 
				int intConsecutive = consecutivosService.getNextConsecutive();
				if (-1 == intConsecutive) {
					throw new NotificationException("No se pudo obtener el siguiente consecutivo");
				}
				
				String consecutive = dfConsecutive.format(intConsecutive);
				Map<String, Object> params = new HashMap<>();
				params.put("pSegregado", patrono.getSegregado());
				params.put("pConsecutive", consecutive);
				byte[] file = null;
				try {
					file = GenerateReportFromTemplate.createReportFromDatabase(localDataSource.getConnection(), params, "/CampanaPrevencionCobro.jasper", "pdf");
					messageIdResult = 
							emailService.sendEmailNotification(
									emailSender, 
									SUBJECT, 
									BODY_HTML_1.replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
									BODY_TEXT_1.replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
									emails, 
									file, 
									"application/pdf", 
									"Notificacion.pdf");
				} catch (IOException | JRException | SQLException  e) {
					logger.error(e.getLocalizedMessage(), e);
				}
				
				//Insertar los destinos de la notificacion (para cada correo)
				final String messageId = messageIdResult;
				Arrays.asList(String.join(",", emails).split(","))
						.forEach(e -> notificacionesService.registrarDestino(notificacion, e, messageId));
			}
			else {
				throw new NotificationException(String.format("Campaña %s, Correo a notificar no encontrado, segregado: %s",
						this.getSupportedCampaign(), patrono.getSegregado()),
						NO_CONTACT_INFO_ERROR);
			}
			
			break;
		case VOICE:
			logger.error(String.format("Esta campaña no soporta notificaciones de voz, %s", this.getSupportedCampaign()));
			throw new NotificationException(String.format("Esta campaña no soporta notificaciones de voz, %s", this.getSupportedCampaign()), NOT_SUPPORTED_CHANNEL);
			
		default:
			break;
		}
		return messageIdResult;

	}

	public String formatTelephone(String telefono) {
		return String.format("+506%s",telefono);
	}

	@Override
	public Integer getSupportedCampaign() {
		return 4;
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
