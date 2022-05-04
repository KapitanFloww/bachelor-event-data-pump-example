package de.flowwindustries.edp.order.controller.dto;

import de.flowwindustries.edp.order.domain.Purchase;
import de.flowwindustries.edp.order.domain.Status;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for {@link Purchase} entities.
 */
@Data
public class OrderDTO {

    /**
     * Order price.
     */
    private Double price;

    /**
     * Order status.
     */
    private Status status;

    /**
     * Referenced user identifier.
     */
    private String holderId;

    /**
     * Referenced product identifiers.
     */
    private List<String> productIds;
}
