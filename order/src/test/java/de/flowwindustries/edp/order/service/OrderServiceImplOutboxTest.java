package de.flowwindustries.edp.order.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.order.controller.dto.OrderDTO;
import de.flowwindustries.edp.order.domain.OrderRepository;
import de.flowwindustries.edp.order.domain.Purchase;
import de.flowwindustries.edp.order.domain.Status;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderServiceImplOutboxTest {

    public static final String DUMMY_UUID = "9998d6c5-6b12-4e92-871e-ef339209fd3d";
    public static final String DUMMY_HOLDER = "9790e5b4-47cd-4d1d-8b27-933cb651e9cc";
    public static final List<String> DUMMY_PRODUCTS = Collections.emptyList();
    public static final Double DUMMY_PRICE = 10.0d;
    @Autowired
    private OrderService entityService;
    @Autowired
    private OrderRepository entityRepository;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void createUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        //WHEN
        Purchase purchase = entityService.createOrder(getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(purchase.getIdentifier(), purchase, EventType.CREATION);
    }

    @Test
    void updateUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        Purchase purchase = entityRepository.save(getEntity());
        //WHEN
        entityService.updateOrder(purchase.getIdentifier(), getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(purchase.getIdentifier(),
                purchase, EventType.UPDATE);
    }

    @Test
    void deleteUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        Purchase purchase = entityRepository.save(getEntity());
        //WHEN
        entityService.deleteOrder(purchase.getIdentifier());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(purchase.getIdentifier(), null, EventType.DELETION);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    private void assertThatOutboxEntryExists(String identifier, Purchase entity, EventType eventType) {
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

    protected static OrderDTO getDTO() {
        OrderDTO dto = new OrderDTO();
        dto.setPrice(DUMMY_PRICE);
        dto.setHolderId(DUMMY_HOLDER);
        dto.setProductIds(DUMMY_PRODUCTS);
        dto.setStatus(Status.APPROVED);
        return dto;
    }

    protected static Purchase getEntity() {
        Purchase purchase = new Purchase();
        purchase.setIdentifier(DUMMY_UUID);
        purchase.setPrice(DUMMY_PRICE);
        purchase.setHolderId(DUMMY_HOLDER);
        purchase.setProductIds(DUMMY_PRODUCTS);
        purchase.setStatus(Status.APPROVED);
        return purchase;
    }
}
