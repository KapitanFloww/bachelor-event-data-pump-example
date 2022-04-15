package de.flowwindustries.edp.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Purchase} entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByIdentifier(String identifier);

    void deleteByIdentifier(String identifier);
}
