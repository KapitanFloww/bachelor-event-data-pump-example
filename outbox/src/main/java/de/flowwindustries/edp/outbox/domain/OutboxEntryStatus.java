package de.flowwindustries.edp.outbox.domain;

/**
 * Processing status of an {@link OutboxEntry}.
 */
public enum OutboxEntryStatus {

    /**
     * Indicates that this {@link OutboxEntry} has not been processed and published to the broker yet.
     */
    UNPROCESSED,

    /**
     * Indicates that this {@link OutboxEntry} has been processed and published to the broker.
     */
    PROCESSED
}
