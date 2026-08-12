package com.energiai.api.client;

import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;



@Component
public class MlModelClientImpl implements MlModelClient {

    private final RestClient restClient;

    public MlModelClientImpl(RestClient.Builder restClientBuilder, @Value("${fastapi.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public ResultadoPrediccion predict(PrediccionRequest datos) {

        AnalisisEnergeticoResponse response = restClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(datos)
                .retrieve()
                .body(AnalisisEnergeticoResponse.class);

        return new ResultadoPrediccion(response,false);
    }
}