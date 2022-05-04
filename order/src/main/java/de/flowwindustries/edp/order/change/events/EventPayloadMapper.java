package de.flowwindustries.edp.order.change.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class to map json Strings to {@link User}s or {@link Product}s.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventPayloadMapper {

    public static final String JSON_MAPPING_ERROR = "Could not map JSON String: %s";
    private final ObjectMapper mapper;

    /**
     * Map a json {@link String} to an {@link User}.
     * @param jsonPayload the JSON Payload form which to map
     * @return the mapped entity
     */
    public User mapToUser(String jsonPayload) {
        log.debug("Trying to map to order user: {}", jsonPayload);
        try {
            JsonNode payload = mapper.readValue(jsonPayload, JsonNode.class);
            User user = new User();
            user.setIdentifier(payload.get("identifier").asText());
            user.setMail(payload.get("mail").asText());
            user.setName(payload.get("name").asText());
            return user;
        } catch (JsonProcessingException ex) {
            log.warn(ex.getMessage());
            throw new IllegalArgumentException(String.format(JSON_MAPPING_ERROR, jsonPayload));
        }
    }

    /**
     * Map a json {@link String} to an {@link Product}.
     * @param jsonPayload the JSON Payload form which to map
     * @return the mapped entity
     */
    public Product mapToProduct(String jsonPayload) {
        log.debug("Trying to map to product: {}", jsonPayload);
        try {
            JsonNode payload = mapper.readValue(jsonPayload, JsonNode.class);
            Product product = new Product();
            product.setIdentifier(payload.get("identifier").asText());
            product.setPrice(payload.get("price").asDouble());
            product.setCategory(payload.get("category").asText());
            product.setName(payload.get("name").asText());
            return product;
        } catch (JsonProcessingException ex) {
            log.warn(ex.getMessage());
            throw new IllegalArgumentException(String.format(JSON_MAPPING_ERROR, jsonPayload));
        }
    }
}
