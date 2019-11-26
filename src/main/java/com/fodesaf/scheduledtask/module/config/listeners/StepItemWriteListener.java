/**
 * 
 */
package com.fodesaf.scheduledtask.module.config.listeners;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

/**
 * @author geanque
 *
 */
public class StepItemWriteListener implements ItemWriteListener<Number>{
	
	@Override
    public void beforeWrite(List<? extends Number> items) {
        System.out.println("ItemWriteListener - beforeWrite");
    }
 
    @Override
    public void afterWrite(List<? extends Number> items) {
        System.out.println("ItemWriteListener - afterWrite");
    }
 
    @Override
    public void onWriteError(Exception exception, List<? extends Number> items) {
        System.out.println("ItemWriteListener - onWriteError");
    }
}
