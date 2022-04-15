package de.flowwindustries.edp.user.repository;

import de.flowwindustries.edp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link User} repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by identifier.
     * @param identifier
     * @return
     */
    Optional<User> findByIdentifier(String identifier);

    /**
     * Delete user by identifier.
     * @param identifier
     */
    void deleteByIdentifier(String identifier);
}
