package com.energiai.api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ConsumoEnergeticoRequest (
        @NotNull @PositiveOrZero Integer household_size,
        @NotNull @PositiveOrZero Integer has_ac,
        @NotNull Boolean home_office,
        @NotNull HousingType housingType,
        @NotNull @PositiveOrZero Integer equipment_count,
        @NotNull @PositiveOrZero Double avg_energy_consumption_kwh,
        @NotNull String peak_usage_level)

{
}
