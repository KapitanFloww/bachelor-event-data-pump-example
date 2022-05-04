package de.flowwindustries.edp.product.services;

import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.controller.dto.ProductDTO;

import java.util.Collection;

/**
 * Service interface for CRUD operations on {@link Product} entities.
 */
public interface ProductService {

    /**
     * Create a new user.
     * @param productDTO data-transfer-object containing the product details
     * @return the persisted product entity
     */
    Product createProduct(ProductDTO productDTO);

    /**
     * Get the product details for the given identifier
     * @param identifier unique identifier of the product to fetch
     * @return the requested product
     * @throws IllegalArgumentException if no product is found for the given identifier
     */
    Product getProduct(String identifier) throws IllegalArgumentException;

    /**
     * Update the product details for the given identifier.
     * @param identifier unique identifier of the product to update
     * @param productDTO data-transfer-object containing the updated details
     * @return the updated product
     * @throws IllegalArgumentException if no product is found for the given identifier
     */
    Product updateProduct(String identifier, ProductDTO productDTO) throws IllegalArgumentException;

    /**
     * Get all products.
     * @return all persisted products.
     */
    Collection<Product> getAllProducts();

    /**
     * Delete product details.
     * @param identifier unique identifier of the product to delete
     */
    void deleteProduct(String identifier);
}
