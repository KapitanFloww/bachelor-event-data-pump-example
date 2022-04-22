package de.flowwindustries.edp.outbox.scheduler;

import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.outbox.service.OutboxEntryServiceTest;
import de.flowwindustries.edp.outbox.OutboxTestApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = OutboxTestApplication.class)
public class OutboxPollingPublisherTest {

    @Value("${application.outbox.out-queue.name}")
    private String queueName;

    @Autowired
    private OutboxPollingPublisher pollingPublisher;
    @Autowired
    private OutboxRepository outboxRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        assertThat(pollingPublisher).isNotNull();
    }

    @Test
    void publishUnprocessedEntries_Should_PublishUnprocessedEntries() {
        //GIVEN
        outboxRepository.save(OutboxEntryServiceTest.getProcessedEntry());
        outboxRepository.save(OutboxEntryServiceTest.getUnprocessedEntry());
        //WHEN
        pollingPublisher.publishUnprocessedEntries();
        //THEN
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(OutboxEntry.class));
    }

    @Test
    void publishUnprocessedEntries_Should_PublishNoEntriesIfNonSaved() {
        //GIVEN
        //WHEN
        pollingPublisher.publishUnprocessedEntries();
        //THEN
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), any(OutboxEntry.class));
    }

    @Test
    void publishUnprocessedEntries_Should_PublishNoEntriesIfAllEntriesProcessed() {
        //GIVEN
        outboxRepository.save(OutboxEntryServiceTest.getProcessedEntry());
        outboxRepository.save(OutboxEntryServiceTest.getProcessedEntry());
        //WHEN
        pollingPublisher.publishUnprocessedEntries();
        //THEN
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), any(OutboxEntry.class));
    }

    @Test
    void publishUnprocessedEntries_Should_CleanupProcessedEntries() {
        //GIVEN
        outboxRepository.save(OutboxEntryServiceTest.getProcessedEntry());
        outboxRepository.save(OutboxEntryServiceTest.getProcessedEntry());
        long size = outboxRepository.count();
        //WHEN
        pollingPublisher.publishUnprocessedEntries();
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(0);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }
}
