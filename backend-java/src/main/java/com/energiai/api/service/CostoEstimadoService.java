package com.energiai.api.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CostoEstimadoService {

    private final double precioKWHDefault;

    public CostoEstimadoService( @Value("${precio.kwh}") double precioKWHDefault) {
        this.precioKWHDefault = precioKWHDefault;
    }


    public Double calcularCostoMensual(Double consumo, Double precioKWHRequest){
        Double precioKwh= (precioKWHRequest!= null)? precioKWHRequest:precioKWHDefault;
        return consumo* precioKwh;
    }
}
