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
     * Create a new entry in the transactional OUTBOX.
     * Supports a current transaction - thus it is required to call this method inside an already existing transaction!
     * @param aggregateId the unique identifier of the referenced entity
     * @param eventType the type of state change that has occured to the referenced entity (i.e. Creation, Update or Deletion)
     * @param payload the referenced entity
     * @return the persisted outbox entry
     */
    OutboxEntry createOutboxEntry(String aggregateId, EventType eventType, Object payload);

    /**
     * Process an outbox entry. This will change the entries status to {@link OutboxEntryStatus#PROCESSED}.
     * @param entry the outbox entry to process
     * @return the processed outbox entry
     */
    OutboxEntry processOutboxEntry(OutboxEntry entry);

    /**
     * Find all entries with {@link OutboxEntryStatus#UNPROCESSED}.
     * @return a collection of unprocessed outbox entries
     */
    Collection<OutboxEntry> findUnprocessedOutboxEntries();

    /**
     * Find all {@link OutboxEntry}s.
     * @return all persisted outbox entries
     */
    Collection<OutboxEntry> findAll();

    /**
     * Clean-up the OUTBOX by deleting PROCESSED entries.
     */
    void deleteProcessed();
}
