/**
 * 
 */
package com.fodesaf.scheduledtask.module.config.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author geanque
 *
 */
public class JobResultListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Called beforeJob().");
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("Called afterJob().");
		
	}

}
