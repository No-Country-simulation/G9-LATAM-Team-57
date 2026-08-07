package com.energiai.api.service;

import com.energiai.api.model.dto.response.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecomendacionService {

    private static final Map<Categoria, List<String>> RECOMENDACIONES = Map.of(

            Categoria.Eficiente, List.of(
                    "Recomendacion eficiente 1",
                    "Recomendacion eficiente 2",
                    "Recomendacion eficiente 3"
            ),
            Categoria.Moderado, List.of(
                    "Recomendacion consumo moderado 1",
                    "Recomendacion consumo moderado 2",
                    "Recomendacion consumo moderado 3"
            ),Categoria.Ineficiente, List.of(
                    "Recomendacion consumo ineficiente 1",
                    "Recomendacion consumo ineficiente 2",
                    "Recomendacion consumo ineficiente 3"
            )
    );

    public List<String> recomendacionesPara(Categoria categoria){
        return RECOMENDACIONES.get(categoria);
    }
}
