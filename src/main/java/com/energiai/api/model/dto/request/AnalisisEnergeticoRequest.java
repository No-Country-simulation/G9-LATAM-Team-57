package com.energiai.api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
/* DTO para los datos que envía el usuario al modelo (API
* python) para la clasificación
* */
public record AnalisisEnergeticoRequest(
        @NotNull @PositiveOrZero Integer householdSize,
        @NotNull @PositiveOrZero Integer hasAc,
        @NotNull Boolean homeOffice,
        @NotNull HousingType housingType,
        @NotNull @PositiveOrZero Integer equipmentCount,
        @NotNull @PositiveOrZero Double consumoTotalMesAnterior,
        @NotNull PeakUsageLevel peakUsageLevel)

{
}
