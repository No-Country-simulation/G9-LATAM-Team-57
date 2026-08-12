package com.energiai.api.service;

import com.energiai.api.client.MlModelClient;
import com.energiai.api.client.MlModelClientImpl;
import com.energiai.api.model.dto.request.HousingType;
import com.energiai.api.model.dto.request.PeakUsageLevel;
import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.model.dto.response.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import static org.assertj.core.api.Assertions.assertThat;
public class MlModelClientImplTest {
    private MockRestServiceServer mockServer;
    private MlModelClientImpl mlModelClientImpl;

    @BeforeEach
    void setUP(){
        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();
        mlModelClientImpl = new MlModelClientImpl(builder, "http://fastapi.local");
    }

    @Test
    void predictReturnsClassificationFromModelService() {
        String responseBody = """
            {
              "categoria": "Eficiente",
              "probabilidad": 0.87
            }
            """;
        mockServer.expect(requestTo("http://fastapi.local/predict"))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        PrediccionRequest request = new PrediccionRequest(3, 1, true, HousingType.CASA, 5, 14.0, PeakUsageLevel.HIGH);
        AnalisisEnergeticoResponse response = mlModelClientImpl.predict(request);

        assertThat(response.categoria()).isEqualTo(Categoria.Eficiente);
        assertThat(response.probabilidad()).isEqualTo(0.87);
    }
}