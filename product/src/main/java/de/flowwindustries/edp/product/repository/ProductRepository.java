package de.flowwindustries.edp.product.repository;

import de.flowwindustries.edp.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Product} de.flowwindustries.edp.product.repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find user by identifier.
     * @param identifier
     * @return
     */
    Optional<Product> findByIdentifier(String identifier);

    /**
     * Delete user by identifier.
     * @param identifier
     */
    void deleteByIdentifier(String identifier);
}
