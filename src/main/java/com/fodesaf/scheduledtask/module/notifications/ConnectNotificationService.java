package com.fodesaf.scheduledtask.module.notifications;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.connect.AmazonConnect;
import com.amazonaws.services.connect.AmazonConnectClientBuilder;
import com.amazonaws.services.connect.model.StartOutboundVoiceContactRequest;
import com.amazonaws.services.connect.model.StartOutboundVoiceContactResult;

/**
 *  Servicio que permite notificar telefonos por medio de contact flows establecidos en Connect. 
 *  Recibe el id del contactflow, el numero de telefono a contactar, y el set de atributos
 *  parámetro valor necesarios para el envio de una notificación
 * @author robertohernandez
 *
 */
@Service
public class ConnectNotificationService {
	
	static AmazonConnect connect = AmazonConnectClientBuilder.defaultClient();
	
	@Value("${fodesaf.connect.instance}")
	private String connectInstanceId;
	
	@Value("${fodesaf.connect.outboundphone}")
	private String outboundPhone;

	public static void main (String args[]) {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("clientName", "Roberto Hernandez");
		ConnectNotificationService notification = new ConnectNotificationService();
		notification.sendVoiceNotification("d82efed6-2a31-4d86-91b2-87d48cfb53f4", attributes, "+50687307606");
		
	}
	
	/**
	 * Método que recibe el contact flow, el telefono destino y los atributos a setear
	 * @return id de contacto
	 */
	public String sendVoiceNotification(String contactFlowId, Map<String, String> attributes, String phoneNumber) {
		
		StartOutboundVoiceContactRequest startOutboundVoiceContactRequest = new StartOutboundVoiceContactRequest();
		startOutboundVoiceContactRequest.setInstanceId(connectInstanceId);
		startOutboundVoiceContactRequest.setContactFlowId(contactFlowId);
		startOutboundVoiceContactRequest.setDestinationPhoneNumber(phoneNumber);
		startOutboundVoiceContactRequest.setSourcePhoneNumber(outboundPhone);
		attributes.forEach((key, value) -> {startOutboundVoiceContactRequest.addAttributesEntry(key, value);});
		
		StartOutboundVoiceContactResult result = connect.startOutboundVoiceContact(startOutboundVoiceContactRequest);
		return result.getContactId();
	}
}
