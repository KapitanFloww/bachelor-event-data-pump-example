package de.flowwindustries.edp.user.services;

import de.flowwindustries.edp.user.controller.dto.UserDTO;
import de.flowwindustries.edp.user.domain.User;

import java.util.Collection;

/**
 * Service interface for CRUD operations on {@link User} entities.
 */
public interface UserService {

    /**
     * Create a new user.
     * @param userDTO data-transfer-object containing the user details
     * @return the persisted user entity
     */
    User createUser(UserDTO userDTO);

    /**
     * Get the user details for the given identifier.
     * @param identifier unique identifier of the user to fetch
     * @return the request user
     * @throws IllegalArgumentException if no user is found for the given identifier
     */
    User getUser(String identifier) throws IllegalArgumentException;

    /**
     * Update the user details for the given identifier.
     * @param identifier unique identifier of the user to update
     * @param userDTO data-transfer-object containing the updated details
     * @return the updated user
     * @throws IllegalArgumentException if no user is found for the given identifier
     */
    User updateUser(String identifier, UserDTO userDTO) throws IllegalArgumentException;

    /**
     * Get all users.
     * @return all persisted users
     */
    Collection<User> getAllUsers();

    /**
     * Delete user details.
     * @param identifier unique identifier of the user to delete
     */
    void deleteUser(String identifier);
}
