package de.flowwindustries.edp.order.controller.dto;

import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.order.domain.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderAggregate {

    private String identifier;
    private Double price;
    private Status status;

    private User holder;
    private List<Product> products;
}
