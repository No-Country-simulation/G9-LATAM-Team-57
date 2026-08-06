package com.energiai.api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * LA COMANDA TOMADA POR EL MOZO (DTO Request)
 *
 * El registro formal donde el Mozo traslada la selección que el comensal hizo de la carta.
 * No contiene lógica ni secretos de cocina; solo la verdad pura de lo que el cliente dictó
 * sobre su hogar: sus hábitos de consumo, sus equipos y sus rutinas.
 * Es el contrato formal escrito en la libreta del Mozo que viaja directo a los fogones
 * del Cocinero Jefe.
 */

@Getter
@Setter
public class ConsumoEnergeticoRequest {

    private Integer householdSize;
    private Integer hasAc;
    private Boolean homeOffice;
    private HousingType housingType;
    private Integer equipmentCount;
    private Double consumoTotalMesAnterior;
    private PeakUsageLevel peakUsageLevel;

}
