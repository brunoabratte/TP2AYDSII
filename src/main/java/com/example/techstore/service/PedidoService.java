package com.example.techstore.service;

import com.example.techstore.dto.PedidoProductoDTO;
import com.example.techstore.dto.PedidoResponseDTO;
import com.example.techstore.model.DetallePedido;
import com.example.techstore.model.Pedido;
import com.example.techstore.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponseDTO> buscarPedidos(
            Integer clienteId,
            String categoria,
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            String estado) {

        if (estado != null) {
            estado = estado.toUpperCase();

            if (!estado.equals("PENDIENTE")
                    && !estado.equals("ENVIADO")
                    && !estado.equals("ENTREGADO")
                    && !estado.equals("CANCELADO")) {

                throw new IllegalArgumentException(
                        "El estado debe ser PENDIENTE, ENVIADO, ENTREGADO o CANCELADO"
                );
            }
        }

        List<Pedido> pedidos = pedidoRepository.buscarPedidos(
                clienteId,
                categoria,
                fechaDesde,
                fechaHasta,
                estado
        );

        return pedidos.stream()
                .map(this::convertirPedido)
                .toList();
    }

    private PedidoResponseDTO convertirPedido(Pedido pedido) {

        List<PedidoProductoDTO> productos = pedido.getDetalles()
                .stream()
                .map(this::convertirDetalle)
                .toList();

        BigDecimal totalPedido = productos.stream()
                .map(PedidoProductoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String nombreCliente = pedido.getCliente().getNombre()
                + " "
                + pedido.getCliente().getApellido();

        return new PedidoResponseDTO(
                pedido.getId(),
                nombreCliente,
                pedido.getFechaPedido(),
                pedido.getEstado(),
                totalPedido,
                productos
        );
    }

    private PedidoProductoDTO convertirDetalle(DetallePedido detalle) {

        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        return new PedidoProductoDTO(
                detalle.getProducto().getNombre(),
                detalle.getProducto().getCategoria(),
                detalle.getCantidad(),
                subtotal
        );
    }
}