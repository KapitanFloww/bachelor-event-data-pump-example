package de.flowwindustries.edp.user.domain;

import de.flowwindustries.edp.outbox.domain.Identifiable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * User entity.
 */
@Data
@Entity
public class User implements Identifiable {

    /**
     * Database ID.
     */
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Unique user identifier.
     */
    @Column(unique = true)
    private String identifier;

    /**
     * User name.
     */
    private String name;

    /**
     * User mail.
     */
    private String mail;

    /**
     * User address.
     */
    private String address;

    /**
     * User status.
     */
    private Status status;
}
