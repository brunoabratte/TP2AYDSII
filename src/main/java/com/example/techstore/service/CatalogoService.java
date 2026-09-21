package com.example.techstore.service;

import com.example.techstore.model.Producto;
import org.springframework.stereotype.Service;
import com.example.techstore.exception.ProductoNoEncontradoException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogoService {

    private List<Producto> productos = new ArrayList<>();

    public CatalogoService() {

        productos.add(new Producto(1, "Mouse inalámbrico", "Perifericos", 5000, 20));
        productos.add(new Producto(2, "Teclado mecánico", "Perifericos", 25000, 10));
        productos.add(new Producto(3, "Monitor 24 pulgadas", "Monitores", 120000, 8));
        productos.add(new Producto(4, "Auriculares", "Audio", 35000, 15));
        productos.add(new Producto(5, "Webcam HD", "Camaras", 45000, 12));
        productos.add(new Producto(6, "SSD 1TB", "Almacenamiento", 80000, 7));
        productos.add(new Producto(7, "Memoria RAM 16GB", "Componentes", 60000, 10));
        productos.add(new Producto(8, "Placa de video", "Componentes", 300000, 5));
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }


    public List<Producto> buscarProductos(String categoria, Double precioMin, Double precioMax) {

        return productos.stream()
                .filter(producto -> categoria == null || producto.getCategoria().equalsIgnoreCase(categoria))
                .filter(producto -> precioMin == null || producto.getPrecio() >= precioMin)
                .filter(producto -> precioMax == null || producto.getPrecio() <= precioMax)
                .toList();
    }

    public List<Producto> ordenarProductos(String criterio, String orden) {

    final String ordenFinal;

    if (orden == null) {
        ordenFinal = "asc";
    } else {
        ordenFinal = orden;
    }

    return productos.stream()
            .sorted((p1, p2) -> {

                int resultado;

                if (criterio.equalsIgnoreCase("precio")) {
                    resultado = Double.compare(p1.getPrecio(), p2.getPrecio());
                } else {
                    resultado = p1.getNombre().compareToIgnoreCase(p2.getNombre());
                }

                if (ordenFinal.equalsIgnoreCase("desc")) {
                    return -resultado;
                }

                return resultado;
            })
            .toList();
    }

    public Producto agregarProducto(Producto producto) {

        int nuevoId = productos.stream()
            .mapToInt(Producto::getId)
            .max()
            .orElse(0) + 1;
            
        producto.setId(nuevoId);

        return producto;
    }

    public Producto modificarStock(Integer id, int cantidad) {

        for (Producto producto : productos) {

            if (id.equals(producto.getId())) {

                    int nuevoStock = producto.getStock() + cantidad;

                    if (nuevoStock < 0) {
                        throw new IllegalArgumentException("El stock no puede quedar por debajo de 0");
                    }

                    producto.setStock(nuevoStock);

                    return producto;
                }
            }

            throw new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado");
        }

        public void eliminarProducto(Integer id) {

            for (Producto producto : productos) {

                if (id.equals(producto.getId())) {
                    productos.remove(producto);
                    return;
                }
            }

            throw new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado");
        }
}