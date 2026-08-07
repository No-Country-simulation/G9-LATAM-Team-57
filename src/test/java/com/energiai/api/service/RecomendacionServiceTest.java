package com.energiai.api.service;

import com.energiai.api.model.dto.response.Categoria;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RecomendacionServiceTest {

    private final RecomendacionService service = new RecomendacionService();

    @Test
    void returnsAtLeastThreeRecommendationsForEachCategory() {
        assertThat(service.recomendacionesPara(Categoria.Eficiente).size()).isGreaterThanOrEqualTo(3);
        assertThat(service.recomendacionesPara(Categoria.Moderado).size()).isGreaterThanOrEqualTo(3);
        assertThat(service.recomendacionesPara(Categoria.Ineficiente).size()).isGreaterThanOrEqualTo(3);
    }
}
