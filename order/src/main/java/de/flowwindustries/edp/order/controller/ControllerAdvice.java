package de.flowwindustries.edp.order.controller;

import de.flowwindustries.edp.order.controller.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Advice for RestController.
 */
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Exception-Handler to handle {@link IllegalArgumentException}s.
     * @param exception the exception to handle
     * @return an {@link ErrorDTO} containing status code and error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.internalServerError().body(ErrorDTO.builder()
                .code(500)
                .message(exception.getMessage())
                .build());
    }
}
