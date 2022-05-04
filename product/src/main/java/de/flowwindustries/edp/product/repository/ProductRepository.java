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
     * Find product by identifier.
     * @param identifier
     * @return the product wrapped in an {@link Optional} or {@link Optional#empty()} if no product is found for the given identifier
     */
    Optional<Product> findByIdentifier(String identifier);

    /**
     * Delete product by identifier.
     * @param identifier the unique identifier of the product to delete
     */
    void deleteByIdentifier(String identifier);
}
