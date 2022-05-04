package de.flowwindustries.edp.product.controller.dto;

import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.domain.Status;
import lombok.Data;

/**
 * Data Transfer Object for {@link Product} entities.
 */
@Data
public class ProductDTO {

    /**
     * Product name.
     */
    private String name;

    /**
     * Product category.
     */
    private String category;

    /**
     * Product price.
     */
    private Double price;

    /**
     * Product status.
     */
    private Status status;
}
