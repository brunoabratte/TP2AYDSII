package com.example.techstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class VentaDTO {
    @NotBlank (message = "El campo 'producto' no puede estar vacío")
    private String producto;

    @Positive(message = "La cantidad sera mayor a 0")
    private int cantidad;

    @Positive (message = "El precio sera mayor a 0")
    private double precioUnitario;

    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
