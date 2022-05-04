package de.flowwindustries.edp.outbox.domain;

/**
 * State-Change Type of the entity.
 * <ul>
 *     <li>When entity is created, use {@code CREATION}</li>
 *     <li>When entity is updated, use {@code UPDATE}</li>
 *     <li>When entity is deleted, use {@code DELETION}</li>
 * </ul>
 */
public enum EventType {

    /**
     * Indicates the creation of an entity.
     * Will result in the data replica being created.
     */
    CREATION,

    /**
     * Indicates the update of an entity.
     * Will result in the data replica being updated accordingly.
     */
    UPDATE,

    /**
     * Indicates the deletion of an entity.
     * Will result in the deletion of the corresponding data replica.
     */
    DELETION
}
