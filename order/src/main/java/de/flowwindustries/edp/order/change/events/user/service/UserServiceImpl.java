package de.flowwindustries.edp.order.change.events.user.service;

import de.flowwindustries.edp.order.change.events.EventPayloadMapper;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import de.flowwindustries.edp.order.change.events.user.domain.UserRepository;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Service implementation of {@link UserService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String INVALID_AGGREGATE_IDS = "Aggregate Ids do not match!";
    public static final String USER_NOT_FOUND = "User %s not found";

    private final UserRepository userRepository;
    private final EventPayloadMapper mapper;

    @Override
    @Transactional
    public void putUser(OutboxEntry entry) {
        //Map payload to order mappedUser
        User mappedUser = mapper.mapToUser(entry.getPayload());
        if(!entry.getAggregateId().equals(mappedUser.getIdentifier())) {
            throw new IllegalStateException(INVALID_AGGREGATE_IDS);
        }

        //If already saved - update the user
        Optional<User> optionalOrderHolder = userRepository.findByIdentifier(entry.getAggregateId());
        if(optionalOrderHolder.isPresent()) {
            User user = optionalOrderHolder.get();

            //Update the details
            user.setName(mappedUser.getName());
            user.setMail(mappedUser.getMail());

            //Persist the updated user
            user = userRepository.save(user);
            log.debug("Updated user: {}", user);
            return;
        }

        //Save updated mappedUser
        mappedUser = userRepository.save(mappedUser);
        log.debug("Created user: {}", mappedUser);
    }

    @Override
    public Collection<User> findAll() {
        log.debug("Request to find all persisted users");
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(OutboxEntry entry) {
        log.debug("Deleting user: {}", entry.getAggregateId());
        userRepository.deleteByIdentifier(entry.getAggregateId());
    }

    @Override
    public User getUserSafe(String identifier) throws IllegalArgumentException {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND, identifier)));
    }
}
