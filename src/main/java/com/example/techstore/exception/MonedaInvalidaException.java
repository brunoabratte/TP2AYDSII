package com.example.techstore.exception;

public class MonedaInvalidaException extends RuntimeException {
    public MonedaInvalidaException(String mensaje){
        super(mensaje);
    }
}
