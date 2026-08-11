package com.energiai.api.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/* Mapea el tipo de hogar que ingresa
 *  el usuario por teclado
 * */
public enum HousingType {
    CASA,
    DEPARTAMENTO,
    MONOAMBIENTE;



    @JsonCreator
    public static HousingType fromString(String valor){

        if (valor ==null){
            throw new IllegalArgumentException("El valor no puede ser nulo");
        }
        String normalizado = valor.trim().toUpperCase();

        //Pruebo con el nombre del enum
        for (HousingType tipo : values()){
            if (tipo.name().equals(normalizado)){
                return tipo;
            }}

        throw new IllegalArgumentException("El tipo de propiedad: "+ valor+" NO es reconocida.");
     }

     @JsonValue
     public  String toValue(){
        return this.name();
    }

    }


