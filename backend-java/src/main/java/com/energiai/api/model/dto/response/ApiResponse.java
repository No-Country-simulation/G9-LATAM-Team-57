package com.energiai.api.model.dto.response;

import java.util.List;
/*Información que se le devuelve al
* frontend
* */
public record ApiResponse(
        Categoria categoria,
        Double probabilidad,
        List<String> recomendaciones,
        Double costoEstimadoMensual
        ) {
}
