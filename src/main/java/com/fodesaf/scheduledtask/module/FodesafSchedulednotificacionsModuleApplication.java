package com.fodesaf.scheduledtask.module;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class FodesafSchedulednotificacionsModuleApplication {
	@Autowired
    JobLauncher jobLauncher;
     
    @Autowired
    Job job;
    
	public static void main(String[] args) {
		SpringApplication.run(FodesafSchedulednotificacionsModuleApplication.class, args);
	}

	@Scheduled(cron = "0 */20 8-15 * * MON-FRI")
	//@Scheduled(cron = "0 */2 8-23 * * *")
    public void perform() throws Exception  {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }


}
