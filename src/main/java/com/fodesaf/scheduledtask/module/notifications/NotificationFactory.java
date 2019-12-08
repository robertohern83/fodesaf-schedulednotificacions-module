package com.fodesaf.scheduledtask.module.notifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactory {
	
	private final Map<String, Notification> notificationServices = new HashMap<>();

	  @Autowired
	  public NotificationFactory(List<Notification> services){
	    for (Notification service: services){
	      register(service.getSupportedCampaign(), service);
	    }
	  }

	  public void register(String campaign, Notification service) {
	    this.notificationServices.put(campaign, service);
	  }

	  public Notification getCaseService(String campaign){
	    return this.notificationServices.get(campaign);
	  }

}
