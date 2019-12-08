package com.fodesaf.scheduledtask.module.notifications;

import java.util.List;
import java.util.Map;

public interface Notification {
    String sendNotification(Map<String, Object> notificationData, NotificationChannel channel);
    
    String getSupportedCampaign();
    
    List<NotificationChannel> getSupportedChannels();
}