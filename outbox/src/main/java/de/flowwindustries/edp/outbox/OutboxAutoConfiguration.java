package de.flowwindustries.edp.outbox;

import de.flowwindustries.edp.outbox.configuration.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Enables Spring Boot autoconfiguration for this module.
 */
@Configuration
@PropertySource(value = "classpath:outbox-application.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(SpringDataWebProperties.class)
@Import(JacksonAutoConfiguration.class)
@ComponentScan(basePackages = "de.flowwindustries")
@EnableJpaRepositories(basePackages = "de.flowwindustries")
@EntityScan(basePackages = "de.flowwindustries")
public class OutboxAutoConfiguration {
}
