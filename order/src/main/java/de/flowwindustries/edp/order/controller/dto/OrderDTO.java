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

    private Double price;
    private Status status;
    private String holderId;
    private List<String> productIds;
}
