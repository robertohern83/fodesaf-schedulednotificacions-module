package com.fodesaf.scheduledtask.module.notifications;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailNotificationServiceTest {

	@Autowired
	EmailNotificationService service;
	
	@Test
	public void sendEmailNotificationWithDefaultSenderTest() {
		service.sendEmailNotification("roberto.hernandez@in2cloudsconsulting.com",
				"Prueba envio con copia", 
				"Hola usuario", 
				"Hola Usuario", 
				"roberto.hern83@gmail.com", 
				null, 
				null, 
				null);
	}

}
