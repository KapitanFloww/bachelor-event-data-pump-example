package de.flowwindustries.edp.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * This is an example microservice for managing orders.
 */
@SpringBootApplication
@Import(de.flowwindustries.edp.outbox.OutboxAutoConfiguration.class)
public class OrderApplication {

	/**
	 * Main-Method.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
