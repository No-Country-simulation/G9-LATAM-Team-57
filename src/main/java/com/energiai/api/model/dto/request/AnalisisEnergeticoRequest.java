package com.energiai.api.model.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/* DTO para los datos que envía el usuario al modelo (API
* python) para la clasificación
* */
public record AnalisisEnergeticoRequest(
        @NotNull(message = "No olvide ingresar la cantidad de habitantes.") @Positive(message = "Debe ser mayor que 0") Integer householdSize,
        @NotNull(message = "Complete este campo") Boolean hasAc,
        @NotNull(message = "Complete este campo")  Boolean homeOffice,
        @NotNull(message = "Complete este campo")  HousingType housingType,
        @NotNull(message = "Complete este campo")  @PositiveOrZero(message = "Debe ingresar un valor positivo") Integer equipmentCount,
        @NotNull(message = "Complete este campo")  @PositiveOrZero(message = "Debe ingresar un valor positivo") Double consumoTotalMesAnterior,
        @NotNull(message = "Complete este campo")  PeakUsageLevel peakUsageLevel)

{
}
