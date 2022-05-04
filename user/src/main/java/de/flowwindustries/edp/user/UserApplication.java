package de.flowwindustries.edp.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * This is an example microservice for managing users.
 */
@SpringBootApplication
@Import(de.flowwindustries.edp.outbox.OutboxAutoConfiguration.class)
public class UserApplication {

	/**
	 * Main-Method.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
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
