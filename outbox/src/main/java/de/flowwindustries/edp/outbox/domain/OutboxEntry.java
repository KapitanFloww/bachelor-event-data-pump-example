package de.flowwindustries.edp.outbox.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

/**
 * Event entity to be persisted to the OUTBOX table.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OUTBOX")
public class OutboxEntry implements Serializable {

    static final long serialVersionUID = 111L;

    @Id
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The unique messageId
     */
    @Column(unique = true)
    private String eventId;

    private OutboxEntryStatus outboxEntryStatus;

    /**
     * The id of the corresponding aggregate / business entity
     */
    private String aggregateId;
    private EventType eventType;
    private String payload;
    private Instant createdAt;
}
