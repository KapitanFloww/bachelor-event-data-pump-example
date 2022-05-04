package de.flowwindustries.edp.order.controller.dto;

import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.order.domain.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data-Structure for aggregated orders.
 * It contains the full information about an order, it's referenced user and products.
 * To accomplish this, this data-structure uses the data replicas.
 */
@Data
@Builder
public class OrderAggregate {

    /**
     * Order identifier.
     */
    private String identifier;

    /**
     * Order price.
     */
    private Double price;

    /**
     * Order status.
     */
    private Status status;

    /**
     * Referenced user replica.
     */
    private User holder;

    /**
     * Referenced product replicas.
     */
    private List<Product> products;
}
