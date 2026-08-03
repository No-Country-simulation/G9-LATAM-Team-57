package com.energiai.api.service;

import com.energiai.api.model.dto.request.ConsumoEnergeticoRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;

public interface AnalisisEnergeticoService {
    // TODO: define el contrato — qué operaciones ofrece el servicio

    AnalisisEnergeticoResponse analizar(ConsumoEnergeticoRequest request);

}
