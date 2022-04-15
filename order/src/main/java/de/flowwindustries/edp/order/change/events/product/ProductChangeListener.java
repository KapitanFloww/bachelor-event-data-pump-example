package de.flowwindustries.edp.order.change.events.product;

import de.flowwindustries.edp.outbox.listener.AbstractChangeEventListener;
import de.flowwindustries.edp.order.change.events.product.service.ProductService;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Listen for product updates.
 */
@Component
@RequiredArgsConstructor
public class ProductChangeListener extends AbstractChangeEventListener {

    private final ProductService productService;

    @Override
    @RabbitListener(queues = "${application.outbox.product-change.queue-name}")
    public void listenForOutboxEvent(OutboxEntry in) {
        super.listenForOutboxEvent(in);
    }

    @Override
    protected Consumer<OutboxEntry> handleCreationEvent() {
        return productService::putProduct;
    }

    @Override
    protected Consumer<OutboxEntry> handleUpdateEvent() {
        return productService::putProduct;
    }

    @Override
    protected Consumer<OutboxEntry> handleDeleteEvent() {
        return productService::deleteProduct;
    }
}
