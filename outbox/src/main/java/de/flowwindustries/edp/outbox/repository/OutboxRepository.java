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
     * @param eventId
     * @return
     */
    Optional<OutboxEntry> findByEventId(String eventId);

    /**
     * Find all entities with the given status.
     * @param status
     * @return
     */
    Collection<OutboxEntry> findAllByOutboxEntryStatusOrderByCreatedAtAsc(OutboxEntryStatus status);

    /**
     * Delete all with specific status.
     * @param status
     */
    void deleteAllByOutboxEntryStatus(OutboxEntryStatus status);
}
