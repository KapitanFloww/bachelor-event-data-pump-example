package de.flowwindustries.edp.order.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.flowwindustries.edp.order.change.events.product.domain.Product;
import de.flowwindustries.edp.order.change.events.user.domain.User;
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

    /**
     * Name for the {@link Product}-Change-Queue.
     */
    @Value("${application.outbox.product-change.queue-name}")
    private String productQueue;

    /**
     * Initialize bean for the ProductChangeQueue.
     * @return a ProductChangeQueue bean
     */
    @Bean
    public Queue productStateChangeQueue() {
        return new Queue(productQueue, true);
    }

    /**
     * Name for the {@link User}-Change-Queue.
     */
    @Value("${application.outbox.user-change.queue-name}")
    private String userQueue;

    /**
     * Initialize bean for the UserChangeQueue.
     * @return a UserChangeQueue bean
     */
    @Bean
    public Queue userStateChangeQueue() {
        return new Queue(userQueue, true);
    }

    /**
     * Initialize and configure {@link RabbitTemplate} to use Jackson2JsonMessageConverter.
     * @param connectionFactory RabbitMQ connection factory
     * @return the configured {@link RabbitTemplate} bean
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Initialize and configure {@link Jackson2JsonMessageConverter} bean to use our configured {@link ObjectMapper}.
     * @return the configured {@link Jackson2JsonMessageConverter} bean.
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    /**
     * Initialize {@link ObjectMapper} and register {@link JavaTimeModule} to allow of date/time parsing.
     * @return the configured {@link ObjectMapper} bean.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
