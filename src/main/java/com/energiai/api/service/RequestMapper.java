package com.energiai.api.service;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.PrediccionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/* Mapea el DTO con los datos que se recibe desde el frontend
* al que espera API-python.
diasDelMes es una variable de entorno
* */
@Component
public class RequestMapper {

    private final int diasDelMes;

    public RequestMapper(@Value("${dias.mes}") int diasDelMes) {
        this.diasDelMes = diasDelMes;
    }
    public PrediccionRequest toPrediccionRequest(AnalisisEnergeticoRequest request){
        return new PrediccionRequest(
          request.householdSize(),
          request.hasAc()? 1:0 ,
          request.homeOffice(),
          request.housingType(),
          request.equipmentCount(),
          request.consumoTotalMesAnterior()/diasDelMes,
          request.peakUsageLevel()
        );
    }
}
