package de.flowwindustries.edp.order.change.events.user;

import de.flowwindustries.edp.outbox.listener.AbstractChangeEventListener;
import de.flowwindustries.edp.order.change.events.user.service.UserService;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Listen for user updates.
 */
@Component
@RequiredArgsConstructor
public class UserChangeListener extends AbstractChangeEventListener {

    private final UserService userService;

    @RabbitListener(queues = "${application.outbox.user-change.queue-name}")
    public void listenForOutboxEvent(OutboxEntry in) {
        super.listenForOutboxEvent(in);
    }

    @Override
    protected Consumer<OutboxEntry> handleCreationEvent() {
        return userService::putUser;
    }

    @Override
    protected Consumer<OutboxEntry> handleUpdateEvent() {
        return userService::putUser;
    }

    @Override
    protected Consumer<OutboxEntry> handleDeleteEvent() {
        return userService::deleteUser;
    }
}
