package de.flowwindustries.edp.product.controller;

import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.controller.dto.ProductDTO;
import de.flowwindustries.edp.product.services.ProductService;
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
 * Resource for {@link Product} entity.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    /**
     * HTTP GET to get a product resource.
     * @param identifier
     * @return
     */
    @GetMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("identifier") String identifier) {
        return ResponseEntity.ok(productService.getProduct(identifier));
    }

    /**
     * HTTP GET all product resources.
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * HTTP POST to create a new product resource.
     * @param productDTO
     * @return
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> postProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/product/%s", product.getIdentifier())))
                .body(product);
    }

    /**
     * HTTP PUT to update a product resource.
     * @param identifier
     * @param productDTO
     * @return
     */
    @PutMapping(value = "/{identifier}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> putProduct(@PathVariable("identifier") String identifier,
                                              @RequestBody ProductDTO productDTO) {
        return ResponseEntity
                .ok(productService.updateProduct(identifier, productDTO));
    }

    /**
     * HTTP DELETE to delete a product resource.
     * @param identifier
     * @return
     */
    @DeleteMapping(value = "/{identifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteProduct(@PathVariable("identifier") String identifier) {
        productService.deleteProduct(identifier);
        return ResponseEntity.ok().build();
    }
}
