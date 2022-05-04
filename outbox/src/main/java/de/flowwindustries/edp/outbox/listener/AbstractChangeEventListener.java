package de.flowwindustries.edp.outbox.listener;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Abstract class for the subscriber / consumer component.
 * Child classes simply have to override {@link #listenForOutboxEvent(OutboxEntry)} method, specify it with
 * the {@code @RabbitListener} annotation, along with the required queue names and call {@code super.listenForOutboxEvent(in);}
 *
 * Simple implement the abstract methods to declare the event handling.
 *
 * See example:
 * <pre>
 * {@code
 * public class ChildListener extends AbstractListener {
 *
 *     @Autowired
 *     private final MyService myService;
 *
 *     @Override
 *     @RabbitListener(queues = "${path.to.my.queue.name}")
 *     public void listenForOutboxEvent(OutboxEntry in) {
 *         super.listenForOutboxEvent(in);
 *     }
 *
 *     @Override
 *     protected Consumer<OutboxEntry> handleCreationEvent() {
 *         return myService::create;
 *     }
 *
 *     @Override
 *     protected Consumer<OutboxEntry> handleUpdateEvent() {
 *         return myService::update;
 *     }
 *
 *     @Override
 *     protected Consumer<OutboxEntry> handleDeleteEvent() {
 *         return myService::delete;
 *     }
 *}
 * }
 * </pre>
 */
@Slf4j
public abstract class AbstractChangeEventListener {

    private final ArrayList<String> processedEventIds = new ArrayList<>();

    public void listenForOutboxEvent(OutboxEntry in) {
        log.debug("Received event to update product: {}", in);
        if(processedEventIds.contains(in.getEventId())) {
            log.debug("Caught duplicate event with id: {} Skipping...", in.getEventId());
            return;
        }
        switch (in.getEventType()) {
            case CREATION -> handleCreationEvent().accept(in);
            case UPDATE -> handleUpdateEvent().accept(in);
            case DELETION -> handleDeleteEvent().accept(in);
        }
        processedEventIds.add(in.getEventId());
        log.debug("Processed event with id: {}", in.getEventId());
    }

    /**
     * Override to specify how to handle an {@link EventType#CREATION} event.
     * @return a {@link Consumer} that consumes and processes an {@link OutboxEntry} to create a data replica
     */
    protected abstract Consumer<OutboxEntry> handleCreationEvent();

    /**
     * Override to specify how to handle an {@link EventType#UPDATE} event.
     * @return a {@link Consumer} that consumes and processes an {@link OutboxEntry} to update a data replica
     */
    protected abstract Consumer<OutboxEntry> handleUpdateEvent();

    /**
     * Override to specify how to handle an {@link EventType#DELETION} event.
     * @return a {@link Consumer} that consumes and processes an {@link OutboxEntry} to delete a data replica
     */
    protected abstract Consumer<OutboxEntry> handleDeleteEvent();
}
