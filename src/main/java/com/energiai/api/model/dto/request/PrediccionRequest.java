package com.energiai.api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/* DTO que mapea la información que le mando al
*   modelo (API-python)
* */
public record PrediccionRequest(
        Integer household_size,
        Integer has_ac,
        Boolean home_office,
        HousingType housing_type,
        Integer equipment_count,
        Double avg_energy_consumption_kwh,
        PeakUsageLevel peak_usage_level
) {

}
