package com.energiai.api.controller;

import com.energiai.api.client.MlModelClient;
import com.energiai.api.client.ResultadoPrediccion;
import com.energiai.api.model.dto.request.HousingType;
import com.energiai.api.model.dto.request.PeakUsageLevel;
import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.model.dto.response.Categoria;
import com.energiai.api.service.CostoEstimadoService;
import com.energiai.api.service.RecomendacionService;
import com.energiai.api.service.RequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PredictControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MlModelClient mlModelClient;

    @MockBean
    private RecomendacionService recomendacionService;

    @MockBean
    private CostoEstimadoService costoEstimadoService;

    @MockBean
    private RequestMapper requestMapper;

    private static final String VALID_PAYLOAD = """
        {
          "householdSize": 4,
          "hasAc": 1,
          "homeOffice": true,
          "housingType": "CASA",
          "equipmentCount": 10,
          "consumoTotalMesAnterior": 420,
          "peakUsageLevel": "HIGH",
          "costoPorKwh":0.8
        }
        """;

    @Test
    void returnsClassificationRecommendationsAndCost() throws Exception {

        PrediccionRequest mappedRequest = new PrediccionRequest(4,
                                                            1,
                                                        true,
                                                                   HousingType.CASA,
                                                    10,
                                           14.0,
                                                                   PeakUsageLevel.HIGH);

        when(requestMapper.toPrediccionRequest(any())).thenReturn(mappedRequest);
        when(mlModelClient.predict(mappedRequest))
                .thenReturn(new ResultadoPrediccion(new AnalisisEnergeticoResponse(Categoria.Ineficiente, 0.81), false));


        when(recomendacionService.recomendacionesPara(eq(Categoria.Ineficiente), any()))
                .thenReturn(List.of("Recomendación 1", "Recomendación 2", "Recomendación 3"));
        when(costoEstimadoService.calcularCostoMensual(420.0,0.8)).thenReturn(336.0);

        mockMvc.perform(post("/analisis-energetico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PAYLOAD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoria").value("Ineficiente"))
                .andExpect(jsonPath("$.probabilidad").value(0.81))
                .andExpect(jsonPath("$.recomendaciones.length()").value(3))
                .andExpect(jsonPath("$.costoEstimadoMensual").value(336.0));
    }

    @Test
    void returns400WhenHousingTypeInvalid() throws Exception {
        String invalidPayload = VALID_PAYLOAD.replace("CASA", "Mansion");

        mockMvc.perform(post("/analisis-energetico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns503WhenModelServiceUnavailable() throws Exception {
        when(requestMapper.toPrediccionRequest(any()))
                .thenReturn(new PrediccionRequest(4, 1, true, HousingType.CASA, 10, 14.0, PeakUsageLevel.HIGH));
        when(mlModelClient.predict(any())).thenThrow(new ResourceAccessException("timeout"));

        mockMvc.perform(post("/analisis-energetico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PAYLOAD))
                .andExpect(status().isServiceUnavailable());
    }
}
