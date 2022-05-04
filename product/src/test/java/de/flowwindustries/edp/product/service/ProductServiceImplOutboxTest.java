package de.flowwindustries.edp.product.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.outbox.domain.EventType;
import de.flowwindustries.edp.outbox.domain.OutboxEntryStatus;
import de.flowwindustries.edp.outbox.repository.OutboxRepository;
import de.flowwindustries.edp.product.controller.dto.ProductDTO;
import de.flowwindustries.edp.product.domain.Product;
import de.flowwindustries.edp.product.domain.Status;
import de.flowwindustries.edp.product.repository.ProductRepository;
import de.flowwindustries.edp.product.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceImplOutboxTest {

    public static final String DUMMY_UUID = "58627faa-74cf-4fc1-815f-f2c317a2d1d0";
    public static final String DUMMY_NAME = "Test-Product";
    public static final String DUMMY_CATEGORY = "Test-Category";
    public static final Double DUMMY_PRICE = 10.0d;
    @Autowired
    private ProductService entityService;
    @Autowired
    private ProductRepository entityRepository;
    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void createUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        //WHEN
        Product product = entityService.createProduct(getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(product.getIdentifier(), product, EventType.CREATION);
    }

    @Test
    void updateUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        Product product = entityRepository.save(getEntity());
        //WHEN
        entityService.updateProduct(product.getIdentifier(), getDTO());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(product.getIdentifier(),
                product, EventType.UPDATE);
    }

    @Test
    void deleteUser_Should_PersistOutboxEntry() {
        //GIVEN
        long count = outboxRepository.count();
        Product product = entityRepository.save(getEntity());
        //WHEN
        entityService.deleteProduct(product.getIdentifier());
        //THEN
        assertThat(outboxRepository.count()).isEqualTo(count + 1);
        assertThatOutboxEntryExists(product.getIdentifier(), null, EventType.DELETION);
    }

    @AfterEach
    void tearDown() {
        outboxRepository.deleteAll();
    }

    private void assertThatOutboxEntryExists(String identifier, Product entity, EventType eventType) {
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

    protected static ProductDTO getDTO() {
        ProductDTO dto = new ProductDTO();
        dto.setName(DUMMY_NAME);
        dto.setCategory(DUMMY_CATEGORY);
        dto.setPrice(DUMMY_PRICE);
        dto.setStatus(Status.APPROVED);
        return dto;
    }

    protected static Product getEntity() {
        Product user = new Product();
        user.setIdentifier(DUMMY_UUID);
        user.setName(DUMMY_NAME);
        user.setCategory(DUMMY_CATEGORY);
        user.setPrice(DUMMY_PRICE);
        user.setStatus(Status.APPROVED);
        return user;
    }
}
