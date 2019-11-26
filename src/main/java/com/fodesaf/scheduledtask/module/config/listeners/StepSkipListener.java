/**
 * 
 */
package com.fodesaf.scheduledtask.module.config.listeners;

import org.springframework.batch.core.SkipListener;

/**
 * @author geanque
 *
 */
public class StepSkipListener implements SkipListener<String, Number>{
	@Override
    public void onSkipInRead(Throwable t) {
        System.out.println("StepSkipListener - onSkipInRead");
    }
 
    @Override
    public void onSkipInWrite(Number item, Throwable t) {
        System.out.println("StepSkipListener - onSkipInWrite");
    }
 
    @Override
    public void onSkipInProcess(String item, Throwable t) {
        System.out.println("StepSkipListener - onSkipInProcess");
    }

}
