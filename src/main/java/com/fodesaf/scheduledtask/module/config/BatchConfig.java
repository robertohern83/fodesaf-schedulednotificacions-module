/**
 * 
 */
package com.fodesaf.scheduledtask.module.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fodesaf.scheduledtask.module.model.repositories.CampanaCanalesRepository;
import com.fodesaf.scheduledtask.module.model.repositories.NotificacionesRepository;
/**
 * @author geanque
 *
 */

@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackages = "com.fodesaf.scheduledtask.module.model.repositories")
@EntityScan(basePackages = "com.fodesaf.scheduledtask.module.model")

public class BatchConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Autowired
	private NotificacionesRepository notificacionesRepository;

	@Autowired
	private CampanaCanalesRepository campanaCanalesRepository;
	
	@Value("test.csv")
    private Resource inputResource;

	@Bean
    protected Step readLines() {
        return steps
          .get("readLines")
          .tasklet(new NotificationsReader(notificacionesRepository))
          .build();
    }
	
	@Bean
    protected Step processLines() {
        return steps
          .get("processLines")
          .tasklet(new NotificationsProcessor(notificacionesRepository, campanaCanalesRepository))
          .build();
    }
	
	@Bean
    protected Step writeLines() {
        return steps
          .get("writeLines")
          .tasklet(new NotificationsWriter(notificacionesRepository))
          .build();
    }
	
	@Bean
    public Job job() {
        return jobs
          .get("taskletsJob")
          .start(readLines())
          .next(processLines())
          .next(writeLines())
          .build();
    }

	 
}
