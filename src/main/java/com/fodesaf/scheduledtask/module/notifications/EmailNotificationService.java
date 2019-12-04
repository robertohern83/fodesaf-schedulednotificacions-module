package com.fodesaf.scheduledtask.module.notifications;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.util.IOUtils;

@Service
public class EmailNotificationService {
	
	// Replace sender@example.com with your "From" address.
		// This address must be verified with Amazon SES.
		private static String SENDER = "info@in2cloudsconsulting.com";

		// Replace recipient@example.com with a "To" address. If your account 
		// is still in the sandbox, this address must be verified.
		//private static String RECIPIENT = "roberto.hernandez@in2cloudsconsulting.com";
		
		private static String RECIPIENT = "consultorias.rhm@gmail.com";


		// The subject line for the email.
		private static String SUBJECT = "Email de ejemplo de notificacion de campaña";

		// The full path to the file that will be attached to the email.
		// If you're using Windows, escape backslashes as shown in this variable.
		private static String ATTACHMENT = "/Users/robertohernandez/Downloads/0107.xls";

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

	static AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.defaultClient();
	
	public static void main (String args[]) throws MessagingException, IOException {
		
		EmailNotificationService service = new EmailNotificationService();
		service.sendEmailNotification(SENDER, SUBJECT, RECIPIENT, IOUtils.toByteArray(new FileInputStream(new File(ATTACHMENT))), "application/vnd.ms-excel", "AdjuntoEjemplo.xls");
		
	}
	
	public String sendEmailNotification(String sender, String Subject, String recipient, byte[] attachment, String contentType, String fileName) {
		Session session = Session.getDefaultInstance(new Properties());
        
		ByteArrayDataSource byteDataSource = new ByteArrayDataSource(attachment,contentType);
        // Create a new MimeMessage object.
        MimeMessage message = new MimeMessage(session);
        
        // Add subject, from and to lines.
        try {
			message.setSubject(SUBJECT, "UTF-8");
		
	        message.setFrom(new InternetAddress(SENDER));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT));
	
	        // Create a multipart/alternative child container.
	        MimeMultipart msg_body = new MimeMultipart("alternative");
	        
	        // Create a wrapper for the HTML and text parts.        
	        MimeBodyPart wrap = new MimeBodyPart();
	        
	        // Define the text part.
	        MimeBodyPart textPart = new MimeBodyPart();
	        textPart.setContent(BODY_TEXT, "text/plain; charset=UTF-8");
	                
	        // Define the HTML part.
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(BODY_HTML,"text/html; charset=UTF-8");
	                
	        // Add the text and HTML parts to the child container.
	        msg_body.addBodyPart(textPart);
	        msg_body.addBodyPart(htmlPart);
	        
	        // Add the child container to the wrapper object.
	        wrap.setContent(msg_body);
	        
	        // Create a multipart/mixed parent container.
	        MimeMultipart msg = new MimeMultipart("mixed");
	        
	        // Add the parent container to the message.
	        message.setContent(msg);
	        
	        // Add the multipart/alternative part to the message.
	        msg.addBodyPart(wrap);
	        
	        // Define the attachment
	        MimeBodyPart att = new MimeBodyPart();
	        DataSource fds = byteDataSource;
	        att.setDataHandler(new DataHandler(fds));
	        att.setFileName(fileName);
	        
	        // Add the attachment to the message.
	        msg.addBodyPart(att);
			
			/** FIN DE ARMADO DE MENSAJE **/
	        
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        message.writeTo(outputStream);
	        RawMessage rawMessage = 
	        		new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
	        
	        SendRawEmailRequest rawEmailRequest = 
	        		new SendRawEmailRequest(rawMessage);
	        		    //.withConfigurationSetName(CONFIGURATION_SET);
	        
	        return client.sendRawEmail(rawEmailRequest).getMessageId();
        } catch (MessagingException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
