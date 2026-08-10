package com.energiai.api.service;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.HousingType;
import com.energiai.api.model.dto.request.PeakUsageLevel;
import com.energiai.api.model.dto.response.Categoria;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RecomendacionServiceTest {


    private final RecomendacionService service = new RecomendacionService();

    private static AnalisisEnergeticoRequest requestCon(boolean hasAc, boolean homeOffice, int equipmentCount, PeakUsageLevel peakUsageLevel) {
        return new AnalisisEnergeticoRequest(3, hasAc, homeOffice, HousingType.CASA, equipmentCount, 300.0, peakUsageLevel);
    }

    @Test
    void returnsAtLeastThreeRecomendacionesForEachCategory() {
        AnalisisEnergeticoRequest request = requestCon(false, false, 5, PeakUsageLevel.LOW);

        assertThat(service.recomendacionesPara(Categoria.Eficiente, request).size()).isGreaterThanOrEqualTo(2);
        assertThat(service.recomendacionesPara(Categoria.Moderado, request).size()).isGreaterThanOrEqualTo(2);
        assertThat(service.recomendacionesPara(Categoria.Ineficiente, request).size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void addsExtraRecomendacionWhenHasAc() {
        AnalisisEnergeticoRequest conAc = requestCon(true, false, 3, PeakUsageLevel.LOW);
        AnalisisEnergeticoRequest sinAc = requestCon(false, false, 2, PeakUsageLevel.LOW);

        int conAcSize = service.recomendacionesPara(Categoria.Eficiente, conAc).size();
        int sinAcSize = service.recomendacionesPara(Categoria.Eficiente, sinAc).size();

        assertThat(conAcSize).isGreaterThan(sinAcSize);
    }

    @Test
    void addsExtraRecomendacionWhenPeakUsageIsHigh() {
        AnalisisEnergeticoRequest picoAlto = requestCon(false, false, 5, PeakUsageLevel.HIGH);
        AnalisisEnergeticoRequest picoBajo = requestCon(false, false, 5, PeakUsageLevel.LOW);

        int altoSize = service.recomendacionesPara(Categoria.Moderado, picoAlto).size();
        int bajoSize = service.recomendacionesPara(Categoria.Moderado, picoBajo).size();

        assertThat(altoSize).isGreaterThan(bajoSize);
    }
}
