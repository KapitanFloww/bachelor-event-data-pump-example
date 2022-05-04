package de.flowwindustries.edp.outbox.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.UUID;

/**
 * Event service to persist outgoing events in the OUTBOX table.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxEntryServiceImpl implements OutboxEntryService {

    public static final String AGGREGATE_ID_INVALID = "AggregateId must not be null";
    private final OutboxRepository outboxRepository;
    private final ObjectMapper mapper;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public OutboxEntry createOutboxEntry(String aggregateId, EventType eventType, Object payload) {
        if(aggregateId == null || aggregateId.isBlank()) {
            throw new IllegalArgumentException(AGGREGATE_ID_INVALID);
        }
        if(payload == null) {
            return createOutboxEntryIntern(aggregateId, eventType, null);
        }
        JsonNode node = mapper.convertValue(payload, JsonNode.class);
        return createOutboxEntryIntern(aggregateId, eventType, node.toString());
    }

    private OutboxEntry createOutboxEntryIntern(String aggregateId, EventType eventType, String payload) {
        log.debug("Creating outbox entry for aggregateId: {}, entryType: {}, with payload: {}", aggregateId, eventType, payload);
        OutboxEntry eventEntity = new OutboxEntry(null,
                UUID.randomUUID().toString(),
                OutboxEntryStatus.UNPROCESSED,
                aggregateId,
                eventType,
                payload,
                Instant.now(Clock.systemUTC()).truncatedTo(ChronoUnit.MILLIS)
        );
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

    @Override
    @Transactional
    public void deleteProcessed() {
        log.debug("Clean-Up OUTBOX");
        outboxRepository.deleteAllByOutboxEntryStatus(OutboxEntryStatus.PROCESSED);
    }
}
