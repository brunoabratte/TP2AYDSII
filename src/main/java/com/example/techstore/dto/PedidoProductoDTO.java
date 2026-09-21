package com.example.techstore.dto;

import java.math.BigDecimal;

public class PedidoProductoDTO {

    private String nombre;
    private String categoria;
    private Integer cantidad;
    private BigDecimal subtotal;

    public PedidoProductoDTO() {
    }

    public PedidoProductoDTO(String nombre, String categoria, Integer cantidad, BigDecimal subtotal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}