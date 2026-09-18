package com.example.techstore.dto;

public class ConversionResponse {
    private Double montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private Double tasaCambio;
    private Double montoConvertido;
    private String fecha;

    public Double getMontoOriginal() {
        return montoOriginal;
    }

    public void setMontoOriginal(Double montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }

    public Double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(Double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMontoConvertido() {
        return montoConvertido;
    }

    public void setMontoConvertido(Double montoConvertido) {
        this.montoConvertido = montoConvertido;
    }
}
