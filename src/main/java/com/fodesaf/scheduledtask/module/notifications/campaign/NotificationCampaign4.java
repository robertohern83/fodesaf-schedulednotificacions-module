package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.EmailNotificationService;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService.MessageType;
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;
import com.fodesaf.scheduledtask.module.service.ConsecutivosService;
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
	
	private static final String SMS_TEMPLATE_1 = "Señor Patrono, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Evítese cobros judiciales y gastos adicionales.";
	
	private static final String SMS_TEMPLATE_2 = "Señor Patrono, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Su deuda será trasladada a Cobro Judicial.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Notificación de cobro - FODESAF";


	// The email body for recipients with non-HTML email clients.
	private static final String BODY_TEXT_1 = "Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto.";

	//The email body for recipients with HTML email clients.
	private static final String BODY_HTML_1 = "<html>\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. </p>\n" + 
			"<p>\n" + 
			"</body>\n" + 
			"</html>";
	
	@Override
	public String sendNotification(Map<String, Object> notificationData, NotificationChannel channel) throws NotificationException{
		String messageIdResult = null;
		
		Patronos patrono = (Patronos)notificationData.get("Patrono");
		DecimalFormat df = new DecimalFormat(Constants.AMOUNT_FORMAT); 
		int attemp = (int)notificationData.get("Attemp");
		
		
		switch (channel) {
		case SMS:
			System.out.println(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			String telefono = patronosService.obtenerTelefonoPatrono(patrono, true);
			if(null != telefono) {
				if(1 == attemp) {
					messageIdResult = smsService.sendSMSMessage(formatTelephone(telefono), SMS_TEMPLATE_1.replaceAll("<<MONTO>>", df.format(patrono.getDeudaTotal())), smsSender, MessageType.PROMOTIONAL);
				}
				else {
					messageIdResult = smsService.sendSMSMessage(formatTelephone(telefono), SMS_TEMPLATE_2.replaceAll("<<MONTO>>", df.format(patrono.getDeudaTotal())), smsSender, MessageType.PROMOTIONAL);
				}
			}
			else {
				throw new NotificationException(String.format("Campaña %s, Telefono a notificar no encontrado, segregado: %s", 
						this.getSupportedCampaign(), patrono.getSegregado()),
						NO_CONTACT_INFO_ERROR);
			}
			
			break;
		case EMAIL:
			System.out.println(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			String correo = patronosService.obtenerCorreoPatrono(patrono);
			
			if(null != correo) {
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
									BODY_HTML_1, 
									BODY_TEXT_1, 
									correo, 
									file, 
									"application/pdf", 
									"Notificacion.pdf");
				} catch (IOException | JRException | SQLException  e) {
					e.printStackTrace();
				}
			}
			else {
				throw new NotificationException(String.format("Campaña %s, Correo a notificar no encontrado, segregado: %s",
						this.getSupportedCampaign(), patrono.getSegregado()),
						NO_CONTACT_INFO_ERROR);
			}
			
			break;
		case VOICE:
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
