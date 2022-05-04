package de.flowwindustries.edp.user.services;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import de.flowwindustries.edp.user.controller.dto.UserDTO;
import de.flowwindustries.edp.user.domain.User;
import de.flowwindustries.edp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

/**
 * Service implementation of {@link UserService} interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User with identifier %s not found";
    private final UserRepository userRepository;
    private final OutboxEntryService outboxEntryService;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setIdentifier(UUID.randomUUID().toString());

        user.setName(userDTO.getName());
        user.setMail(userDTO.getMail());
        user.setAddress(userDTO.getAddress());
        user.setStatus(userDTO.getStatus());

        user = userRepository.save(user);
        log.info("Created new user: {}", user);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(user.getIdentifier(), EventType.CREATION, user);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String identifier) throws IllegalArgumentException {
        log.info("Request to get user with identifier: {}", identifier);
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND, identifier)));
    }

    @Override
    @Transactional
    public User updateUser(String identifier, UserDTO userDTO) throws IllegalArgumentException {
        log.info("Request to update user with identifier: {} to {}", identifier, userDTO);
        User user = getUser(identifier);

        user.setName(userDTO.getName());
        user.setMail(userDTO.getMail());
        user.setAddress(userDTO.getAddress());
        user.setStatus(userDTO.getStatus());

        user = userRepository.save(user);

        log.info("Updated user: {}", user);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(user.getIdentifier(), EventType.UPDATE, user);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getAllUsers() {
        log.info("Request to return all users");
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(String identifier) {
        userRepository.deleteByIdentifier(identifier);

        //Persist outbox entry
        outboxEntryService.createOutboxEntry(identifier, EventType.DELETION, null);
    }
}
