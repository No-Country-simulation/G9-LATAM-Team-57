package com.energiai.api.controller;

import com.energiai.api.client.MlModelClient;
import com.energiai.api.client.MlModelClientMock;
import com.energiai.api.client.ResultadoPrediccion;
import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.model.dto.response.ApiResponse;
import com.energiai.api.service.CostoEstimadoService;
import com.energiai.api.service.RecomendacionService;
import com.energiai.api.service.RequestMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalisisEnergeticoController {

    private final MlModelClient mlModelClient;
    private final RecomendacionService recomendacionService;
    private final CostoEstimadoService costoEstimadoService;
    private final RequestMapper requestMapper;

    public AnalisisEnergeticoController(MlModelClient mlModelClient, RecomendacionService recomendacionService, CostoEstimadoService costoEstimadoService, RequestMapper requestMapper) {
        this.mlModelClient = mlModelClient;
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

        //mlModelClient --> realiza el post y recibe la rta
        ResultadoPrediccion resultadoPrediccion = mlModelClient.predict(modelRequest);

        AnalisisEnergeticoResponse modelResponse = resultadoPrediccion.response();

        //Mapeo lo que quiero devolverle al front
        return new ApiResponse(modelResponse.categoria(),
                               modelResponse.probabilidad(),
                               recomendacionService.recomendacionesPara(modelResponse.categoria(),request),
                               costoEstimadoService.calcularCostoMensual(request.consumoTotalMesAnterior(), request.costoPorKwh()),
                               resultadoPrediccion.simulado());
    }
}

