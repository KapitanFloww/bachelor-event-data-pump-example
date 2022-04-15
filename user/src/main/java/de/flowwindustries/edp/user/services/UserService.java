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
     * @param userDTO
     * @return
     */
    User createUser(UserDTO userDTO);

    /**
     * Get the user details for the given identifier
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     */
    User getUser(String identifier) throws IllegalArgumentException;

    /**
     * Update the user details for the given identifier
     * @param identifier
     * @param userDTO
     * @return
     * @throws IllegalArgumentException
     */
    User updateUser(String identifier, UserDTO userDTO) throws IllegalArgumentException;

    /**
     * Get all users.
     * @return
     */
    Collection<User> getAllUsers();

    /**
     * Delete user details
     * @param identifier
     */
    void deleteUser(String identifier);
}
