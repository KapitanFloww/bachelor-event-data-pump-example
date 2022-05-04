package de.flowwindustries.edp.outbox.service;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Proxy class that invokes the {@link OutboxEntryService} with an already existing transaction.
 */
@Component
@RequiredArgsConstructor
public class MockCrudServiceProxy {

    private final OutboxEntryService outboxEntryService;

    /**
     * Create a transaction and then call {@link OutboxEntryService#createOutboxEntry(String, EventType, Object)}.
     */
    @Transactional
    public OutboxEntry createOutboxEntry(String aggregateId, EventType eventType, Object payload) {
        return outboxEntryService.createOutboxEntry(aggregateId, eventType, payload);
    }
}
