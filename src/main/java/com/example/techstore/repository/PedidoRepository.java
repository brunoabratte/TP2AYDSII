package com.example.techstore.repository;

import com.example.techstore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query ("""
            SELECT DISTINCT p
            FROM Pedido p
            LEFT JOIN FETCH p.cliente c
            LEFT JOIN FETCH p.detalles d
            LEFT JOIN FETCH d.producto prod
            LEFT JOIN FETCH prod.categoria cat
            WHERE (:clienteId IS NULL OR c.id = :clienteId)
                AND (:categoria IS NULL OR LOWER(cat.nombre) = LOWER(:categoria))
                AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde)
                AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta)
                AND (:estado IS NULL OR UPPER(p.estado) = UPPER(:estado))
            """)
            List<Pedido>buscarPedidos(
                @Param ("clienteId")
                Integer clienteId,
                @Param ("categoria") 
                String categoria,
                @Param ("fechaDesde")
                LocalDate fechaDesde,
                @Param ("fechaHasta")
                LocalDate fechaHasta,
                @Param ("estado")
                String estado
            );
}