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

    private final UserRepository userRepository;
    private final EventPayloadMapper mapper;

    @Override
    @Transactional
    public User putUser(OutboxEntry entry) {
        //Map payload to order mappedUser
        User mappedUser = mapper.mapToUser(entry.getPayload());
        if(!entry.getAggregateId().equals(mappedUser.getIdentifier())) {
            throw new IllegalStateException("Aggregate Ids do not match!");
        }

        //If already saved - update the user
        Optional<User> optionalOrderHolder = userRepository.findByIdentifier(entry.getAggregateId());
        if(optionalOrderHolder.isPresent()) {
            User user = optionalOrderHolder.get();

            //Update the details
            user.setName(mappedUser.getName());
            user.setMail(mappedUser.getMail());

            //Persist the updated user
            log.debug("Updated user: {}", user);
            user = userRepository.save(user);
            return user;
        }

        //Save updated mappedUser
        mappedUser = userRepository.save(mappedUser);
        log.debug("Created user: {}", mappedUser);
        return mappedUser;
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
    public User getUserSafe(String identifier) {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User %s not found", identifier)));
    }
}
