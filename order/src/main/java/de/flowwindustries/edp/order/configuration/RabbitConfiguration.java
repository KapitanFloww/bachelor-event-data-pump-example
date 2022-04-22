package de.flowwindustries.edp.order.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for RabbitMQ message broker & queues.
 */
@Configuration
public class RabbitConfiguration {

    // Product Change In-Queue

    @Value("${application.outbox.product-change.queue-name}")
    private String productQueue;

    @Bean
    public Queue productStateChangeQueue() {
        return new Queue(productQueue, true);
    }

    // User Change In-Queue

    @Value("${application.outbox.user-change.queue-name}")
    private String userQueue;

    @Bean
    public Queue userStateChangeQueue() {
        return new Queue(userQueue, true);
    }

    // Template Bean Configuration

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
