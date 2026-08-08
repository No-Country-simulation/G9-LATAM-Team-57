package com.energiai.api.service;

import com.energiai.api.model.dto.request.PeakUsageLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CostoEstimadoService {

    private final double precioKWH;

    public CostoEstimadoService( @Value("${precio.kwh}") double precioKWH) {
        this.precioKWH = precioKWH;
    }


    public Double calcularCostoMensual(Double consumo){

        return consumo*precioKWH;
    }
}
