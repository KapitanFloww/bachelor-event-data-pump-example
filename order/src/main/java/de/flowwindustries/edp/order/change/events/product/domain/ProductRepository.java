package de.flowwindustries.edp.order.change.events.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link Product} entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find {@link Product} by given identifier.
     * @param identifier
     * @return
     */
    Optional<Product> findByIdentifier(String identifier);

    /**
     * Delete {@link Product} by given identifier.
     * @param identifier
     */
    void deleteByIdentifier(String identifier);
}
