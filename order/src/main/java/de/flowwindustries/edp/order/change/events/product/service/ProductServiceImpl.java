package de.flowwindustries.edp.order.change.events.product.service;

import de.flowwindustries.edp.order.change.events.EventPayloadMapper;
import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.product.domain.ProductRepository;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Service implementation of {@link ProductService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EventPayloadMapper mapper;

    @Override
    @Transactional
    public Product putProduct(OutboxEntry entry) {
        //Map payload to order product
        Product product = mapper.mapToProduct(entry.getPayload());
        if(!entry.getAggregateId().equals(product.getIdentifier())) {
            throw new IllegalStateException("Aggregate Ids do not match!");
        }

        //If already saved - update the holder
        Optional<Product> optionalOrderHolder = productRepository.findByIdentifier(entry.getAggregateId());
        if(optionalOrderHolder.isPresent()) {
            Product persistedProduct = optionalOrderHolder.get();

            //Update the details
            persistedProduct.setName(product.getName());
            persistedProduct.setPrice(product.getPrice());
            persistedProduct.setCategory(product.getCategory());

            //Persist the updated order holder
            log.debug("Updated product: {}", persistedProduct);
            persistedProduct = productRepository.save(persistedProduct);
            return persistedProduct;
        }

        //Save updated product
        product = productRepository.save(product);
        log.debug("Created product: {}", product);
        return product;
    }

    @Override
    public Collection<Product> findAll() {
        log.debug("Request to find all persisted products");
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteProduct(OutboxEntry entry) {
        log.debug("Deleting products: {}", entry.getAggregateId());
        productRepository.deleteByIdentifier(entry.getAggregateId());
    }

    @Override
    public Product getProductSafe(String identifier) {
        return productRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product %s not found!", identifier)));
    }
}
