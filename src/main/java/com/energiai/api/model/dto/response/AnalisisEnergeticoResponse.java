package com.energiai.api.model.dto.response;
/* DTO que deserializa la respuesta JSON
*  que devuelve la API de python
* */
public record AnalisisEnergeticoResponse(
        Categoria categoria,
        Double probabilidad,
        Probabilidades probabilidades) {
}
