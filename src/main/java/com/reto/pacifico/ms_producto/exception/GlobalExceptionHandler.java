package com.reto.pacifico.ms_producto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String,String>>> handleValidation(WebExchangeBindException ex) {
        String message = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        if (message.isEmpty()) {
            message = "Solicitud inválida";
        }
        return Mono.just(ResponseEntity.badRequest().body(Map.of("error", message)));
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Map<String,String>>> handle(RuntimeException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage())));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String,String>>> handleBad(IllegalArgumentException ex) {
        return Mono.just(ResponseEntity.badRequest().body(Map.of("error", ex.getMessage())));
    }
}
