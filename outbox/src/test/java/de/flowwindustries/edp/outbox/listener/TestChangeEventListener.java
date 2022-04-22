package de.flowwindustries.edp.outbox.listener;

import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.listener.AbstractChangeEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class TestChangeEventListener extends AbstractChangeEventListener {

    @Override
    protected Consumer<OutboxEntry> handleCreationEvent() {
        return outboxEntry -> log.debug("Handling creation event: {}", outboxEntry);
    }

    @Override
    protected Consumer<OutboxEntry> handleUpdateEvent() {
        return outboxEntry -> log.debug("Handling update event: {}", outboxEntry);
    }

    @Override
    protected Consumer<OutboxEntry> handleDeleteEvent() {
        return outboxEntry -> log.debug("Handling deletion event: {}", outboxEntry);
    }
}
