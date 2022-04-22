package de.flowwindustries.edp.outbox;

import de.flowwindustries.edp.outbox.OutboxAutoConfiguration;
import de.flowwindustries.edp.outbox.configuration.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import(OutboxAutoConfiguration.class)
@PropertySource(value = "classpath:outbox-application-dev.yml", factory = YamlPropertySourceFactory.class)
public class OutboxTestApplication {
}
