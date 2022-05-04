package de.flowwindustries.edp.order.change.events.product.domain;

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
 * Product Replica Data-Structure.
 */
@Data
@Entity
public class Product {

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
     * Product replica identifier.
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

}
