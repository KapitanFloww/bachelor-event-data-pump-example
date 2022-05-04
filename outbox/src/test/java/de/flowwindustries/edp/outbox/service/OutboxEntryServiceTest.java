package de.flowwindustries.edp.outbox.service;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.outbox.OutboxTestApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static de.flowwindustries.edp.outbox.service.OutboxEntryServiceImpl.AGGREGATE_ID_INVALID;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = OutboxTestApplication.class)
public class OutboxEntryServiceTest {

    private static final String TEST_UUID = "f76ca146-5535-4ccd-b978-e75393878cc7";

    @Autowired
    private OutboxEntryService outboxEntryService;

    @Autowired
    private MockCrudServiceProxy proxyService;
    @Autowired
    private OutboxRepository outboxRepository;

    @Test
    void contextLoads() {
        assertThat(outboxEntryService).isNotNull();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "TestEntity(identifier=f76ca146-5535-4ccd-b978-e75393878cc7, name=Test-Entity)")
    void createOutboxEntry_Should_CreateAndPersistOutboxEntry_When_WithPayload(String payload) {
        //GIVEN
        long size = outboxRepository.count();

        //WHEN
        OutboxEntry entry = proxyService.createOutboxEntry(TEST_UUID, EventType.CREATION, payload);

        //THEN
        assertThat(outboxRepository.count()).isEqualTo(size + 1);
        Optional<OutboxEntry> optionalOutboxEntry = outboxRepository.findByEventId(entry.getEventId());
        assertThat(optionalOutboxEntry).isPresent();
        assertThat(optionalOutboxEntry.get()).isEqualTo(entry);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = SPACE)
    void createOutboxEntry_Should_ThrowIllegalArgumentException_When_InvalidAggregateId(String aggregateId) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> proxyService.createOutboxEntry(aggregateId, EventType.CREATION, null))
                .withMessageContaining(AGGREGATE_ID_INVALID);
    }

    @Test
    void processOutboxEntry_Should_UpdateOutboxEntry() {
        //GIVEN
        OutboxEntry entry = outboxRepository.save(getUnprocessedEntry());
        //WHEN
        entry = outboxEntryService.processOutboxEntry(entry);
        //THEN
        assertThat(entry.getOutboxEntryStatus()).isEqualTo(OutboxEntryStatus.PROCESSED);
    }

    @Test
    void findUnprocessedOutboxEntries_Should_ReturnEmptyCollection() {
        //GIVEN
        //WHEN
        Collection<OutboxEntry> entries = outboxEntryService.findUnprocessedOutboxEntries();
        //THEN
        assertThat(entries).isNotNull();
        assertThat(entries).hasSize(0);
    }

    @Test
    void findUnprocessedOutboxEntries_Should_CollectionWithUnprocessedEntries() {
        //GIVEN
        outboxRepository.save(getProcessedEntry());
        OutboxEntry unprocessedEntry = outboxRepository.save(getUnprocessedEntry());
        //WHEN
        Collection<OutboxEntry> entries = outboxEntryService.findUnprocessedOutboxEntries();
        //THEN
        assertThat(entries).isNotNull();
        assertThat(entries).hasSize(1);
        assertThat(entries).contains(unprocessedEntry);
    }

    @Test
    void findAll_Should_ReturnEmptyCollection() {
        //GIVEN
        //WHEN
        Collection<OutboxEntry> entries = outboxEntryService.findAll();
        //THEN
        assertThat(entries).isNotNull();
        assertThat(entries).hasSize(0);
    }

    @Test
    void findAll_Should_ReturnAllEntries() {
        //GIVEN
        OutboxEntry entry1 = outboxRepository.save(getProcessedEntry());
        OutboxEntry entry2 = outboxRepository.save(getUnprocessedEntry());
        //WHEN
        Collection<OutboxEntry> entries = outboxEntryService.findAll();
        //THEN
        assertThat(entries).isNotNull();
        assertThat(entries).hasSize(2);
        assertThat(entries).contains(entry1, entry2);
    }

    @Test
    void deleteProcessed_Should_DeleteProcessedEntries() {
        //GIVEN
        outboxRepository.save(getUnprocessedEntry());
        outboxRepository.save(getUnprocessedEntry());
        outboxRepository.save(getProcessedEntry());
        outboxRepository.save(getProcessedEntry());
        long size = outboxRepository.count();
        //WHEN
        outboxEntryService.deleteProcessed();
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(size - 2);
    }

    @Test
    void deleteProcessed_Should_DeleteNoEntries_WhenNoProcessedEntriesPresent() {
        //GIVEN
        outboxRepository.save(getUnprocessedEntry());
        outboxRepository.save(getUnprocessedEntry());
        long size = outboxRepository.count();
        //WHEN
        outboxEntryService.deleteProcessed();
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(size);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    public static OutboxEntry getProcessedEntry() {
        return getEntryIntern();
    }

    public static OutboxEntry getUnprocessedEntry() {
        OutboxEntry entry = getEntryIntern();
        entry.setOutboxEntryStatus(OutboxEntryStatus.UNPROCESSED);
        return entry;
    }

    public static OutboxEntry getEntryWithType(EventType type) {
        OutboxEntry entry = getEntryIntern();
        entry.setEventType(type);
        return entry;
    }

    private static OutboxEntry getEntryIntern() {
        return new OutboxEntry(null, UUID.randomUUID().toString(), OutboxEntryStatus.PROCESSED, UUID.randomUUID().toString(), EventType.CREATION, "Test-Data", Instant.now(Clock.system(ZoneId.systemDefault())).truncatedTo(ChronoUnit.MILLIS));
    }
}
