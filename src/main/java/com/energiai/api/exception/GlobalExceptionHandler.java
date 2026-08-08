package com.energiai.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /* Si falla la validacion de AnalisisEnergeticoRequest
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {
            Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, fe -> fe.getDefaultMessage(), (a, b) -> a));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    /* No se puede deserializar el JSON de la request que mandó el frontend
    * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleMalformedJson(HttpMessageNotReadableException e){

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("error","Solicitud invalida."));
    }
    /*  No respone la API a la que realizo el POST (fastapi) desde esta API
    * */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String,String>> handleModelUnavailable(ResourceAccessException e){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of("error","No se puede realizar la predicción en este momento dado que el servicio no está disponible. Prueba más tarde."));

    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String,String>> handleModelError(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("error","Error interno."));
    }


    


}
