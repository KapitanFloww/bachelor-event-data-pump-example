package de.flowwindustries.edp.outbox.service;


import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;

import java.util.Collection;

/**
 * Service interface for the transactional OUTBOX service.
 */
public interface OutboxEntryService {

    /**
     * Create a new entry in the transactional OUTBOX
     * @param aggregateId
     * @param eventType
     * @param payload
     * @return
     */
    OutboxEntry createOutboxEntry(String aggregateId, EventType eventType, Object payload);

    /**
     * Process an outbox entry. This will change the entries status to {@link OutboxEntryStatus#PROCESSED}.
     * @param entry
     * @return
     */
    OutboxEntry processOutboxEntry(OutboxEntry entry);

    /**
     * Find all entries with {@link OutboxEntryStatus#UNPROCESSED}.
     * @return
     */
    Collection<OutboxEntry> findUnprocessedOutboxEntries();

    /**
     * Find all {@link OutboxEntry}.
     * @return
     */
    Collection<OutboxEntry> findAll();
}
