package com.fodesaf.scheduledtask.module.notifications.campaign;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;

@Service
public class NotificationCampaign2 implements Notification {

	@Override
	public String sendNotification(Map<String, Object> reportData, NotificationChannel channel) {
		// TODO Auto-generated method stub
		return "Not implemented yet";
	}

	@Override
	public String getSupportedCampaign() {
		return "CAMPAIGN2";
	}

	@Override
	public List<NotificationChannel> getSupportedChannels() {
		return Arrays.asList(NotificationChannel.EMAIL, NotificationChannel.SMS);
	}

}
