package com.example.techstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.techstore.dto.ClienteDTO;
import com.example.techstore.model.Cliente;
import com.example.techstore.response.ApiResponse;
import com.example.techstore.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping 
    @Operation (
        summary = "Crear un cliente",
        description = "Registra un nuevo cliente en la base de datos"
    )
    @ApiResponses ({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Cliente creado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Datos inválidos"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<Cliente>> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente clienteCreado = clienteService.crearCliente(clienteDTO);

        ApiResponse<Cliente> repsuesta = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Cliente creado correctamente",
            clienteCreado
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(repsuesta);
    }

    @PostMapping ("/validado")
    @Operation (
        summary = "Crear un cliente validado",
        description = "Registra un nuevo cliente aplicando validaciones de los datos recibidos"
    )
    @ApiResponses ({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "Cliente creado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Datos inválidos"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Error interno del servidor"
        )
    })
    public ResponseEntity<ApiResponse<Cliente>> crearClienteValidado(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente clienteCreado = clienteService.crearClienteValidado(clienteDTO);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Cliente creado correctamente",
                    clienteCreado
            ));
    }
}
