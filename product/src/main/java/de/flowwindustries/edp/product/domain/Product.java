package de.flowwindustries.edp.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.flowwindustries.edp.outbox.domain.Identifiable;
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
 * Product entity.
 */
@Data
@Entity
public class Product implements Identifiable {

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
     * Unique product identifier.
     */
    @Column(unique = true)
    private String identifier;

    /**
     * Product name.
     */
    private String name;

    /**
     * Product category.
     */
    private String category;

    /**
     * Product price.
     */
    private Double price;

    /**
     * Product status.
     */
    private Status status;
}
