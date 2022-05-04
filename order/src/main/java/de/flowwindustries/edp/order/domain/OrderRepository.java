package de.flowwindustries.edp.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Purchase} entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Purchase, Long> {

    /**
     * Find an order by the given identifier.
     * @param identifier the unique identifier of the order to fetch
     * @return the order wrapped in an {@link Optional} or {@link Optional#empty()} if no order is found for the given identifier
     */
    Optional<Purchase> findByIdentifier(String identifier);

    /**
     * Delete an order by the given identifier.
     * @param identifier the unique identifier of the order to delete
     */
    void deleteByIdentifier(String identifier);
}
