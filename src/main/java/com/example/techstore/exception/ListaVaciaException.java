package com.example.techstore.exception;

public class ListaVaciaException extends RuntimeException {
    public ListaVaciaException(String mensaje) {
        super(mensaje);
    }
}