package com.energiai.api.controller;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.model.dto.response.ApiResponse;
import com.energiai.api.service.CostoEstimadoService;
import com.energiai.api.service.ModelClient;
import com.energiai.api.service.RecomendacionService;
import com.energiai.api.service.RequestMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalisisEnergeticoController {

    private final ModelClient modelClient;
    private final RecomendacionService recomendacionService;
    private final CostoEstimadoService costoEstimadoService;
    private final RequestMapper requestMapper;

    public AnalisisEnergeticoController(ModelClient modelClient, RecomendacionService recomendacionService, CostoEstimadoService costoEstimadoService, RequestMapper requestMapper) {
        this.modelClient = modelClient;
        this.recomendacionService = recomendacionService;
        this.costoEstimadoService = costoEstimadoService;
        this.requestMapper = requestMapper;
    }

    /*Recibe la request que envía el frontend, los mapeo con
    * los datos para el  POST a la API de python , luego hace
    *  el POST y devuelve la respuesta de la API de python*/
    @PostMapping("/analisis-energetico")
    public ApiResponse predict(@Valid @RequestBody AnalisisEnergeticoRequest request){
        //requestMapper --> prepara los datos para el modelo de prediccion
        PrediccionRequest modelRequest= requestMapper.toPrediccionRequest(request);
        //modelClient --> realiza el post y recibe la rta
        AnalisisEnergeticoResponse modelResponse = modelClient.predict(modelRequest);
        //Mapeo lo que quiero devolverle al front
        return new ApiResponse(modelResponse.categoria(),
                               modelResponse.probabilidad(),
                               recomendacionService.recomendacionesPara(modelResponse.categoria(),request),
                               costoEstimadoService.calcularCostoMensual(request.consumoTotalMesAnterior(), request.costoPorKwh())
                                );
    }
}

