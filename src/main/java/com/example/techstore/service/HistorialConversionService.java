package com.example.techstore.service;

import com.example.techstore.model.HistorialConversion;
import com.example.techstore.repository.HistorialConversionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialConversionService {

    private final HistorialConversionRepository historialRepository;

    public HistorialConversionService(
            HistorialConversionRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public HistorialConversion guardar(HistorialConversion historial) {
        return historialRepository.save(historial);
    }

    public List<HistorialConversion> buscarHistorial(
            String monedaOrigen,
            String monedaDestino) {

        return historialRepository
                .findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
                        monedaOrigen,
                        monedaDestino
                );
    }
}