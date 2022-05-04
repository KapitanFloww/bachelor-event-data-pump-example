package de.flowwindustries.edp.order.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data-Transfer-Object for errors.
 */
@Data
@Builder
public class ErrorDTO {

    /**
     * HTTP error code.
     */
    private int code;

    /**
     * Error/Exception message.
     */
    private String message;
}
