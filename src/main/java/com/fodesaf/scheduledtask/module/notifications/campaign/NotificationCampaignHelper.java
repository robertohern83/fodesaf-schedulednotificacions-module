package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;

import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService;
import com.fodesaf.scheduledtask.module.notifications.SMSNotificationService.MessageType;
import com.fodesaf.scheduledtask.module.service.PatronosService;

public class NotificationCampaignHelper {

	public static void iterateOverPhonesAndInvokeFunction(
			Patronos patrono, 
			Consumer<String> functionOverPhones, 
			Function<Patronos, Set<String>> getPhonesFunction,
			Logger logger, 
			int supportedCampaign) throws NotificationException{
		
		Set<String> phoneList = getPhonesFunction.apply(patrono);
		
		if(null != phoneList && 0 < phoneList.size()) {
			phoneList.forEach(functionOverPhones);
		}
		else {
			logger.error(String.format("Campaña %s, Telefono a notificar no encontrado, segregado: %s", 
					supportedCampaign, patrono.getSegregado()));
			
			throw new NotificationException(String.format("Campaña %s, Telefono a notificar no encontrado, segregado: %s", 
					supportedCampaign, patrono.getSegregado()), 
					Notification.NO_CONTACT_INFO_ERROR);
		}
	}
	
	public static Function<Patronos, Set<String>> getPhonesFilter(PatronosService patronosService, Patronos patrono, boolean smsCompatible) {
		return phones -> patronosService.getEmployerPhoneList(patrono, smsCompatible);
	}


	public static Consumer<String> buildSMSMessagesConsumer(SMSNotificationService smsService, String smsSender, final List<String> messageIds, Patronos patrono, String message) {
		return phone ->  messageIds.add(smsService.sendSMSMessage(
											phone, 
											message, 
											smsSender, 
											MessageType.PROMOTIONAL));
	}
}
