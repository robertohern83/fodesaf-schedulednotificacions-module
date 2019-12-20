package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.text.DecimalFormat;
import java.util.Arrays;
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
import com.fodesaf.scheduledtask.module.service.PatronosService;

@Service
public class NotificationCampaign3 implements Notification {

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
	
	private static final String SMS_TEMPLATE = "Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que ya está al cobro la cuota del mes de su arreglo de pago. El monto de la cuota es de ¢ <<MONTO>>.";
	
	// The subject line for the email.
	private static final String SUBJECT = "Notificación de cobro - FODESAF";


	// The email body for recipients with non-HTML email clients.
	private static final String BODY_TEXT_1 = "Preventivo (AL DIA): Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que ya está al cobro la cuota del mes de su arreglo de pago. El monto de la cuota es de ¢ <<MONTO>>.\n" + 
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
			" Cédula Jurídica de Fodesf es 3007092879\n" + 
			" \n" + 
			"Es importante anotar en el detalle del depósito / transferencia el número de cédula jurídica de la empresa, el número de cédula física o número de asegurado (en el caso de extranjero) de quien mantiene la deuda con el Fodesaf en el depósito.\n" + 
			" \n" + 
			" \n" + 
			"Debe reportar el comprobante al correo electrónico desaf.cobros@mtss.go.cr\n" + 
			" \n" + 
			"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"";
	
	private static final String BODY_TEXT_2 = "Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que usted mantiene un atraso en el arreglo de pago. Sírvase revisar archivo adjunto ";

	// The HTML body of the email.
	private static final String BODY_HTML_1 = "<html>\n" + 
			"\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que ya está al cobro la cuota del mes de su arreglo de pago. El monto de la cuota es de ¢ <<MONTO>>.\n" + 
			"</p>\n" + 
			"<p>\n" + 
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
			"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"</p>\n" + 
			"</body>\n" + 
			"\n" + 
			"</html>";
	
	private static final String BODY_HTML_2 = "<html>\n" + 
			"\n" + 
			"<head></head>\n" + 
			"<body>\n" + 
			"<p>\n" + 
			"Señor Patrono, el Departamento de Gestión de Cobro del Fodesaf informa que ya está al cobro la cuota del mes de su arreglo de pago. El monto de la cuota es de ¢ <<MONTO>>.\n" + 
			"</p>\n" + 
			"<p>\n" + 
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
			"Le agradecemos poner al día el monto indicado. Si ya canceló favor omitir este mensaje.\n" + 
			"</p>\n" + 
			"</body>\n" + 
			"\n" + 
			"</html>";
	
	@Override
	public String sendNotification(Map<String, Object> notificationData, NotificationChannel channel)  throws NotificationException{
		String messageIdResult = null;
		
		Patronos patrono = (Patronos)notificationData.get("Patrono");
		DecimalFormat df = new DecimalFormat("#.00"); 
		
		switch (channel) {
		case SMS:
			System.out.println(String.format("Enviando notificacion de SMS, %s", this.getSupportedCampaign()));
			String telefono = patronosService.obtenerTelefonoPatrono(patrono, true);
			if(null != telefono) {
				messageIdResult = smsService.sendSMSMessage(formatTelephone(telefono), SMS_TEMPLATE.replaceAll("<<MONTO>>", df.format(patrono.getCuotasAlCobro())), smsSender, MessageType.PROMOTIONAL);
			}
			else {
				System.out.println(String.format("Campaña %s, Telefono a notificar no encontrado, segregado: s%", this.getSupportedCampaign(), patrono.getSegregado()));
				throw new NotificationException(String.format("Campaña %s, Telefono a notificar no encontrado, segregado: s%", this.getSupportedCampaign(), patrono.getSegregado()));
			}
			break;
		case EMAIL:
			System.out.println(String.format("Enviando notificacion de EMAIL, %s", this.getSupportedCampaign()));
			String correo = patronosService.obtenerCorreoPatrono(patrono);
			
			if(null != correo) {
				int attemp = (int)notificationData.get("Attemp");
				
				if(1 == attemp) {
					emailService.sendEmailNotification(
							emailSender, 
							SUBJECT, 
							BODY_HTML_1.replaceAll("<<MONTO>>", String.valueOf(df.format(patrono.getCuotasAlCobro()))), 
							BODY_TEXT_1.replaceAll("<<MONTO>>", String.valueOf(df.format(patrono.getCuotasAlCobro()))), 
							correo, 
							null, 
							null, 
							null);
				}
				else {
					emailService.sendEmailNotification(
							emailSender, 
							SUBJECT, 
							BODY_HTML_2.replaceAll("<<MONTO>>", String.valueOf(df.format(patrono.getCuotasAlCobro()))), 
							BODY_TEXT_2.replaceAll("<<MONTO>>", String.valueOf(df.format(patrono.getCuotasAlCobro()))), 
							correo, 
							null, 
							null, 
							null);
				}
			}
			else {
				System.out.println(String.format("Campaña %s, Correo a notificar no encontrado, segregado: s%", this.getSupportedCampaign(), patrono.getSegregado()));
				throw new NotificationException(String.format("Campaña %s, Correo a notificar no encontrado, segregado: s%", this.getSupportedCampaign(), patrono.getSegregado()));
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
		return "Record. Cuota Arreglo CJ-CA";
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
