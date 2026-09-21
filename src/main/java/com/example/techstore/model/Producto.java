package com.example.techstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    private String descripcion;

    @Positive(message = "El precio del producto debe ser un valor positivo")
    private BigDecimal precio;

    @Min(value = 0, message = "El stock del producto no puede ser negativo")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Producto() {
    }

    // Constructor utilizado por el Ejercicio 2
    public Producto(Integer id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;

        Categoria categoriaObjeto = new Categoria();
        categoriaObjeto.setNombre(categoria);
        this.categoria = categoriaObjeto;

        this.precio = BigDecimal.valueOf(precio);
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Se mantiene como String para no romper el Ejercicio 2
    public String getCategoria() {
        if (categoria == null) {
            return null;
        }

        return categoria.getNombre();
    }

    public void setCategoria(String categoria) {
        Categoria categoriaObjeto = new Categoria();
        categoriaObjeto.setNombre(categoria);
        this.categoria = categoriaObjeto;
    }

    // Se mantiene como double para no romper el Ejercicio 2
    public double getPrecio() {
        if (precio == null) {
            return 0;
        }

        return precio.doubleValue();
    }

    public void setPrecio(double precio) {
        this.precio = BigDecimal.valueOf(precio);
    }

    public int getStock() {
        if (stock == null) {
            return 0;
        }

        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método para acceder a la entidad Categoria desde Java cuando la necesitemos
    @JsonIgnore
    public Categoria getCategoriaEntity() {
        return categoria;
    }

    @JsonIgnore
    public BigDecimal getPrecioDecimal() {
        return precio;
    }
}