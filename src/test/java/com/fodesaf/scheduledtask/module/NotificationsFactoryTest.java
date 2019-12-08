package com.fodesaf.scheduledtask.module;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
class NotificationsFactoryTest {

	
	@Autowired
	NotificationFactory factory;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testNotificationCampaign1Load() {
		Notification notification = factory.getCaseService("CAMPAIGN1");
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}
	
	@Test
	void testSendNotificationCampaign1ByEmail() {
		Notification notification = factory.getCaseService("CAMPAIGN1");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Segregado", "000100575474001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testNotificationCampaign2Load() {
		Notification notification = factory.getCaseService("CAMPAIGN2");
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}

}
