package com.example.techstore.controller;

import com.example.techstore.dto.ConversionResponse;
import com.example.techstore.response.ApiResponse;
import com.example.techstore.service.DivisaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController 
@RequestMapping ("api/divisas")
public class DivisaController {
    private final DivisaService divisaService;

    public DivisaController(DivisaService divisaService) {
        this.divisaService = divisaService;
    }

    @Operation(
        summary = "Convertir divisias",
        description = "Convierte un monto de una moneda a otra utilizada la API de Frankfurter"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Conversión realizada correctamente"
    ),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos o moneda inexistente"
    ),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "502",
        description = "Error al comunicarse con el servicio de divisas"
    )
    })
    @GetMapping("/convertir")
    public ApiResponse<ConversionResponse> convertir(
            @Parameter (description = "Monto a convertir")
            @RequestParam Double monto,
            @Parameter (description = "Moneda origen")
            @RequestParam String origen,
            @Parameter (description = "Moneda destino")
            @RequestParam String destino) {

        ConversionResponse conversion =
                divisaService.convertir(monto, origen, destino);

        return new ApiResponse<>(
                200,
                "Conversión realizada correctamente",
                conversion
        );
    }
}
