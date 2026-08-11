package com.energiai.api.dto;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.HousingType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DtoDeserializacionTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializesAnalisisEnergeticoRequestFromJson() throws Exception {
        String json = """
            {
              "householdSize": 4,
              "hasAc": 1,
              "homeOffice": true,
              "housingType": "CASA",
              "equipmentCount": 10,
              "consumoTotalMesAnterior": 420,
              "peakUsageLevel": "HIGH"
            }
            """;

        AnalisisEnergeticoRequest request = objectMapper.readValue(json, AnalisisEnergeticoRequest.class);

        assertThat(request.housingType()).isEqualTo(HousingType.CASA);
        assertThat(request.householdSize()).isEqualTo(4);
        assertThat(request.consumoTotalMesAnterior()).isEqualTo(420.0);
    }
}
