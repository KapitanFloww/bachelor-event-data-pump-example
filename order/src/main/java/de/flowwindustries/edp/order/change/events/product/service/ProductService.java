package de.flowwindustries.edp.order.change.events.product.service;

import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;

import java.util.Collection;

/**
 * Service interface to update the user data replica, known as {@link Product} in this context.
 */
public interface ProductService {

    /**
     * Create or update a {@link Product} for this {@link OutboxEntry}.
     * @param entry {@link OutboxEntry} containing the product to create or update
     */
    void putProduct(OutboxEntry entry);

    /**
     * Return all persisted {@link Product}s.
     * @return all persisted products
     */
    Collection<Product> findAll();

    /**
     * Delete the {@link Product} associated to this {@link OutboxEntry}.
     * @param entry {@link OutboxEntry} containing the identifier of the product to delete ({@link OutboxEntry#getAggregateId()}
     */
    void deleteProduct(OutboxEntry entry);

    /**
     * Get the product.
     * @param identifier identifier of the product to fetch
     * @return the requested product
     * @throws IllegalArgumentException if no product can be found with the given identifier
     */
    Product getProductSafe(String identifier) throws IllegalArgumentException;
}
