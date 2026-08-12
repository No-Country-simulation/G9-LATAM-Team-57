package com.energiai.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {
    /** Configuro el RestClient para para que al realizar
     * peticiones respete los timeouts de 2seg. para
     * conexión y 5 seg. para respuesta.
     * */
    @Bean
    public RestClient.Builder restClientBuilder(){

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofSeconds(2));
        factory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder().requestFactory(factory);
    }
}
