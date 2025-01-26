package com.ex.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Manejo de errores de validación
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
                Map<String, List<String>> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors().forEach(error -> {
                        errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                                        .add(error.getDefaultMessage());
                });

                return ResponseEntity.badRequest().body(errors); // Devuelve el mapa con listas de errores
        }

        @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
        public ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Formato de respuesta no aceptado");
        }

        // Manejo de excepciones generales
        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleGeneralException(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado");
        }

}
