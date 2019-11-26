/**
 * 
 */
package com.fodesaf.scheduledtask.module.config.listeners;

import org.springframework.batch.core.ItemProcessListener;

/**
 * @author geanque
 *
 */
public class StepItemProcessListener implements ItemProcessListener<String, Number>{

	@Override
	public void beforeProcess(String item) {
		System.out.println("ItemProcessListener - beforeProcess");
		
	}

	@Override
	public void afterProcess(String item, Number result) {
		System.out.println("ItemProcessListener - afterProcess");
		
	}

	@Override
	public void onProcessError(String item, Exception e) {
		System.out.println("ItemProcessListener - onProcessError");
		
	}

}
