package com.energiai.api.service;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.HousingType;
import com.energiai.api.model.dto.request.PeakUsageLevel;
import com.energiai.api.model.dto.request.PrediccionRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {

    private final RequestMapper mapper = new RequestMapper(30);

    @Test
    void convertMonthlyConsumptionToDailyAverage(){
        AnalisisEnergeticoRequest request = new AnalisisEnergeticoRequest(
                4,
                true,
                true,
                HousingType.CASA,
                10,
                420.0,
                PeakUsageLevel.HIGH);

        PrediccionRequest resultado= mapper.toPrediccionRequest(request);

        assertThat(resultado.avg_energy_consumption_kwh()).isEqualTo(14.0);
        assertThat(resultado.household_size()).isEqualTo(4);
        assertThat(resultado.has_ac()).isEqualTo(1);
        assertThat(resultado.home_office()).isTrue();
        assertThat(resultado.housing_type()).isEqualTo(HousingType.CASA);
        assertThat(resultado.equipment_count()).isEqualTo(10);
        assertThat(resultado.peak_usage_level()).isEqualTo(PeakUsageLevel.HIGH );

    }
}
