package com.example.techstore.controller;

import com.example.techstore.dto.PedidoResponseDTO;
import com.example.techstore.response.ApiResponse;
import com.example.techstore.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar pedidos",
            description = "Busca pedidos aplicando filtros opcionales"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse (responseCode = "200", description = "Pedidos encontrados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Filtros inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPedidos(

            @Parameter (
                description = "ID del cliente",
                example = "6",
                required = false
            )    
            @RequestParam(required = false)
            Integer clienteId,

            @Parameter(
                description = "Categoría de los productos del pedido",
                example = "Perifericos",
                required = false
            )
            @RequestParam(required = false)
            String categoria,

            @Parameter (
                description = "Fecha inicial del período de búsqueda",
                example = "2026-09-01",
                required = false
            )
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaDesde,

            @Parameter(
                description = "Fecha final del período de búsqueda",
                example = "2026-09-30",
                required = false
                )
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fechaHasta,

            @Parameter(
                description = "Estado del pedido: PENDIENTE, ENVIADO, ENTREGADO o CANCELADO",
                example = "PENDIENTE",
                required = false
            )
            @RequestParam(required = false)
            String estado) {

        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidos(
                clienteId,
                categoria,
                fechaDesde,
                fechaHasta,
                estado
        );

        ApiResponse<List<PedidoResponseDTO>> respuesta =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Pedidos obtenidos correctamente",
                        pedidos
                );

        return ResponseEntity.ok(respuesta);
    }
}