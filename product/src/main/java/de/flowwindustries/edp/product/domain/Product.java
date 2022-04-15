package de.flowwindustries.edp.product.domain;

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
 * User entity.
 */
@Data
@Entity
public class Product {

    @Id
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String identifier;

    private String name;
    private String category;
    private Double price;
    private Status status;
}
