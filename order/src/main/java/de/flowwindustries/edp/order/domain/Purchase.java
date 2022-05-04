package de.flowwindustries.edp.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.flowwindustries.edp.outbox.domain.Identifiable;
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
public class Purchase implements Identifiable {

    /**
     * Database ID.
     */
    @Id
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Unique order identifier.
     */
    @Column(unique = true)
    private String identifier;

    /**
     * Total price of an order.
     */
    private Double price;

    /**
     * Current status of the order.
     */
    private Status status;

    /**
     * Referenced user identifier.
     */
    private String holderId;

    /**
     * Referenced product identifiers.
     */
    @ElementCollection
    private List<String> productIds;
}
