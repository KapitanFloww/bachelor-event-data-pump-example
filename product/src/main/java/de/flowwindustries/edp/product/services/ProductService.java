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
     * @param productDTO
     * @return
     */
    Product createProduct(ProductDTO productDTO);

    /**
     * Get the user details for the given identifier
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     */
    Product getProduct(String identifier) throws IllegalArgumentException;

    /**
     * Update the user details for the given identifier
     * @param identifier
     * @param productDTO
     * @return
     * @throws IllegalArgumentException
     */
    Product updateProduct(String identifier, ProductDTO productDTO) throws IllegalArgumentException;

    /**
     * Get all users.
     * @return
     */
    Collection<Product> getAllProducts();

    /**
     * Delete user details
     * @param identifier
     */
    void deleteProduct(String identifier);
}
