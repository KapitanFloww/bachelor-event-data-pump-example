package de.flowwindustries.edp.user.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.user.controller.dto.UserDTO;
import de.flowwindustries.edp.user.domain.Status;
import de.flowwindustries.edp.user.domain.User;
import de.flowwindustries.edp.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceImplOutboxTest {

    public static final String DUMMY_UUID = "bf8c9e4e-04f2-4f37-812f-613ef6b6690c";
    public static final String DUMMY_ADDRESS = "Musterstraße 8, Musterstadt";
    public static final String DUMMY_MAIL = "Max@Mustermann.de";
    public static final String DUMMY_NAME = "Max Mustermann";
    @Autowired
    private UserService entityService;
    @Autowired
    private UserRepository entityRepository;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void createUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        //WHEN
        User user = entityService.createUser(getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(user.getIdentifier(), user, EventType.CREATION);
    }

    @Test
    void updateUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        User user = entityRepository.save(getEntity());
        //WHEN
        entityService.updateUser(user.getIdentifier(), getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(user.getIdentifier(),
                user, EventType.UPDATE);
    }

    @Test
    void deleteUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        User user = entityRepository.save(getEntity());
        //WHEN
        entityService.deleteUser(user.getIdentifier());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(user.getIdentifier(), null, EventType.DELETION);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    private void assertThatOutboxEntryExists(String identifier, User entity, EventType eventType) {
        assertThat(outboxRepository.findAll().get(0).getAggregateId()).isEqualTo(identifier);
        assertThat(outboxRepository.findAll().get(0).getEventType()).isEqualTo(eventType);
        assertThat(outboxRepository.findAll().get(0).getOutboxEntryStatus()).isEqualTo(OutboxEntryStatus.UNPROCESSED);
        if(entity == null) {
            assertThat(outboxRepository.findAll().get(0).getPayload()).isNull();
        } else {
            JsonNode node = mapper.convertValue(entity, JsonNode.class);
            assertThat(outboxRepository.findAll().get(0).getPayload()).isEqualTo(node.toString());
        }
    }

    protected static UserDTO getDTO() {
        UserDTO dto = new UserDTO();
        dto.setAddress(DUMMY_ADDRESS);
        dto.setMail(DUMMY_MAIL);
        dto.setName(DUMMY_NAME);
        dto.setStatus(Status.APPROVED);
        return dto;
    }

    protected static User getEntity() {
        User user = new User();
        user.setIdentifier(DUMMY_UUID);
        user.setAddress(DUMMY_ADDRESS);
        user.setMail(DUMMY_MAIL);
        user.setName(DUMMY_NAME);
        user.setStatus(Status.APPROVED);
        return user;
    }
}
