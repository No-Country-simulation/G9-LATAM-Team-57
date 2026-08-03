package com.energiai.api.service.impl;

import com.energiai.api.service.AnalisisEnergeticoService;
import com.energiai.api.model.dto.request.ConsumoEnergeticoRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;

public class AnalisisEnergeticoServiceImpl implements AnalisisEnergeticoService {

    @Override
    public AnalisisEnergeticoResponse analizar(ConsumoEnergeticoRequest request) {
        // TODO: acá va la lógica: llamar al client (Python o mock),
        // calcular el costo estimado, armar la respuesta
        return null;
    }

}
