package com.example.techstore.controller;

import com.example.techstore.dto.ConversionResponse;
import com.example.techstore.response.ApiResponse;
import com.example.techstore.service.DivisaService;
import com.example.techstore.dto.HistorialConversionDTO;
import com.example.techstore.model.HistorialConversion;
import com.example.techstore.service.HistorialConversionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;


@RestController 
@RequestMapping ("api/divisas")
public class DivisaController {

    private final DivisaService divisaService;

    private final HistorialConversionService historialConversionService;

    public DivisaController(DivisaService divisaService, HistorialConversionService historialConversionService) {
        this.divisaService = divisaService;
        this.historialConversionService = historialConversionService;
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

    @Operation(
        summary = "Consultar y guardar conversión",
        description = "Consulta una tasa de cambio, realiza la conversión y guarda la consulta en el historial"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Conversión consultada y guardada correctamente"
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
    @PostMapping("/consultar")
    public ApiResponse<ConversionResponse> consultar(
        @Parameter(
            description = "Monto a convertir",
            example = "100",
            required = true
        )
        @RequestParam Double monto,

        @Parameter(
            description = "Moneda de origen en formato de 3 letras",
            example = "USD",
            required = true
        )
        @RequestParam String origen,

        @Parameter(
            description = "Moneda de destino en formato de 3 letras",
            example = "ARS",
            required = true
        )
        @RequestParam String destino) {
            ConversionResponse conversion =
                divisaService.convertir(monto, origen, destino);

            divisaService.guardarEnHistorial(conversion);

            return new ApiResponse<>(
                200,
                "Conversion consultada y guardada correctamente",
                conversion
            );
        }

        @Operation(
            summary = "Consultar historial de conversiones",
            description = "Obtiene el historial de conversiones para un par de monedas, ordenado de más reciente a más antiguo"
        )
        @io.swagger.v3.oas.annotations.responses.ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Historial obtenido correctamente"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Datos de entrada inválidos"
            )
        })
        @GetMapping("/historial")
        public ApiResponse<List<HistorialConversionDTO>> historial(
            @Parameter(
                description = "Moneda de origen en formato de 3 letras",
                example = "USD",
                required = true
            )
            @RequestParam String origen,

            @Parameter(
                description = "Moneda de destino en formato de 3 letras",
                example = "ARS",
                required = true
            )
            @RequestParam String destino
        ) {
            List<HistorialConversion> historial = historialConversionService.buscarHistorial(origen, destino);

            List<HistorialConversionDTO> resultado = historial.stream().
                map(h -> new HistorialConversionDTO(
                    h.getFechaConsulta(),
                    h.getTasa()
                ))
                .collect(Collectors.toList());

            return new ApiResponse<>(
                200,
                "Historial obtenido correctamente",
                resultado
            );
        }
}
