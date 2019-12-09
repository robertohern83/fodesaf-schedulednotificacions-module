package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.notifications.EmailNotificationService;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService.MessageType;
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;

import net.sf.jasperreports.engine.JRException;

@Service
public class NotificationCampaign5 implements Notification {

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
	
	private static final String SMS_TEMPLATE_1 = "Señor Patrono, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución. El total pendiente es de ¢ <<MONTO>>. Evítese Cobros Judiciales y gastos adicionales.";
	
	private static final String SMS_TEMPLATE_2 = "Señor Patrono, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución. El total pendiente es de ¢ <<MONTO>>. Su deuda será trasladada a Cobro Judicial.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Notificación de cobro - FODESAF";


	// The email body for recipients with non-HTML email clients.
	private static final String BODY_TEXT_1 = "Señor Patrono, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución. Sírvase revisar archivo adjunto. ";

	//The email body for recipients with HTML email clients.
	private static final String BODY_HTML_1 = "<html>\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución. Sírvase revisar archivo adjunto.  </p>\n" + 
			"<p>\n" + 
			"</body>\n" + 
			"</html>";
	
	@Override
	public String sendNotification(Map<String, Object> notificationData, NotificationChannel channel) {
		String messageIdResult = null;
		String cedula = (String)notificationData.get("Cedula");
		String segregado = (String)notificationData.get("Segregado");
		String telefono = (String)notificationData.get("Telefono");
		double deudaTotal = (double)notificationData.get("DeudaTotal");
		int attemp = (int)notificationData.get("Attemp");
		
		
		switch (channel) {
		case SMS:
			System.out.println(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			
			if(1 == attemp) {
				messageIdResult = smsService.sendSMSMessage(formatTelephone(telefono), SMS_TEMPLATE_1.replaceAll("<<MONTO>>", String.valueOf(deudaTotal)), smsSender, MessageType.PROMOTIONAL);
			}
			else {
				messageIdResult = smsService.sendSMSMessage(formatTelephone(telefono), SMS_TEMPLATE_2.replaceAll("<<MONTO>>", String.valueOf(deudaTotal)), smsSender, MessageType.PROMOTIONAL);
			}
			
			break;
		case EMAIL:
			System.out.println(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			String correo = (String)notificationData.get("Correo");
			int consecutive = (int)notificationData.get("Consecutive");
			
			Map<String, Object> params = new HashMap<>();
			params.put("pSegregadoPrincipal", segregado);
			params.put("pConsecutive", consecutive);
			byte[] file = null;
			try {
				file = GenerateReportFromTemplate.createReportFromDatabase(localDataSource.getConnection(), params, "/Campana5_BD.jasper", "pdf");
				messageIdResult = 
						emailService.sendEmailNotification(
								emailSender, 
								SUBJECT, 
								BODY_HTML_1, 
								BODY_TEXT_1, 
								correo, 
								file, 
								"application/pdf", 
								"Notificacion.pdf");
			} catch (IOException | JRException | SQLException  e) {
				e.printStackTrace();
			}
			
			
			break;
		case VOICE:
			System.out.println(String.format("Esta campaña no soporta notificaciones de voz, %s", this.getSupportedCampaign()));
			break;

		default:
			break;
		}
		return messageIdResult;

	}

	public String formatTelephone(String telefono) {
		return String.format("+506%s",telefono);
	}

	@Override
	public String getSupportedCampaign() {
		return "CAMPAIGN5";
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
