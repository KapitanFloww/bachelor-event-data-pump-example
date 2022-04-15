package de.flowwindustries.edp.outbox.controller;

import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.service.OutboxEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Debug Controller for {@link OutboxEntry}s.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/debug/outbox")
public class OutboxDebugController {

    private final OutboxEntryService outboxEntryService;

    /**
     * GET all outbox entry resources with status {@link OutboxEntryStatus#UNPROCESSED}.
     * @return all outbox entries with status {@link OutboxEntryStatus#UNPROCESSED}
     */
    @GetMapping(value = "/unprocessed/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OutboxEntry>> getOutboxEntries() {
        return ResponseEntity.ok(outboxEntryService.findUnprocessedOutboxEntries());
    }

    /**
     * Get all outbox entries.
     * @return all outbox entries
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OutboxEntry>> getAll() {
        return ResponseEntity.ok(outboxEntryService.findAll());
    }


}
