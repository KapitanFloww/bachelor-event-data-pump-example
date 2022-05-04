package de.flowwindustries.edp.outbox.repository;

import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for {@link OutboxEntry}'s.
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxEntry, Long> {

    /**
     * Find an entry by its unique id.
     * @param eventId the unique id of the event / message / outbox entry
     * @return the {@link OutboxEntry} wrapped inside an {@link Optional}
     * or {@link Optional#empty()} if no entry for given identifier found
     */
    Optional<OutboxEntry> findByEventId(String eventId);

    /**
     * Find all entities with the given status.
     * @param status the status of the entries to be searched for
     * @return a collection of outbox entries with the given status
     */
    Collection<OutboxEntry> findAllByOutboxEntryStatusOrderByCreatedAtAsc(OutboxEntryStatus status);

    /**
     * Delete all with specific status.
     * @param status the status of the entries that are to be deleted
     */
    void deleteAllByOutboxEntryStatus(OutboxEntryStatus status);
}
