package de.flowwindustries.edp.order.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {

    private int code;
    private String message;
}
