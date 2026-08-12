package com.energiai.api.client;

import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;

public record ResultadoPrediccion(AnalisisEnergeticoResponse response, boolean simulado) {
}
