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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.EmailNotificationService;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;
import com.fodesaf.scheduledtask.module.service.NotificacionesService;
import com.fodesaf.scheduledtask.module.service.PatronosService;
import com.fodesaf.scheduledtask.module.util.Constants;

import net.sf.jasperreports.engine.JRException;

@Service
@Scope("prototype")
public class NotificationCampaign1 implements Notification {

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
	NotificacionesService notificacionesService;
	
	
	//TODO: Reversar cambio a mensaje
	//private static final String SMS_TEMPLATE = "Señor Patrono, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Le agradecemos ponerse al día lo antes posible. Si ya canceló favor omitir este mensaje.";
	private static final String SMS_TEMPLATE = "Señor Patrono <<CEDULA>>, el Fodesaf le informa que su arreglo de pago se encuentra atrasado. El total pendiente es de ¢ <<MONTO>>. Se le solicita amortizar este pendiente.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Notificación de cobro - FODESAF";

	// The email body for recipients with non-HTML email clients.
	private static final String BODY_TEXT_1 = 
			"Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. El total pendiente es de ¢ <<MONTO>>.\n" + 
			" \n" + 
			"Usted puede realizar su pago mediante el servicio de Conectividad con el Banco de Costa Rica (BCR).\n" + 
			"El pago puede realizarse por medio del servicio de Conectividad en ventanilla (indicando al cajero el número patronal completo, el cual consta de 18 dígitos) o desde su cuenta del BCR seleccionando: 1-Pago de Servicios 2-Cuotas y Planes 3-Cobro FODESAF 4-Pago a realizar (debe colocar el número patronal completo, el cual consta de 18 dígitos). Con lo anterior, el pago se aplicará automáticamente a su deuda, o bien puede realizarlo por medio de depósito bancario o transferencia a las siguientes cuentas:\n" + 
			" \n" + 
			"1. Banco Nacional de Costa Rica:\n" + 
			"·     	Cuenta IBAN: CR94015100010010777734\n" + 
			"·     	Nombre de la cuenta: DESAF-INGRESOS\n" + 
			" \n" + 
			"2. Banco de Costa Rica:\n" + 
			"·    	 Cuenta IBAN: CR36015201001029593489\n" + 
			"·     	Nombre de la cuenta: Dirección General de Asignaciones Familiares\n" + 
			" \n" + 
			" Cédula Jurídica de Fodesf es: 3007092879\n" + 
			"Es importante anotar en el detalle del depósito / transferencia el número de cédula jurídica de la empresa, el número de cédula física o número de asegurado (en el caso de extranjero) de quien mantiene la deuda con el Fodesaf en el depósito.\n" + 
			" \n" + 
			"Debe reportar el comprobante al correo electrónico desaf.cobros@mtss.go.cr\n" + 
			//TODO: Reversar cambio a mensaje
			//"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"Se le solicita amortizar este pendiente.\n" + 
			"";
	
	//private static final String BODY_TEXT_2 = "Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto.";
	private static final String BODY_TEXT_2 = 
			"Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. Se le solicita amortizar este pendiente. ";

	//TODO: Reversar cambio a mensaje
	// The HTML body of the email.
	private static final String BODY_HTML_1 = "\n" + 
			"<html>\n" + 
			"\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. El total pendiente es de ¢ <<MONTO>>.\n" + 
			"</p>\n" + 
			"<p>\n" + 
			"Usted puede realizar su pago mediante el servicio de Conectividad con el Banco de Costa Rica (BCR).\n" + 
			"El pago puede realizarse por medio del servicio de Conectividad en ventanilla (indicando al cajero el número patronal completo, el cual consta de 18 dígitos) o desde su cuenta del BCR seleccionando: 1-Pago de Servicios 2-Cuotas y Planes 3-Cobro FODESAF 4-Pago a realizar (debe colocar el número patronal completo, el cual consta de 18 dígitos). Con lo anterior, el pago se aplicará automáticamente a su deuda, o bien puede realizarlo por medio de depósito bancario o transferencia a las siguientes cuentas:\n" + 
			"</p>\n" + 
			"<p>\n" + 
			"<ol>\n" + 
			"<li>\n" + 
			"Banco Nacional de Costa Rica:\n" + 
			"  <ul>\n" + 
			"    <li>\n" + 
			"      Cuenta IBAN: CR94015100010010777734\n" + 
			"    </li>\n" + 
			"    <li>\n" + 
			"      Nombre de la cuenta: DESAF-INGRESOS\n" + 
			"    </li>\n" + 
			"  </ul>\n" + 
			"</li>\n" + 
			"<li>\n" + 
			"Banco de Costa Rica:\n" + 
			"<ul>\n" + 
			"  <li>\n" + 
			"    Cuenta IBAN: CR36015201001029593489\n" + 
			"  </li>\n" + 
			"  <li>\n" + 
			"    Nombre de la cuenta: Dirección General de Asignaciones Familiares\n" + 
			"  </li>\n" + 
			"</ul>\n" + 
			"</li>\n" + 
			"</ol>\n" + 
			"</p>\n" + 
			"<p>\n" + 
			" Cédula Jurídica de Fodesf es: 3007092879\n" + 
			"Es importante anotar en el detalle del depósito / transferencia el número de cédula jurídica de la empresa, el número de cédula física o número de asegurado (en el caso de extranjero) de quien mantiene la deuda con el Fodesaf en el depósito.\n" + 
			"</p>\n" + 
			"<p>\n" + 
			"Debe reportar el comprobante al correo electrónico desaf.cobros@mtss.go.cr\n" + 
			"</p>\n" + 
			"<p>\n" + 
			//"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" +
			"Se le solicita amortizar este pendiente.\n" +
			"</p>\n" + 
			"</body>\n" + 
			"\n" + 
			"</html>";
	
