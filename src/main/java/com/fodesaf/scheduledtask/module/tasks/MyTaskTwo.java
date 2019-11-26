/**
 * 
 */
package com.fodesaf.scheduledtask.module.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author geanque
 *
 */
public class MyTaskTwo implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("MyTaskTwo start..");
		 
        // ... your code
         
        System.out.println("MyTaskTwo done..");
        return RepeatStatus.FINISHED;
	}

}
