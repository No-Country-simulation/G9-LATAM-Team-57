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
    @PostMapping("/analisis-energetico")
    public ApiResponse predict(@Valid @RequestBody AnalisisEnergeticoRequest request){

        PrediccionRequest modelRequest= requestMapper.toPrediccionRequest(request);

        AnalisisEnergeticoResponse modelResponse = modelClient.predict(modelRequest);

        return new ApiResponse(modelResponse.categoria(),
                               modelResponse.probabilidad(),
                               recomendacionService.recomendacionesPara(modelResponse.categoria(),request),
                               costoEstimadoService.calcularCostoMensual(request.consumoTotalMesAnterior())
                                );
    }
}

