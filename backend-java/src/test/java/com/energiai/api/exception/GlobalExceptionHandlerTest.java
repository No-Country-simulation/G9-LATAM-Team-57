package com.energiai.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
public class GlobalExceptionHandlerTest {


    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void modelUnavailableMapsTo503() {
        ResponseEntity<Map<String, String>> response =
                handler.handleModelUnavailable(new ResourceAccessException("timeout"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    void modelErrorMapsTo502() {
        HttpServerErrorException ex = HttpServerErrorException.create(
                HttpStatus.INTERNAL_SERVER_ERROR, "error", null, null, null);
        ResponseEntity<Map<String, String>> response = handler.handleModelError(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_GATEWAY);
    }
}
