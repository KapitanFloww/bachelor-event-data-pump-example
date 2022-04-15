package de.flowwindustries.edp.outbox.repository;

import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Repository for {@link OutboxEntry}'s.
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxEntry, Long> {

    /**
     * Find all entities with the given status.
     * @param status
     * @return
     */
    Collection<OutboxEntry> findAllByOutboxEntryStatusOrderByCreatedAtAsc(OutboxEntryStatus status);
}
