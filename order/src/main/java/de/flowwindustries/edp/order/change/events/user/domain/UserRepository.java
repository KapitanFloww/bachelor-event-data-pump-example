package de.flowwindustries.edp.order.change.events.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find {@link User} by given identifier.
     * @param identifier
     * @return
     */
    Optional<User> findByIdentifier(String identifier);

    /**
     * Delete {@link User} by given identifier.
     * @param identifier
     */
    void deleteByIdentifier(String identifier);
}
