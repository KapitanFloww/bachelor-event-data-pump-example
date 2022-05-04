package de.flowwindustries.edp.order.change.events.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * User Replica Data-Structure.
 */
@Data
@Entity
public class User {

    /**
     * Database ID.
     */
    @Id
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User replica identifier.
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


}
