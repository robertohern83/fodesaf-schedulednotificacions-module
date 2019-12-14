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
		Notification notification = factory.getCaseService("Notif. arreglos de pago cuotas atrasadas en el CJ-CA");
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}
	
	@Test
	void testSendNotificationCampaign1ByEmailAttemp1() {
		Notification notification = factory.getCaseService("Notif. arreglos de pago cuotas atrasadas en el CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotasAlCobro", 12345.45);
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign1ByEmailAttemp2() {
		Notification notification = factory.getCaseService("Notif. arreglos de pago cuotas atrasadas en el CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotasAlCobro", 12345.45);
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign1BySMS() {
		Notification notification = factory.getCaseService("Notif. arreglos de pago cuotas atrasadas en el CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotasAlCobro", 12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2BySMS() {
		Notification notification = factory.getCaseService("Notif. Periodos CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Cedula", "00107050876");
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Telefono", "87307606");
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2ByEmail() {
		Notification notification = factory.getCaseService("Notif. Periodos CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Cedula", "00107050876");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("DeudaTotal", 12345.45);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign2ByVoice() {
		Notification notification = factory.getCaseService("Notif. Periodos CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Cedula", "00107050876");
		notificationData.put("DeudaTotal", 12345345.45);
		notificationData.put("Telefono", "87307606");
		
		notification.sendNotification(notificationData, NotificationChannel.VOICE);
	}
	
	@Test
	void testNotificationCampaign2Load() {
		Notification notification = factory.getCaseService("Notif. Periodos CJ-CA");
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}
	
	@Test
	void testSendNotificationCampaign3ByEmailAttemp1() {
		Notification notification = factory.getCaseService("Record. Cuota Arreglo CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotaAlCobro", 10000.45);
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3ByEmailAttemp2() {
		Notification notification = factory.getCaseService("Record. Cuota Arreglo CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotaAlCobro", 11111.45);
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3BySMS() {
		Notification notification = factory.getCaseService("Record. Cuota Arreglo CJ-CA");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("CuotaAlCobro", 12345.45);
		notificationData.put("Telefono", "87307606");
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp1() {
		Notification notification = factory.getCaseService("Preven. de arreglos de pagos cuotas de atraso");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp2() {
		Notification notification = factory.getCaseService("Preven. de arreglos de pagos cuotas de atraso");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4ByEmail() {
		Notification notification = factory.getCaseService("Preven. de arreglos de pagos cuotas de atraso");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Consecutive", "9999");
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp1() {
		Notification notification = factory.getCaseService("Preven. solo Periodos");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp2() {
		Notification notification = factory.getCaseService("Preven. solo Periodos");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5ByEmail() {
		Notification notification = factory.getCaseService("Preven. solo Periodos");
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Attemp", 1);
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Correo", "consultorias.rhm@gmail.com");
		notificationData.put("DeudaTotal", 12345.45);
		notificationData.put("Consecutive", "9999");
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}

}
