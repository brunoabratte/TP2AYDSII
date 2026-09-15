package com.example.techstore.controller;

import com.example.techstore.model.Producto;
import com.example.techstore.service.CatalogoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.example.techstore.response.ApiResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation (summary = "Obtener todos los productos del catálogo",
            description = "Devuelve una lista de todos los productos disponibles en el catálogo.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Productos obtenidos correctamente"
        )
    })
    @GetMapping
    public ApiResponse<List<Producto>> obtenerProductos() {
        return new ApiResponse<>(
                200,
                "Productos obtenidos correctamente",
                catalogoService.obtenerProductos()
        );
    }

        @Operation(
        summary = "Buscar productos",
        description = "Busca productos por categoría y/o rango de precios"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Productos encontrados correctamente"
        )
    })
    @GetMapping("/buscar")
    public ApiResponse<List<Producto>> buscarProductos(

            @Parameter(
                description = "Categoría del producto",
                example = "Perifericos"
            )
            @RequestParam(required = false) String categoria,

            @Parameter(
                description = "Precio mínimo",
                example = "5000"
            )
            @RequestParam(required = false) Double precioMin,

            @Parameter(
                description = "Precio máximo",
                example = "100000"
            )
            @RequestParam(required = false) Double precioMax) {

        List<Producto> productos =
                catalogoService.buscarProductos(categoria, precioMin, precioMax);

        return new ApiResponse<>(
                200,
                "Productos encontrados correctamente",
                productos
        );
    }

    @Operation (summary = "Ordenar Productos",
        description = "Devuelve los productos ordenados por nombre o precio, de forma ascendente o descendente"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Productos ordenados correctamente"
        )
    })
    @GetMapping("/ordenar")
    public ApiResponse<List<Producto>> ordenarProductos(
        @Parameter (
            description = "Criterio de ordenamiento: nombre o precio",
            example = "precio"
        )
        @RequestParam String criterio,
        
        @Parameter (
            description = "Orden: asc o desc",
            example = "asc"
        )
        @RequestParam(required = false) String orden) {

        return new ApiResponse<>(
                200,
                "Productos ordenados correctamente",
                catalogoService.ordenarProductos(criterio, orden)
        );
    }

    @Operation(
        summary = "Agregar un producto",
        description = "Agrega un nuevo producto al catálogo"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Producto agregado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Los datos del producto son inválidos"
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Producto> agregarProducto(
            @Valid @RequestBody Producto producto) {

        Producto productoAgregado = catalogoService.agregarProducto(producto);

        return new ApiResponse<>(
                201,
                "Producto agregado correctamente",
                productoAgregado
        );
    }

    @Operation(
        summary = "Modificar stock",
        description = "Aumenta o disminuye el stock de un producto"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Stock modificado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "El stock no puede quedar negativo"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })

    @PutMapping("/{id}/stock")
    public ApiResponse<Producto> modificarStock(
        @Parameter (
            description = "ID del producto",
            example = "1"
        )
        @PathVariable Integer id,
        @Parameter (
            description = "Cantidad a modificar. Positiva aumenta el stock y negativa lo disminuye",
            example = "5"
        )
            @RequestParam int cantidad) {

        Producto productoModificado =
                catalogoService.modificarStock(id, cantidad);

        return new ApiResponse<>(
                200,
                "Stock modificado correctamente",
                productoModificado
        );
    }



    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto del catálogo mediante su ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "Producto eliminado correctamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(
    
            @Parameter(
                description = "ID del producto a eliminar",
                example = "1"
            )
            @PathVariable Integer id) {
            
        catalogoService.eliminarProducto(id);
    }
}