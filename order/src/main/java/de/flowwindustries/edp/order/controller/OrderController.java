package de.flowwindustries.edp.order.controller;

import de.flowwindustries.edp.order.controller.dto.OrderAggregate;
import de.flowwindustries.edp.order.controller.dto.OrderDTO;
import de.flowwindustries.edp.order.domain.Purchase;
import de.flowwindustries.edp.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;

/**
 * Resource for {@link Purchase} entity.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * HTTP GET to get a user resource.
     * @param identifier unique identifier of the order to get
     * @return the order
     */
    @GetMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> getUser(@PathVariable("identifier") String identifier) {
        return ResponseEntity.ok(orderService.getOrder(identifier));
    }

    /**
     * HTTP GET all user resources.
     * @return all persisted orders
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Purchase>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * HTTP POST to create a new user resource.
     * @param orderDTO data-transfer-object for order to create
     * @return the created and persisted order
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> postUser(@RequestBody OrderDTO orderDTO) {
        Purchase user = orderService.createOrder(orderDTO);
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/order/%s", user.getIdentifier())))
                .body(user);
    }

    /**
     * HTTP PUT to update a user resource.
     * @param identifier the unique identifier of the order to update
     * @param orderDTO data-transfer-object containing the updated order details
     * @return the updated order
     */
    @PutMapping(value = "/{identifier}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Purchase> putUser(@PathVariable("identifier") String identifier,
                                            @RequestBody OrderDTO orderDTO) {
        return ResponseEntity
                .ok(orderService.updateOrder(identifier, orderDTO));
    }

    /**
     * HTTP DELETE to delete a user resource.
     * @param identifier the unique identifier of the order to delete
     * @return ResponseEntity#ok
     */
    @DeleteMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable("identifier") String identifier) {
        orderService.deleteOrder(identifier);
        return ResponseEntity.ok().build();
    }

    /**
     * HTTP GET the order aggregate containing aggregated data.
     * @param identifier the unique identifier of the aggregated order to get
     * @return the aggregated order details
     */
    @GetMapping(value = "/aggregate/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderAggregate> getOrderAggregate(@PathVariable("identifier") String identifier) {
        return ResponseEntity.ok(orderService.getOrderAggregate(identifier));
    }
}
