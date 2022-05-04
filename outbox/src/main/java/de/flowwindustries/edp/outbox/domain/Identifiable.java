package de.flowwindustries.edp.outbox.domain;

/**
 * Interface to declare an entity as being identifiable using a unique identifier.
 */
public interface Identifiable {

    /**
     * Return the unique identifier for this entity.
     * @return a {@link String} representation of the unique identifier of this entity
     */
    String getIdentifier();
}