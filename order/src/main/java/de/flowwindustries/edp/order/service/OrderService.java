package de.flowwindustries.edp.order.service;

import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.order.controller.dto.OrderAggregate;
import de.flowwindustries.edp.order.controller.dto.OrderDTO;
import de.flowwindustries.edp.order.domain.Purchase;

import java.util.Collection;

/**
 * Service interface for {@link Purchase} entities.
 */
public interface OrderService {

    /**
     * Create a new user.
     * @param orderDTO data-transfer-object containing the order details
     * @return the persisted order entity
     */
    Purchase createOrder(OrderDTO orderDTO);

    /**
     * Get the order details for the given identifier.
     * @param identifier unique identifier of the order to fetch
     * @return the requested order
     * @throws IllegalArgumentException if no order is found for the given identifier
     */
    Purchase getOrder(String identifier) throws IllegalArgumentException;

    /**
     * Update the order details for the given identifier.
     * @param identifier unique identifier of the order to update
     * @param orderDTO data-transfer-object containing the updated details
     * @return the updated order
     * @throws IllegalArgumentException if no order is found for the given identifier
     */
    Purchase updateOrder(String identifier, OrderDTO orderDTO) throws IllegalArgumentException;

    /**
     * Get all orders.
     * @return all persisted orders
     */
    Collection<Purchase> getAllOrders();

    /**
     * Delete order details.
     * @param identifier unique identifier of the order to delete
     */
    void deleteOrder(String identifier);


    /**
     * Get the order aggregate containing details of order, user and product contexts.
     * @param identifier unique identifier of the order to fetch
     * @return an {@link OrderAggregate} containing aggregated information about an {@link Purchase},
     * along with the referenced {@link User} and {@link Product}s
     */
    OrderAggregate getOrderAggregate(String identifier);
}
