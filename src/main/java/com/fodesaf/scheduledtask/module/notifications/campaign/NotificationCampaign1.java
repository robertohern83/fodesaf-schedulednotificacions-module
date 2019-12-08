package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;

import net.sf.jasperreports.engine.JRException;

@Service
public class NotificationCampaign1 implements Notification {

	@Value("${fodesaf.notifications.email.sender}")
	private String sender;
	
	@Autowired
	EmailNotificationService emailService;
	
	@Autowired
    protected DataSource localDataSource;
	
	// The subject line for the email.
	private static String SUBJECT = "Email de ejemplo de notificacion de campaña";


	// The email body for recipients with non-HTML email clients.
	private static String BODY_TEXT = "Hola,\r\n"
                                        + "Este es un correo de ejemplo de  "
                                        + "un correo para contacto de clientes";

	// The HTML body of the email.
	private static String BODY_HTML = "<html>"
                                        + "<head></head>"
                                        + "<body>"
                                        + "<h1>Hola!</h1>"
                                        + "<p>Por favor vea el correo adjunto para certificar su deuda</p>"
                                        + "</body>"
                                        + "</html>";
	
	@Override
	public String sendNotification(Map<String, Object> notificationData, NotificationChannel channel) {
		String messageIdResult = null;
		String cedula = (String)notificationData.get("Cedula");
		String segregado = (String)notificationData.get("Segregado");
		String telefono = (String)notificationData.get("Telefono");
		
		switch (channel) {
		case SMS:
			System.out.println(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			break;
		case EMAIL:
			System.out.println(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			String correo = (String)notificationData.get("Correo");
			
			Map<String, Object> params = new HashMap<>();
			params.put("pSegregadoPrincipal", segregado);
			byte[] file = null;
			try {
				file = GenerateReportFromTemplate.createReportFromDatabase(localDataSource.getConnection(), params, "/Campana1_BD.jasper", "pdf");
				messageIdResult = emailService.sendEmailNotification(sender, SUBJECT, BODY_HTML, BODY_TEXT, correo, file, "application/pdf", "Notificacion.pdf");
			} catch (IOException | JRException | SQLException  e) {
				e.printStackTrace();
			}
			
			break;
		case VOICE:
			System.out.println(String.format("Enviando notificacion de VOZ, %s", this.getSupportedCampaign()));
			
			break;

		default:
			break;
		}
		return messageIdResult;

	}

	@Override
	public String getSupportedCampaign() {
		return "CAMPAIGN1";
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
