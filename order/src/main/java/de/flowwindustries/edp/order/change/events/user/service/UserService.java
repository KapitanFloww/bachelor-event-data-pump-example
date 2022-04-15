package de.flowwindustries.edp.order.change.events.user.service;

import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;

import java.util.Collection;

/**
 * Service interface to update the user data replica, known as {@link User} in this context.
 */
public interface UserService {

    /**
     * Create or update a {@link User} for this {@link OutboxEntry}.
     * @param entry
     * @return
     */
    User putUser(OutboxEntry entry);

    /**
     * Return all persisted {@link User}s.
     * @return
     */
    Collection<User> findAll();

    /**
     * Delete the {@link User} associated to this {@link OutboxEntry}.
     * @param entry
     */
    void deleteUser(OutboxEntry entry);

    /**
     * Get the order holder
     * @param identifier
     * @return
     */
    User getUserSafe(String identifier);
}
