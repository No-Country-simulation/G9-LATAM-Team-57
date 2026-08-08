package com.energiai.api.service;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CostoEstimadoServiceTest {

    private final CostoEstimadoService service = new CostoEstimadoService(0.7);

    @Test
    void calculatesMonthlyCostFromConsumption() {
        Double costo = service.calcularCostoMensual(420.0);
        assertThat(costo).isEqualTo(294.0);
    }
}
