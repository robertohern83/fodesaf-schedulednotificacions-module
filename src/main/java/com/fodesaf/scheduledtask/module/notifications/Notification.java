package com.fodesaf.scheduledtask.module.notifications;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fodesaf.scheduledtask.module.model.Notificaciones;

public interface Notification {
	
	static final int NO_CONTACT_INFO_ERROR = -100;
	
	static final int NOT_SUPPORTED_CHANNEL = -200;
	
	static final Logger logger = LoggerFactory.getLogger(Notification.class);
	
    String sendNotification(Notificaciones notificacion, NotificationChannel channel) throws NotificationException;
    
    Integer getSupportedCampaign();
    
    List<NotificationChannel> getSupportedChannels();
}