/**
 * 
 */
package com.fodesaf.scheduledtask.module.config.listeners;

import org.springframework.batch.core.ItemReadListener;

/**
 * @author geanque
 *
 */
public class StepItemReadListener implements ItemReadListener<String>{

	@Override
	public void beforeRead() {
		System.out.println("ItemReadListener - beforeRead");
		
	}

	@Override
	public void afterRead(String item) {
		System.out.println("ItemReadListener - afterRead");
		
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener - onReadError");
		
	}

}
