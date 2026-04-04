package com.carpool.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, String>> conflict(IllegalStateException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException ex) {
    String msg =
        ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
            .orElse("Validation failed");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", msg));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> other(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("message", ex.getMessage() == null ? "Error" : ex.getMessage()));
  }
}
