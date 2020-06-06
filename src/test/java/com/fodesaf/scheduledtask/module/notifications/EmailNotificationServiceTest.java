package com.fodesaf.scheduledtask.module.notifications;

import java.util.ArrayList;
import java.util.List;

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
		
		List<String> testEmails = new ArrayList<String>();
		
		testEmails.add("roberto.hern83@gmail.com");
		testEmails.add("roberto.hernandez@in2cloudsconsulting.com");
		
		service.sendEmailNotification("roberto.hernandez@in2cloudsconsulting.com",
				"Prueba envio con copia", 
				"Hola usuario", 
				"Hola Usuario", 
				testEmails, 
				null, 
				null, 
				null);
	}

}
