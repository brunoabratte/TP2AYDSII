package com.example.techstore.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.example.techstore.response.ApiResponse;

import org.springframework.validation.method.ParameterErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensajeError = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación");

        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                mensajeError,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Object>> manejarValidacionLista(HandlerMethodValidationException ex) {

        String mensajeError = ex.getParameterValidationResults().stream()
                .filter(result -> result instanceof ParameterErrors)
                .map(result -> (ParameterErrors) result)
                .flatMap(errors -> errors.getFieldErrors().stream()
                        .map(fieldError -> "Elemento [" + errors.getContainerIndex() + "] - campo '"
                                + fieldError.getField() + "': " + fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        if (mensajeError.isEmpty()) {
            mensajeError = "Error de validación";
        }

        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                mensajeError,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(PorcentajeInvalidoException.class)
    public ResponseEntity<ApiResponse<Object>> manejarPorcentajeInvalido(PorcentajeInvalidoException ex) {
        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> manejarCualquierError(Exception ex) {
        System.out.println("TIPO DE EXCEPCION: " + ex.getClass().getName());

        ApiResponse<Object> respuesta = new ApiResponse<>(
                500,
                "Error: " + ex.getMessage(),
                null
        );
        return ResponseEntity.status(500).body(respuesta);
    }

    @ExceptionHandler(ListaVaciaException.class)
    public ResponseEntity<ApiResponse<Object>> manejarListaVacia(ListaVaciaException ex) {
        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

        @ExceptionHandler(ProductoNoEncontradoException.class)
        public ResponseEntity<ApiResponse<Void>> manejarProductoNoEncontrado(ProductoNoEncontradoException ex) {
            ApiResponse<Void> respuesta = new ApiResponse<>(
                    404,
                    ex.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiResponse<Void>> manejarArgumentoInvalido(
                    IllegalArgumentException ex) {
                
                ApiResponse<Void> respuesta = new ApiResponse<>(
                        400,
                        ex.getMessage(),
                        null
                );
        
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(respuesta);
        }
}