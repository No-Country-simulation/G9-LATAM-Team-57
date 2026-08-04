package com.energiai.api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
/* DTO para los datos que envía el usuario al modelo (API
* python) para la clasificación
* */
public record AnalisisEnergeticoRequest(
        @NotNull @PositiveOrZero Integer household_size,
        @NotNull @PositiveOrZero Integer has_ac,
        @NotNull Boolean home_office,
        @NotNull HousingType housingType,
        @NotNull @PositiveOrZero Integer equipmentCount,
        @NotNull @PositiveOrZero Double consumoTotalMesAnterior,
        @NotNull String peakUsageLevel)

{
}
