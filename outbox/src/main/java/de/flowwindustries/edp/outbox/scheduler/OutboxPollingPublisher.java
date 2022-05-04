package de.flowwindustries.edp.outbox.scheduler;

import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPollingPublisher {

    @Value("${application.outbox.out-queue.name}")
    private String outboxQueueName;

    private final OutboxEntryService outboxEntryService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Poll the {@code OUTBOX}-Table and publish all {@link OutboxEntryStatus#UNPROCESSED} entries.
     * Delay between polls can be set with {@code application.outbox.polling-delay} property (default: 10.000 = 10s).
     * Initial delay can be set with {@code application.outbox.polling-initial-delay} property (default: 10.000 = 10s).
     */
    @Scheduled(fixedDelayString = "${application.outbox.polling.delay}", initialDelayString = "${application.outbox.polling.initial-delay}")
    public void publishUnprocessedEntries() {
        cleanOutbox();

        log.debug("Publishing all unprocessed entries");
        outboxEntryService.findUnprocessedOutboxEntries()
                .forEach( entry -> {
                    log.debug("Publishing entryId: {} for aggregate: {}({})", entry.getEventId(), entry.getAggregateId(), entry.getEventType());
                    rabbitTemplate.convertAndSend(outboxQueueName, entry);
                    outboxEntryService.processOutboxEntry(entry); 
                });
    }

    /**
     * Initialize outgoing queue bean.
     * @return the outgoing queue bean
     */
    @Bean
    public Queue outboxQueue() {
        return new Queue(outboxQueueName, true);
    }

    /**
     * Clean-Up the OUTBOX by deleting processed entries.
     */
    private void cleanOutbox() {
        outboxEntryService.deleteProcessed();
    }
}
