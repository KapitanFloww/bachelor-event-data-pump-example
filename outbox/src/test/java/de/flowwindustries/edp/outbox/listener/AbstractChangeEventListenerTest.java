package de.flowwindustries.edp.outbox.listener;

import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntry;
import de.flowwindustries.edp.outbox.OutboxTestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static de.flowwindustries.edp.outbox.service.OutboxEntryServiceTest.getEntryWithType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = OutboxTestApplication.class)
public class AbstractChangeEventListenerTest {

    @SpyBean
    private TestChangeEventListener listener;

    @ParameterizedTest
    @EnumSource(EventType.class)
    void listenForOutboxEvent_Should_InvokeCorrectConsumer(EventType eventType) {
        //GIVEN
        OutboxEntry entry = getEntryWithType(eventType);

        //WHEN
        listener.listenForOutboxEvent(entry);

        //THEN
        switch (eventType) {
            case CREATION -> verify(listener, times(1)).handleCreationEvent();
            case UPDATE -> verify(listener, times(1)).handleUpdateEvent();
            case DELETION -> verify(listener, times(1)).handleDeleteEvent();
        }
    }

    @Test
    void listenForOutboxEvent_Should_SkipDuplicateEvents() {
        //GIVEN
        OutboxEntry entry = getEntryWithType(EventType.CREATION);

        //WHEN
        listener.listenForOutboxEvent(entry);
        listener.listenForOutboxEvent(entry);
        listener.listenForOutboxEvent(entry);

        //THEN
        verify(listener, times(1)).handleCreationEvent();
    }
}
