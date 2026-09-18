package com.example.techstore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.example.techstore.dto.ConversionResponse;
import com.example.techstore.dto.FrankfurterResponse;
import com.example.techstore.exception.MonedaInvalidaException;
import com.example.techstore.exception.ServicioExternoException;

@Service
public class DivisaService {

    private final RestClient restClient;

    public DivisaService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.frankfurter.dev")
                .build();
    }

    public ConversionResponse convertir(Double monto, String origen, String destino) {
        if (monto <= 0) {
            throw new IllegalArgumentException(
                "El monto debe ser mayor que cero."
            );
        }

        if (origen == null || !origen.matches("[A-Za-z]{3}")) {
            throw new IllegalArgumentException(
                "La moneda de origen debe tener exactamente 3 letras"
            );
        }

        if (destino == null || !destino.matches("[A-Za-z]{3}")){
            throw new IllegalArgumentException(
                "La moneda de destino debe tener exactamente 3 letras"
            );
        }

        FrankfurterResponse respuesta;

        try {
            respuesta = restClient.get().uri("/v2/rate/{origen}/{destino}", origen, destino).retrieve().body(FrankfurterResponse.class);
        } catch (RestClientResponseException ex) {
            
            if (ex.getStatusCode().value() == 422) {
                throw new MonedaInvalidaException("La moneda de origen o destino no es valida");
            }
            throw new ServicioExternoException("No se pudo obtener informacion del servicio de divisas");
        } catch (Exception ex) {
            throw new ServicioExternoException("No se pudo conectar con el servicio de divisas");
        }

        Double tasaCambio = respuesta.getRate();

        Double montoConvertido = monto * tasaCambio;

        ConversionResponse conversion = new ConversionResponse();

        conversion.setMontoOriginal(monto);
        conversion.setMonedaOrigen(origen);
        conversion.setMonedaDestino(destino);
        conversion.setTasaCambio(tasaCambio);
        conversion.setMontoConvertido(montoConvertido);
        conversion.setFecha(respuesta.getDate());

        return conversion;
    }
}