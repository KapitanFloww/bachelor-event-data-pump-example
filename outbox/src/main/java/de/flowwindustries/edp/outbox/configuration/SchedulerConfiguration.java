package de.flowwindustries.edp.outbox.configuration;

import de.flowwindustries.edp.outbox.scheduler.OutboxPollingPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Enable schedulers used for the {@link OutboxPollingPublisher}
 * when property {@code application.outbox.polling.enabled} is set to {@code true}.
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = "application.outbox.polling.enabled", havingValue = "true")
public class SchedulerConfiguration {
}
