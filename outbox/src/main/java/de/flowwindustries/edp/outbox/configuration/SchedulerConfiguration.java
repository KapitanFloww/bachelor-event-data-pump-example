package de.flowwindustries.edp.outbox.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "application.outbox.polling.enabled", havingValue = "true")
public class SchedulerConfiguration {
}
