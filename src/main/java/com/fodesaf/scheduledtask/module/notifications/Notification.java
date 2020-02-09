package com.fodesaf.scheduledtask.module.notifications;

import java.util.List;
import java.util.Map;

public interface Notification {
	
	static final int NO_CONTACT_INFO_ERROR = -100;
	
	static final int NOT_SUPPORTED_CHANNEL = -200;
	
    String sendNotification(Map<String, Object> notificationData, NotificationChannel channel) throws NotificationException;
    
    Integer getSupportedCampaign();
    
    List<NotificationChannel> getSupportedChannels();
}