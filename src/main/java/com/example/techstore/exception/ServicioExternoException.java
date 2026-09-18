package com.example.techstore.exception;

public class ServicioExternoException extends RuntimeException{
    public ServicioExternoException(String mensaje) {
        super(mensaje);
    }
}
