package de.flowwindustries.edp.order.change.events.product.controller;

import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/debug/products")
@RequiredArgsConstructor
public class ProductDebugController {

    private final ProductService productService;

    /**
     * GET all persisted orderHolders
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }


}
