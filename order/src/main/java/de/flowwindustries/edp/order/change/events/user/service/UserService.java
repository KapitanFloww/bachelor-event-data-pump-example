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
     * @param entry {@link OutboxEntry} containing the user to create or update
     */
    void putUser(OutboxEntry entry);

    /**
     * Return all persisted {@link User}s.
     * @return all persisted users
     */
    Collection<User> findAll();

    /**
     * Delete the {@link User} associated to this {@link OutboxEntry}.
     * @param entry {@link OutboxEntry} containing the identifier of the user to delete ({@link OutboxEntry#getAggregateId()})
     */
    void deleteUser(OutboxEntry entry);

    /**
     * Get the order holder.
     * @param identifier identifier of the user to fetch
     * @return the requested user
     * @throws IllegalArgumentException if no user can be found with the given identifier
     */
    User getUserSafe(String identifier) throws IllegalArgumentException;
}
