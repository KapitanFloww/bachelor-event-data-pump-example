package de.flowwindustries.edp.order.controller;

import de.flowwindustries.edp.order.controller.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.internalServerError().body(ErrorDTO.builder()
                .code(500)
                .message(exception.getMessage())
                .build());
    }
}
