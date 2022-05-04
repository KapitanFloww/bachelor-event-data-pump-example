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

import static de.flowwindustries.edp.order.change.events.user.service.UserServiceImpl.INVALID_AGGREGATE_IDS;

/**
 * Service implementation of {@link ProductService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Product %s not found!";

    private final ProductRepository productRepository;
    private final EventPayloadMapper mapper;

    @Override
    @Transactional
    public void putProduct(OutboxEntry entry) {
        //Map payload to order product
        Product product = mapper.mapToProduct(entry.getPayload());
        if(!entry.getAggregateId().equals(product.getIdentifier())) {
            throw new IllegalStateException(INVALID_AGGREGATE_IDS);
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
            persistedProduct = productRepository.save(persistedProduct);
            log.debug("Updated product: {}", persistedProduct);
            return;
        }

        //Save updated product
        product = productRepository.save(product);
        log.debug("Created product: {}", product);
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
                .orElseThrow(() -> new IllegalArgumentException(String.format(PRODUCT_NOT_FOUND, identifier)));
    }
}
