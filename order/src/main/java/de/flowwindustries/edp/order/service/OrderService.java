package de.flowwindustries.edp.order.service;

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
     * @param orderDTO
     * @return
     */
    Purchase createOrder(OrderDTO orderDTO);

    /**
     * Get the order details for the given identifier
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     */
    Purchase getOrder(String identifier) throws IllegalArgumentException;

    /**
     * Update the order details for the given identifier
     * @param identifier
     * @param orderDTO
     * @return
     * @throws IllegalArgumentException
     */
    Purchase updateOrder(String identifier, OrderDTO orderDTO) throws IllegalArgumentException;

    /**
     * Get all orders.
     * @return
     */
    Collection<Purchase> getAllOrders();

    /**
     * Delete order details
     * @param identifier
     */
    void deleteOrder(String identifier);


    /**
     * Get the order aggregate containing details of order, user and product contexts.
     * @param identifier
     * @return
     */
    OrderAggregate getOrderAggregate(String identifier);
}
