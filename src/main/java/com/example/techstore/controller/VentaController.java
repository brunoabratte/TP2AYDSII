package com.example.techstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.techstore.dto.EstadisticasDTO;
import com.example.techstore.dto.VentaDTO;
import com.example.techstore.response.ApiResponse;
import com.example.techstore.service.VentaService;

import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.techstore.dto.DescuentoResponseDTO;
import com.example.techstore.exception.ListaVaciaException;
import com.example.techstore.exception.PorcentajeInvalidoException;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController 
@RequestMapping ("/api/ventas")
public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(summary = "Calcular estadísticas de ventas",
            description = "Recibe una lista de ventas y devuelve estadísticas calculadas: total facturado, cantidad de ventas, ticket promedio, venta mayor y menor, y producto más vendido.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estadísticas calculadas con éxito")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de ventas inválidos o lista vacía")
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticasDTO>> obtenerEstadisticas(
            @RequestBody @Valid List<VentaDTO> ventas) {

        if (ventas.isEmpty()) {
            throw new ListaVaciaException("La lista de ventas no puede estar vacía");
        }

        EstadisticasDTO estadisticas = ventaService.calcularEstadisticas(ventas);

        ApiResponse<EstadisticasDTO> respuesta = new ApiResponse<>(
                200,
                "Estadisticas calculadas con exito",
                estadisticas
        );

        return ResponseEntity.ok(respuesta);
        }

    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponse<DescuentoResponseDTO>> aplicarDescuento(
            @RequestBody @Valid List<VentaDTO> ventas,
            @RequestParam double porcentaje) {

        if (porcentaje < 0 || porcentaje > 100) {
            throw new PorcentajeInvalidoException(
                    "El porcentaje debe estar entre 0 y 100");
        }

        DescuentoResponseDTO resultado = ventaService.aplicarDescuento(ventas, porcentaje);

        ApiResponse<DescuentoResponseDTO> respuesta = new ApiResponse<>(
                200,
                "Descuento aplicado con exito",
                resultado
        );

        return ResponseEntity.ok(respuesta);
    }
}
