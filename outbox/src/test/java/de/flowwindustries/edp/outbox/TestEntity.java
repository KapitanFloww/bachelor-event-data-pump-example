package de.flowwindustries.edp.outbox;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

@Data
@Builder
public class TestEntity {
    @Id
    private String identifier;
    private String name;
}
