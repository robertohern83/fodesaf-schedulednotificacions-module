package com.fodesaf.scheduledtask.module.notifications;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;

@Service
public class SMSNotificationService {

	static AmazonSNS client = AmazonSNSClientBuilder.defaultClient();
	
	public static void main(String args[]) {
		
		SMSNotificationService service = new SMSNotificationService();
		service.sendSMSMessage("+50687307606", "Hola2", "in2clouds", MessageType.PROMOTIONAL);
	}
	
	public String sendSMSMessage(String phoneNumber, String message, String pSender, MessageType pType) {
		PublishRequest request = new PublishRequest();
		request.setPhoneNumber(phoneNumber);
		request.setMessage(message);
		MessageAttributeValue sender = new MessageAttributeValue();
		sender.setDataType("String");
		sender.setStringValue(pSender);
		request.addMessageAttributesEntry("AWS.SNS.SMS.SenderID", sender);
		
		MessageAttributeValue type = new MessageAttributeValue();
		type.setDataType("String");
		type.setStringValue(pType.getValue());
		request.addMessageAttributesEntry("AWS.SNS.SMS.SMSType", type);
		
		return client.publish(request).getMessageId();
	}
	
	public enum MessageType{
		
		PROMOTIONAL("Promotional"),
		TRANSACTIONAL("Transactional");
		
		private String value;
		
		private MessageType(String value) {
			this.value = value;
			
		}
		
		public String getValue() {
			return value;
		}
	}
}

