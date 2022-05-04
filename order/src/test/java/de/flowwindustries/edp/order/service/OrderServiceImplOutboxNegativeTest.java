package de.flowwindustries.edp.order.service;

import de.flowwindustries.edp.order.domain.OrderRepository;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static de.flowwindustries.edp.order.service.OrderServiceImplOutboxTest.DUMMY_UUID;
import static de.flowwindustries.edp.order.service.OrderServiceImplOutboxTest.getDTO;
import static de.flowwindustries.edp.order.service.OrderServiceImplOutboxTest.getEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceImplOutboxNegativeTest {
    private static final String MOCK_CONNECTION_EXCEPTION = "Could not connect to database host";
    @MockBean
    private OrderRepository entityRepositoryMock;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private OrderService entityService;

    @Test
    void createUser_Should_NotPersistOutboxEntry_WhenUserModificationError() {
        //GIVEN
        long count = outboxRepository.count();
        databaseIsUnavailable();

        //WHEN
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> entityService.createOrder(getDTO()))
                .withMessage(MOCK_CONNECTION_EXCEPTION);

        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count);
    }

    @Test
    void updateUser_Should_NotPersistOutboxEntry_WhenUserModificationError() {
        //GIVEN
        long count = outboxRepository.count();
        databaseIsUnavailable();
        findDummyUserIsMocked();

        //WHEN
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> entityService.updateOrder(DUMMY_UUID, getDTO()))
                .withMessage(MOCK_CONNECTION_EXCEPTION);

        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count);
    }

    @Test
    void deleteUser_Should_NotPersistOutboxEntry_WhenUserModificationError() {
        //GIVEN
        long count = outboxRepository.count();
        databaseIsUnavailable();
        findDummyUserIsMocked();

        //WHEN
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> entityService.deleteOrder(DUMMY_UUID))
                .withMessage(MOCK_CONNECTION_EXCEPTION);

        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    private void databaseIsUnavailable() {
        when(entityRepositoryMock.save(any())).thenThrow(new RuntimeException(MOCK_CONNECTION_EXCEPTION));
        doThrow(new RuntimeException(MOCK_CONNECTION_EXCEPTION)).when(entityRepositoryMock).deleteByIdentifier(anyString());
    }

    private void findDummyUserIsMocked() {
        when(entityRepositoryMock.findByIdentifier(DUMMY_UUID)).thenReturn(Optional.of(getEntity()));
    }
}
