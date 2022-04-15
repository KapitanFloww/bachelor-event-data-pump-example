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
     * @param entry
     * @return
     */
    Product putProduct(OutboxEntry entry);

    /**
     * Return all persisted {@link Product}s.
     * @return
     */
    Collection<Product> findAll();

    /**
     * Delete the {@link Product} associated to this {@link OutboxEntry}.
     * @param entry
     */
    void deleteProduct(OutboxEntry entry);

    /**
     * Get the product
     * @param identifier
     * @return
     */
    Product getProductSafe(String identifier);
}
