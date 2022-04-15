package de.flowwindustries.edp.outbox.scheduler;

import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class OutboxPollingPublisher {

    @Value("${application.outbox.out-queue.name}")
    private String outboxQueue;

    private final OutboxEntryService outboxEntryService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Poll the {@code OUTBOX}-Table and publish all {@link OutboxEntryStatus#UNPROCESSED} entries.
     * Delay between polls can be set with {@code application.outbox.polling-delay} property (default: 10.000 = 10s).
     * Initial delay can be set with {@code application.outbox.polling-initial-delay} property (default: 10.000 = 10s).
     */
    @Scheduled(fixedDelayString = "${application.outbox.polling-delay}", initialDelayString = "${application.outbox.polling-initial-delay}")
    public void publishUnprocessedEvents() {
        log.debug("Publishing all unprocessed entries");
        outboxEntryService.findUnprocessedOutboxEntries()
                .forEach( entry -> {
                    log.debug("Publishing entryId: {} for aggregate: {}({})", entry.getEventId(), entry.getAggregateId(), entry.getEventType());
                    rabbitTemplate.convertAndSend(outboxQueue, entry);
                    outboxEntryService.processOutboxEntry(entry); 
                });
    }

    @Bean
    public Queue outboxQueue() {
        return new Queue(outboxQueue, false);
    }
}
