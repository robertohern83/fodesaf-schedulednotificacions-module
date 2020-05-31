package com.fodesaf.scheduledtask.module;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
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
		Notification notification = factory.getCaseService(1);
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}
	
	@Test
	void testSendNotificationCampaign1ByEmailAttemp1() throws NotificationException {
		Notification notification = factory.getCaseService(1);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com,roberto.hernandez@in2cloudsconsulting.com");
		patrono.setCuotasAlCobro(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Segregado", "000107050876001001");
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign1ByEmailAttemp2()  throws NotificationException {
		Notification notification = factory.getCaseService(1);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setCuotasAlCobro(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign1BySMS()  throws NotificationException {
		Notification notification = factory.getCaseService(1);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00100575474");
		patrono.setSegregado("000100575474001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2BySMS() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2ByEmail() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign2ByVoice() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notification.sendNotification(notificationData, NotificationChannel.VOICE);
	}
	
	@Test
	void testNotificationCampaign2Load() {
		Notification notification = factory.getCaseService(2);
		System.out.println(notification);
		Assert.assertNotNull(notification);
	}
	
	@Test
	void testSendNotificationCampaign3ByEmailAttemp1() throws NotificationException  {
		Notification notification = factory.getCaseService(3);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3ByEmailAttemp2() throws NotificationException  {
		Notification notification = factory.getCaseService(3);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		notificationData.put("Attemp", 2);
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3BySMS()  throws NotificationException {
		Notification notification = factory.getCaseService(3);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		notificationData.put("Telefono", "87307606");
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp1()  throws NotificationException {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp2() throws NotificationException  {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 2);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4ByEmail() throws NotificationException  {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Consecutive", "9999");
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp1() throws NotificationException  {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		
		notificationData.put("Patrono", patrono);
		notificationData.put("Attemp", 1);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp2() throws NotificationException {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		notificationData.put("Attemp", 2);
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5ByEmail() throws NotificationException {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		Map<String, Object> notificationData = new HashMap<>();
		
		notificationData.put("Attemp", 1);
		Patronos patrono = new Patronos();
		
		patrono.setCedula("00107050876");
		patrono.setSegregado("000107050876001001");
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		notificationData.put("Consecutive", "9999");
		notificationData.put("Patrono", patrono);
		
		notification.sendNotification(notificationData, NotificationChannel.EMAIL);
	}

}