	//TODO: Reversar cambio a mensaje
	private static final String BODY_HTML_2 = "<html>\n" + 
			"<head></head>\n" + 
			"<body>\n" +
			"<p>\n" + 
			//TODO: Reversar cambio a mensaje
			//"Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto </p>\n" + 
			"Señor Patrono <<NOMBRE_PATRONO>>, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto. Se le solicita amortizar este pendiente.\n" + 
			"</p>\n" + 
			"</body>\n" + 
			"</html>";
	
	@Override
	public String sendNotification(Notificaciones notificacion, NotificationChannel channel) throws NotificationException {
		String messageIdResult = null;
		final List<String> messageIds = new ArrayList<String>();
		Patronos patrono = notificacion.getPrimaryKey().getPatrono();
		DecimalFormat df = new DecimalFormat(Constants.AMOUNT_FORMAT); 
		
		switch (channel) {
		case SMS:
			logger.info(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			
			NotificationCampaignHelper.iterateOverPhonesAndInvokeFunction(patrono, 
					NotificationCampaignHelper.buildSMSMessagesConsumer(
							smsService, 
							smsSender, 
							notificacionesService,
							messageIds, 
							notificacion, 
							SMS_TEMPLATE.replaceAll(
								"<<MONTO>>", 
								df.format(
										patrono.getCuotasAlCobro())).replaceAll("<<CEDULA>>", 
										patrono.getCedula())), 
					NotificationCampaignHelper.getPhonesFilter(patronosService, patrono, true), 
					logger, 
					this.getSupportedCampaign());
			
			break;
		case EMAIL:
			logger.info(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			List<String> emails = patronosService.obtenerCorreoPatrono(patrono);
			
			if(null != emails && !emails.isEmpty()) {
				int attemp = notificacion.getPrimaryKey().getIntento();
				
				if(1 == attemp) {
					messageIdResult = emailService.sendEmailNotification(
							emailSender, 
							SUBJECT, 
							BODY_HTML_1.replaceAll("<<MONTO>>", df.format(patrono.getCuotasAlCobro())).replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
							BODY_TEXT_1.replaceAll("<<MONTO>>", df.format(patrono.getCuotasAlCobro())).replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
							emails, 
							null, 
							null, 
							null);
				}
				else {
					Map<String, Object> params = new HashMap<>();
					params.put("pSegregadoPrincipal", patrono.getSegregado());
					byte[] file = null;
					try {
						file = GenerateReportFromTemplate.createReportFromDatabase(localDataSource.getConnection(), params, "/Campana1_BD.jasper", "pdf");
						messageIdResult = 
								emailService.sendEmailNotification(
										emailSender, 
										SUBJECT, 
										BODY_HTML_2.replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
										BODY_TEXT_2.replaceAll("<<NOMBRE_PATRONO>>", patrono.getNombre()), 
										emails, 
										file, 
										"application/pdf", 
										"Notificacion.pdf");
					} catch (IOException | JRException | SQLException  e) {
						logger.error(e.getLocalizedMessage(), e);
						throw new NotificationException("Excepcion al generar notificacion de correo electronico", e);
					}
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
		
		
		if(null == messageIdResult && null != messageIds && 0 < messageIds.size()) {
			messageIdResult = String.join(",", messageIds);
		}
		
		return messageIdResult;

	}


	@Override
	public Integer getSupportedCampaign() {
		return 1;
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}
	


}
