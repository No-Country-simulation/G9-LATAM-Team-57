package com.energiai.api.client;


import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;

public interface MlModelClient {

    ResultadoPrediccion predict(PrediccionRequest datos);

}