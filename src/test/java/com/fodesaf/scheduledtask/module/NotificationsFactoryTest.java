package com.fodesaf.scheduledtask.module;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fodesaf.scheduledtask.module.model.Campanas;
import com.fodesaf.scheduledtask.module.model.Notificaciones;
import com.fodesaf.scheduledtask.module.model.NotificacionesPK;
import com.fodesaf.scheduledtask.module.model.Patronos;
import com.fodesaf.scheduledtask.module.model.Segmentacion;
import com.fodesaf.scheduledtask.module.notifications.Notification;
import com.fodesaf.scheduledtask.module.notifications.NotificationChannel;
import com.fodesaf.scheduledtask.module.notifications.NotificationException;
import com.fodesaf.scheduledtask.module.notifications.NotificationFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
class NotificationsFactoryTest {

	
	@Autowired
	NotificationFactory factory;
	
	private String segregado = "000105680556001001";
	private String cedula = "00105680556";
	private int campana = 135;
	private int segmento = 82;
	
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
		
		
		Patronos patrono = new Patronos();
		
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com,roberto.hernandez@in2cloudsconsulting.com");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.EMAIL);
	}
	
	
	@Test
	void testSendNotificationCampaign1ByEmailAttemp2()  throws NotificationException {
		Notification notification = factory.getCaseService(1);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		
		notification.sendNotification(newNotificaciones(patrono,2), NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign1BySMS()  throws NotificationException {
		Notification notification = factory.getCaseService(1);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setTelefonoRepresentanteLegal("87307606");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,2), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2BySMS() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setCuotasAlCobro(123.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign2ByEmail() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign2ByVoice() throws NotificationException  {
		Notification notification = factory.getCaseService(2);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setTelefonoRepresentanteLegal("88854992");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.VOICE);
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
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3ByEmailAttemp2() throws NotificationException  {
		Notification notification = factory.getCaseService(3);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		notification.sendNotification(newNotificaciones(patrono,2), NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign3BySMS()  throws NotificationException {
		Notification notification = factory.getCaseService(3);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setCuotasAlCobro(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp1()  throws NotificationException {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4BySMSAttemp2() throws NotificationException  {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,2), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign4ByEmail() throws NotificationException  {
		Notification notification = factory.getCaseService(4);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.EMAIL);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp1() throws NotificationException  {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5BySMSAttemp2() throws NotificationException {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		notification.sendNotification(newNotificaciones(patrono,2), NotificationChannel.SMS);
	}
	
	@Test
	void testSendNotificationCampaign5ByEmail() throws NotificationException {
		Notification notification = factory.getCaseService(5);
		Assert.assertNotNull(notification);
		
		
		Patronos patrono = new Patronos();
		
		patrono.setCedula(this.cedula);
		patrono.setSegregado(this.segregado);
		patrono.setCorreo("consultorias.rhm@gmail.com");
		patrono.setTelefono("87307606");
		patrono.setDeudaTotal(12345.45);
		patrono.setNombre("NOMBRE DE PRUEBA");
		
		
		notification.sendNotification(newNotificaciones(patrono,1), NotificationChannel.EMAIL);
	}
	
	
	private Notificaciones newNotificaciones(Patronos patrono, int attemp) {
		Notificaciones notificacion = new Notificaciones();
		
		NotificacionesPK primaryKey = new NotificacionesPK();
		Campanas campana = new Campanas();
		campana.setId(this.campana);
		primaryKey.setCampana(campana);
		primaryKey.setCanal("EMAIL");
		primaryKey.setCedula(this.cedula);
		Segmentacion segmento = new Segmentacion();
		segmento.setId(this.segmento);
		primaryKey.setSegmento(segmento);
		primaryKey.setPatrono(patrono);
		primaryKey.setIntento(attemp);
		notificacion.setPrimaryKey(primaryKey);
		notificacion.setEstatus("ENVIDADA");
		notificacion.setFechaCreacion(new Date());
		notificacion.setFechaEnvio(new Date());
		notificacion.setMessageId("12345678");
		
		
		return notificacion;
	}


}
