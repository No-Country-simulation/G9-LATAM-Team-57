package com.energiai.api.service;

import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/*
* Conecta el base-url y api-key.
* Recibe el PrediccionRequest (información ingresada por el usuario
* mapeada a lo que espera la API-python) y con esta información
* hace POST y luego recibe la respuesta.
* apiKey y baseUrl son variables de entorno
* */
@Service
public class ModelClient {

    private final RestClient restClient;
    private final String apiKey;

    public ModelClient(RestClient.Builder restClientBuilder, @Value("${fastapi.base-url}") String baseUrl ,@Value("${fastapi.api-key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient =restClientBuilder.baseUrl(baseUrl).build();
    }

    public AnalisisEnergeticoResponse predict(PrediccionRequest request){

        return restClient.post()
                .uri("/predict")
                .header("X-API-Key",apiKey)
                /*
                *Maneja el body de la request
                *que va a API-python
                */
                .body(request)
                /*
                * Retrieve: Ejecuta la llamada HTTP y
                * prepara la respuesta para ser procesada
                * */
                .retrieve()
                //Maneja el body de la respuesta de API-python
                .body(AnalisisEnergeticoResponse.class);
    }
}
