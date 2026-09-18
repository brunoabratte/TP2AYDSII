package com.example.techstore.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.example.techstore.response.ApiResponse;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.validation.method.ParameterErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Object>> manejarValidacion(MethodArgumentNotValidException ex) {

        Map<String, String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (mensajeExistente, mensajeNuevo) -> mensajeExistente
                ));

        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Hay errores de validación",
                errores
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
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
        public ResponseEntity<ApiResponse<Void>> manejarIllegalArgumentException(
                IllegalArgumentException ex) {

                ApiResponse<Void> respuesta = new ApiResponse<>(
                        400,
                        ex.getMessage(),
                        null
                        );

                return ResponseEntity.badRequest().body(respuesta);
        }

        @ExceptionHandler(MonedaInvalidaException.class)
        public ResponseEntity<ApiResponse<Object>> manejarMonedaInvalida(
                MonedaInvalidaException ex) {
                        ApiResponse<Object> respuesta = new ApiResponse<>(
                                400,
                                ex.getMessage(), 
                                null
                        );

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }

        @ExceptionHandler(ServicioExternoException.class)
        public ResponseEntity<ApiResponse<Object>> manejarServicioExterno(ServicioExternoException ex) {
                ApiResponse<Object> respuesta = new ApiResponse<>(
                        502,
                        ex.getMessage(),
                        null
                );

                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(respuesta);
        }

        @ExceptionHandler(EmailDuplicadoException.class)
        public ResponseEntity<ApiResponse<Object>> manejarEmailDuplicado(
                EmailDuplicadoException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
        }
}