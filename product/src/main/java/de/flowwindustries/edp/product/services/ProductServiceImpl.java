package de.flowwindustries.edp.product.services;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import de.flowwindustries.edp.product.controller.dto.ProductDTO;
import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

/**
 * Service implementation of {@link ProductService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_NOT_FOUND = "Product with identifier %s not found";
    private final ProductRepository productRepository;
    private final OutboxEntryService outboxEntryService;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setIdentifier(UUID.randomUUID().toString());

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.getStatus());

        product = productRepository.save(product);
        log.info("Created new product: {}", product);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(product.getIdentifier(), EventType.CREATION, product);

        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(String identifier) throws IllegalArgumentException {
        log.info("Request to get product with identifier: {}", identifier);
        return productRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format(PRODUCT_NOT_FOUND, identifier)));

    }

    @Override
    @Transactional
    public Product updateProduct(String identifier, ProductDTO productDTO) throws IllegalArgumentException {
        log.info("Request to update product with identifier: {} to {}", identifier, productDTO);
        Product product = getProduct(identifier);

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.getStatus());

        product = productRepository.save(product);

        log.info("Updated product: {}", product);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(product.getIdentifier(), EventType.UPDATE, product);

        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Product> getAllProducts() {
        log.info("Request to return all products");
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteProduct(String identifier) {
        productRepository.deleteByIdentifier(identifier);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(identifier, EventType.DELETION, null);
    }
}
