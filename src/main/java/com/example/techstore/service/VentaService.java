package com.example.techstore.service;

import org.springframework.stereotype.Service;
import com.example.techstore.dto.VentaDTO;
import com.example.techstore.dto.EstadisticasDTO;
import com.example.techstore.dto.VentaConDescuentoDTO;
import com.example.techstore.dto.DescuentoResponseDTO;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.example.techstore.dto.VentaConImporteDTO;

@Service
public class VentaService {

    public EstadisticasDTO calcularEstadisticas(List<VentaDTO> ventas) {
        EstadisticasDTO estadisticas = new EstadisticasDTO();

        double total = ventas.stream()
                .mapToDouble(venta -> venta.getCantidad() * venta.getPrecioUnitario())
                .sum();
        estadisticas.setTotalFacturado(total);

        int cantidadVentas = ventas.size();
        estadisticas.setCantidadVentas(cantidadVentas);

        double ticketPromedio = cantidadVentas > 0 ? total / cantidadVentas : 0;
        estadisticas.setTicketPromedio(ticketPromedio);

        VentaDTO ventaMayor = ventas.stream()
                .max(Comparator.comparingDouble(venta -> venta.getCantidad() * venta.getPrecioUnitario()))
                .orElse(null);
        if (ventaMayor != null) {
            double importeMayor = ventaMayor.getCantidad() * ventaMayor.getPrecioUnitario();
            estadisticas.setVentaMayor(new VentaConImporteDTO(
                    ventaMayor.getProducto(),
                    ventaMayor.getCantidad(),
                    ventaMayor.getPrecioUnitario(),
                    importeMayor
            ));
        }

        VentaDTO ventaMenor = ventas.stream()
                .min(Comparator.comparingDouble(venta -> venta.getCantidad() * venta.getPrecioUnitario()))
                .orElse(null);
        if (ventaMenor != null) {
            double importeMenor = ventaMenor.getCantidad() * ventaMenor.getPrecioUnitario();
            estadisticas.setVentaMenor(new VentaConImporteDTO(
                    ventaMenor.getProducto(),
                    ventaMenor.getCantidad(),
                    ventaMenor.getPrecioUnitario(),
                    importeMenor
            ));
        }

        Map<String, Integer> cantidadPorProducto = ventas.stream()
                .collect(Collectors.groupingBy(
                        VentaDTO::getProducto,
                        Collectors.summingInt(VentaDTO::getCantidad)
                ));

        String productoMasVendido = cantidadPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        estadisticas.setProductoMasVendido(productoMasVendido);

        return estadisticas;
    }

    public DescuentoResponseDTO aplicarDescuento(List<VentaDTO> ventas, double porcentaje) {

        List<VentaConDescuentoDTO> ventasConDescuento = ventas.stream()
                .map(venta -> {
                    double montoOriginal = venta.getCantidad() * venta.getPrecioUnitario();
                    double montoConDescuento = montoOriginal * (1 - porcentaje / 100);

                    return new VentaConDescuentoDTO(
                            venta.getProducto(),
                            venta.getCantidad(),
                            venta.getPrecioUnitario(),
                            montoConDescuento
                    );
                })
                .collect(Collectors.toList());

        double totalConDescuento = ventasConDescuento.stream()
                .mapToDouble(VentaConDescuentoDTO::getMontoConDescuento)
                .sum();

        return new DescuentoResponseDTO(ventasConDescuento, totalConDescuento);
    }
}