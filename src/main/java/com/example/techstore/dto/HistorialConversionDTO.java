package com.example.techstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialConversionDTO {

    private LocalDateTime fecha;
    private BigDecimal tasaCambio;

    public HistorialConversionDTO() {
    }

    public HistorialConversionDTO(LocalDateTime fecha, BigDecimal tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }
}