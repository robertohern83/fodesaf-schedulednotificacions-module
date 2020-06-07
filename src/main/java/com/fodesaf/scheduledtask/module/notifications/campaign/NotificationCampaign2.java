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

import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.ConnectNotificationService;
import com.fodesaf.scheduledtask.module.notifications.EmailNotificationService;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.reports.GenerateReportFromTemplate;
import com.fodesaf.scheduledtask.module.service.PatronosService;
import com.fodesaf.scheduledtask.module.util.Constants;

import net.sf.jasperreports.engine.JRException;

@Service
public class NotificationCampaign2 implements Notification {

	@Value("${fodesaf.notifications.email.sender}")
	private String emailSender;
	
	@Value("${fodesaf.notifications.sms.sender}")
	private String smsSender;
	
	@Value("${fodesaf.notifications.connect.contactflowC2}")
	private String contactFlowId;
	
	@Autowired
	EmailNotificationService emailService;
	
	@Autowired
	SMSNotificationService smsService;
	
	@Autowired
	ConnectNotificationService connectService;
	
	@Autowired
    protected DataSource localDataSource;
	
	@Autowired
	PatronosService patronosService;
	
	private static final String SMS_TEMPLATE = "Señor Patrono <<CEDULA>>, el Fodesaf le informa que mantiene una deuda con nuestra institución. El total pendiente es de <<MONTO>>. Si desea más información nos puede contactar a desaf.cobros@mtss.go.cr o al teléfono 2547-3600 opción 9.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Notificación de cobro - FODESAF";


	// The email body for recipients with non-HTML email clients.
	private static final String BODY_TEXT_1 = "Señor Patrono <<NOMBRE_PATRONO>>, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución.\n" + 
			" \n" + 
			"Se adjunta estado de la deuda actualizado con el detalle de los periodos pendientes: \n" + 
			"Usted puede realizar su pago mediante el servicio de Conectividad con el Banco de Costa Rica (BCR). El pago puede realizarse por medio del servicio de Conectividad en ventanilla (indicando al cajero el número patronal completo, el cual consta de 18 dígitos) o desde su cuenta del BCR seleccionando: 1-Pago de Servicios 2-Cuotas y Planes 3-Cobro FODESAF 4-Pago a realizar (debe colocar el número patronal completo, el cual consta de 18 dígitos). Con lo anterior, el pago se aplicará automáticamente a su deuda, o bien puede realizarlo por medio de depósito bancario o transferencia a las siguientes cuentas:\n" + 
			" \n" + 
			"1. Banco Nacional de Costa Rica:\n" + 
			"·     	Cuenta IBAN: CR94015100010010777734\n" + 
			"·     	Nombre de la cuenta: DESAF-INGRESOS\n" + 
			" \n" + 
			"2. Banco de Costa Rica:\n" + 
			"·     	Cuenta IBAN: CR36015201001029593489\n" + 
			"·     	Nombre de la cuenta: Dirección General de Asignaciones Familiares\n" + 
			" \n" + 
			" Cédula Jurídica de Fodesaf es 3007092879\n" + 
			" \n" + 
			"Es importante anotar en el detalle del depósito o transferencia el número de cédula jurídica de la empresa, el número de cédula física o número de asegurado (en el caso de extranjero) de quien mantiene la deuda con el Fodesaf en el depósito.\n" + 
			" \n" + 
			" \n" + 
			"Debe reportar el comprobante al correo electrónico desaf.cobros@mtss.go.cr \n" + 
			"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"";
	
	
	// The HTML body of the email.
	private static final String BODY_HTML_1 = "\n" + 
			"<html>\n" + 
			"\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono <<NOMBRE_PATRONO>>, el Fondo de Desarrollo Social y Asignaciones Familiares (Fodesaf) le informa que mantiene una deuda con la institución.\n" + 
			"</p>\n" + 
			"<p>\n" + 
			"Se adjunta estado de la deuda actualizado con el detalle de los periodos pendientes. \n" + 
			"Usted puede realizar su pago mediante el servicio de Conectividad con el Banco de Costa Rica (BCR). El pago puede realizarse por medio del servicio de Conectividad en ventanilla (indicando al cajero el número patronal completo, el cual consta de 18 dígitos) o desde su cuenta del BCR seleccionando: 1-Pago de Servicios 2-Cuotas y Planes 3-Cobro FODESAF 4-Pago a realizar (debe colocar el número patronal completo, el cual consta de 18 dígitos). Con lo anterior, el pago se aplicará automáticamente a su deuda, o bien puede realizarlo por medio de depósito bancario o transferencia a las siguientes cuentas:\n" + 
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
			"Es importante anotar en el detalle del depósito o transferencia el número de cédula jurídica de la empresa, el número de cédula física o número de asegurado (en el caso de extranjero) de quien mantiene la deuda con el Fodesaf en el depósito.\n" + 
			"</p>\n" + 
			"<p>\n" + 
			"Debe reportar el comprobante al correo electrónico desaf.cobros@mtss.go.cr \n" + 
			"</p>\n" + 
			"<p>\n" + 
			"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"</p>\n" + 
			"</body>\n" + 
			"\n" + 
			"</html>\n" + 
			"";
	
