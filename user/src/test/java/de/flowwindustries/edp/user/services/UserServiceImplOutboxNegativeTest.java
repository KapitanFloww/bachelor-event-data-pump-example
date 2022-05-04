package de.flowwindustries.edp.user.services;

import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static de.flowwindustries.edp.user.services.UserServiceImplOutboxTest.DUMMY_UUID;
import static de.flowwindustries.edp.user.services.UserServiceImplOutboxTest.getDTO;
import static de.flowwindustries.edp.user.services.UserServiceImplOutboxTest.getEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplOutboxNegativeTest {

    private static final String MOCK_CONNECTION_EXCEPTION = "Could not connect to database host";
    @MockBean
    private UserRepository entityRepositoryMock;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private UserService entityService;

    @Test
    void createUser_Should_NotPersistOutboxEntry_WhenUserModificationError() {
        //GIVEN
        long count = outboxRepository.count();
        databaseIsUnavailable();

        //WHEN
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> entityService.createUser(getDTO()))
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
                .isThrownBy(() -> entityService.updateUser(DUMMY_UUID, getDTO()))
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
                .isThrownBy(() -> entityService.deleteUser(DUMMY_UUID))
                .withMessage(MOCK_CONNECTION_EXCEPTION);

        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    private void findDummyUserIsMocked() {
        when(entityRepositoryMock.findByIdentifier(DUMMY_UUID)).thenReturn(Optional.of(getEntity()));
    }

    private void databaseIsUnavailable() {
        when(entityRepositoryMock.save(any())).thenThrow(new RuntimeException(MOCK_CONNECTION_EXCEPTION));
        doThrow(new RuntimeException(MOCK_CONNECTION_EXCEPTION)).when(entityRepositoryMock).deleteByIdentifier(anyString());
    }
}
