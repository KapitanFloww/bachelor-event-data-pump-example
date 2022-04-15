package de.flowwindustries.edp.product.controller.dto;

import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.domain.Status;
import lombok.Data;

/**
 * Data Transfer Object for {@link Product} entities.
 */
@Data
public class ProductDTO {
    private String name;
    private String category;
    private Double price;
    private Status status;
}
