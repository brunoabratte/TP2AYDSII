package com.example.techstore.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoResponseDTO {

    private Integer pedidoId;
    private String cliente;
    private LocalDate fecha;
    private String estado;
    private BigDecimal totalPedido;
    private List<PedidoProductoDTO> productos;

    public PedidoResponseDTO() {
    }

    public PedidoResponseDTO(
            Integer pedidoId,
            String cliente,
            LocalDate fecha,
            String estado,
            BigDecimal totalPedido,
            List<PedidoProductoDTO> productos) {

        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.productos = productos;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<PedidoProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProductoDTO> productos) {
        this.productos = productos;
    }
}