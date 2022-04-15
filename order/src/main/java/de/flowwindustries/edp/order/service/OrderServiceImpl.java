package de.flowwindustries.edp.order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.product.service.ProductService;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.order.change.events.user.service.UserService;
import de.flowwindustries.edp.order.controller.dto.OrderAggregate;
import de.flowwindustries.edp.order.controller.dto.OrderDTO;
import de.flowwindustries.edp.order.domain.OrderRepository;
import de.flowwindustries.edp.order.domain.Purchase;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation of {@link OrderService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_NOT_FOUND = "Order with identifier %s not found";

    /**
     * Outbox Entry Types
     */

    private final ObjectMapper mapper;
    private final OrderRepository orderRepository;
    private final OutboxEntryService outboxEntryService;
    private final ProductService productService;
    private final UserService userService;

    @Override
    @Transactional
    public Purchase createOrder(OrderDTO orderDTO) {
        Purchase purchase = new Purchase();
        purchase.setIdentifier(UUID.randomUUID().toString());

        purchase.setPrice(orderDTO.getPrice());
        purchase.setHolderId(orderDTO.getHolderId());
        purchase.setProductIds(orderDTO.getProductIds());
        purchase.setStatus(orderDTO.getStatus());

        purchase = orderRepository.save(purchase);
        log.info("Created new order: {}", purchase);

        //Persist outbox entry
        createOutboxEntry(purchase, EventType.CREATION);

        return purchase;
    }

    @Override
    @Transactional(readOnly = true)
    public Purchase getOrder(String identifier) throws IllegalArgumentException {
        log.info("Request to get order with identifier: {}", identifier);
        return orderRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format(ORDER_NOT_FOUND, identifier)));
    }

    @Override
    @Transactional
    public Purchase updateOrder(String identifier, OrderDTO orderDTO) throws IllegalArgumentException {
        log.info("Request to update order with identifier: {} to {}", identifier, orderDTO);
        Purchase purchase = getOrder(identifier);

        purchase.setPrice(orderDTO.getPrice());
        purchase.setHolderId(orderDTO.getHolderId());
        purchase.setProductIds(orderDTO.getProductIds());
        purchase.setStatus(orderDTO.getStatus());

        purchase = orderRepository.save(purchase);

        log.info("Updated order: {}", identifier);

        //Persist outbox entry
        createOutboxEntry(purchase, EventType.UPDATE);

        return purchase;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Purchase> getAllOrders() {
        log.debug("Request to return all orders");
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOrder(String identifier) {
        log.info("Deleting order: {}", identifier);
        orderRepository.deleteByIdentifier(identifier);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(identifier, EventType.DELETION, null);
    }

    @Override
    @Transactional
    public OrderAggregate getOrderAggregate(String identifier) {
        log.info("Request to get order aggregate for: {}", identifier);
        Purchase purchase = getOrder(identifier);
        User holder = userService.getUserSafe(purchase.getHolderId());
        List<Product> products = purchase.getProductIds().stream()
                .map(productService::getProductSafe)
                .toList();

        //Aggregate the results and return
        return OrderAggregate.builder()
                .identifier(purchase.getIdentifier())
                .price(purchase.getPrice())
                .status(purchase.getStatus())
                .holder(holder)
                .products(products)
                .build();
    }

    private void createOutboxEntry(Purchase purchase, EventType eventType) {
        JsonNode node = mapper.convertValue(purchase, JsonNode.class);
        outboxEntryService.createOutboxEntry(purchase.getIdentifier(), eventType, node.toString());
    }
}
