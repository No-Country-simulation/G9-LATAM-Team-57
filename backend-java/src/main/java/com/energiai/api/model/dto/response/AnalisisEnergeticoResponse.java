package com.energiai.api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * EL PLATO SERVIDO (DTO Response)
 *
 * La obra terminada. La vajilla fina donde convergen el diagnóstico de la repostería (IA/Mock)
 * y los acompañamientos calculados por el Cocinero Jefe (Costo mensual y Recomendaciones).
 * Es el deleite que el Mozo lleva de regreso a la mesa del comensal.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalisisEnergeticoResponse {

    private String categoria;
    private Double probabilidad;
    private List<String> recomendaciones;
    private Double costoEstimadoMensual;

}