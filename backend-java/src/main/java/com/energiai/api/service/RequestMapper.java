package com.energiai.api.service;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.PrediccionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.energiai.api.client.MlPrediccionRequest;


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
          request.hasAc(),
          request.homeOffice(),
          request.housingType(),
          request.equipmentCount(),
          request.consumoTotalMesAnterior()/diasDelMes,
          request.peakUsageLevel()
        );
    }

    public MlPrediccionRequest toMlPrediccionRequest(AnalisisEnergeticoRequest request){
        MlPrediccionRequest mlPrediccionRequest = new MlPrediccionRequest();

        mlPrediccionRequest.setHouseholdSize(request.householdSize());
        mlPrediccionRequest.setHasAc(request.hasAc());
        mlPrediccionRequest.setHomeOffice(request.homeOffice());
        mlPrediccionRequest.setHousingType(request.housingType().toValue());
        mlPrediccionRequest.setEquipmentCount(request.equipmentCount());
        mlPrediccionRequest.setAvgEnergyConsumptionKwh(request.consumoTotalMesAnterior() / diasDelMes);
        mlPrediccionRequest.setPeakUsageLevel(request.peakUsageLevel().name());

        return mlPrediccionRequest;
    }
}
