package com.example.techstore.repository;

import com.example.techstore.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialConversionRepository
        extends JpaRepository<HistorialConversion, Integer> {

    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
            String monedaOrigen,
            String monedaDestino
    );
}