	@Override
	public String sendNotification(Map<String, Object> notificationData, NotificationChannel channel)  throws NotificationException {
		String messageIdResult = null;
		final List<String> messageIds = new ArrayList<String>();
		Patronos patrono = (Patronos)notificationData.get("Patrono");
		DecimalFormat df = new DecimalFormat(Constants.AMOUNT_FORMAT); 
		
		switch (channel) {
		case SMS:
			logger.info(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			
			NotificationCampaignHelper.iterateOverPhonesAndInvokeFunction(patrono, 
					NotificationCampaignHelper.buildSMSMessagesConsumer(
							smsService, 
							smsSender, 
							messageIds, 
							patrono, 
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
			
			if(null != emails) {
				Map<String, Object> params = new HashMap<>();
				params.put("pCedula", patrono.getCedula());
				byte[] file = null;
				try {
					file = GenerateReportFromTemplate.createReportFromDatabase(localDataSource.getConnection(), params, "/Campana2_BD.jasper", "pdf");
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
					throw new NotificationException("Excepcion al generar notificacion de correo electronico", e);
				}
			}
			else {
				logger.error(String.format("Campaña %s, Correo a notificar no encontrado, segregado: %s", 
						this.getSupportedCampaign(), patrono.getSegregado()));
				
				throw new NotificationException(String.format("Campaña %s, Correo a notificar no encontrado, segregado: %s", 
						this.getSupportedCampaign(), patrono.getSegregado()),
						NO_CONTACT_INFO_ERROR);
			}
			
			break;
		case VOICE:
			logger.info(String.format("Enviando notificación por voz, %s", this.getSupportedCampaign()));
			
			Map<String, String> attributes = new HashMap<>();
			attributes.put("deudaTotal", df.format(patrono.getDeudaTotal()));
			
			NotificationCampaignHelper.iterateOverPhonesAndInvokeFunction(patrono, 
					NotificationCampaignHelper.buildVoiceMessagesConsumer(
							connectService,
							contactFlowId,
							attributes, 
							messageIds,
							patrono), 
					NotificationCampaignHelper.getPhonesFilter(patronosService, patrono, false), 
					logger, 
					this.getSupportedCampaign());
			break;

		default:
			break;
		}
		
		if(null == messageIdResult && null != messageIds && 0 < messageIds.size()) {
			messageIdResult = String.join(",", messageIds);
		}
		
		return messageIdResult;

	}

	public String formatTelephone(String telefono) {
		return String.format("+506%s",telefono);
	}

	@Override
	public Integer getSupportedCampaign() {
		return 2;
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
