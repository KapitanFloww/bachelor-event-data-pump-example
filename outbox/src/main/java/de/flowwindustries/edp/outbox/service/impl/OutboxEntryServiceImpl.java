package de.flowwindustries.edp.outbox.service.impl;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

/**
 * Event service to persist outgoing events in the OUTBOX table.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEntryServiceImpl implements OutboxEntryService {

    private final OutboxRepository outboxRepository;

    @Override
    @Transactional
    public OutboxEntry createOutboxEntry(String aggregateId, EventType eventType, Object payload) {
        log.debug("Creating outbox entry for aggregateId: {}, entryType: {}, with payload: {}", aggregateId, eventType, payload);
        String stringPayload = null;
        if(payload != null) {
            stringPayload = payload.toString();
        }
        OutboxEntry eventEntity = new OutboxEntry(null,
                UUID.randomUUID().toString(),
                OutboxEntryStatus.UNPROCESSED,
                aggregateId,
                eventType,
                stringPayload,
                Instant.now(Clock.systemUTC()));
        return outboxRepository.save(eventEntity);
    }

    @Override
    @Transactional
    public OutboxEntry processOutboxEntry(OutboxEntry entry) {
        log.debug("Processing outbox entry: {}", entry);
        entry.setOutboxEntryStatus(OutboxEntryStatus.PROCESSED);
        return outboxRepository.save(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OutboxEntry> findUnprocessedOutboxEntries() {
        log.debug("Request to find all outbox entries with status: {}", OutboxEntryStatus.UNPROCESSED);
        return outboxRepository.findAllByOutboxEntryStatusOrderByCreatedAtAsc(OutboxEntryStatus.UNPROCESSED);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<OutboxEntry> findAll() {
        log.debug("Request to find all outbox entries");
        return outboxRepository.findAll();
    }
}
