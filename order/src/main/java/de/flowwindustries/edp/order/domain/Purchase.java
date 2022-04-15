package de.flowwindustries.edp.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * Order entity.
 */
@Data
@Entity
public class Purchase {

    @Id
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String identifier;

    private Double price;
    private Status status;

    /**
     * Relationships to order domains
     */

    /**
     * Relationship to user domain. User identifier.
     */
    private String holderId;

    /**
     * Relationship to product domain. Product identifier.
     */
    @ElementCollection
    private List<String> productIds;
}
