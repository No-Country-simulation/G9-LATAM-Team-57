package com.energiai.api.service.impl;

import com.energiai.api.client.MlModelClient;
import com.energiai.api.client.MlPrediccionRequest;
import com.energiai.api.client.MlPrediccionResultado;
import com.energiai.api.model.dto.request.ConsumoEnergeticoRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.service.AnalisisEnergeticoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * EL COCINERO JEFE Y EL ARTE DEL FALLBACK (Service Implementation)
 *
 * Tras las puertas vaivén de la cocina, el Cocinero Jefe orquesta la magia. Él no es el mozo;
 * él gobierna los fuegos de la lógica de negocio. Lee la comanda, calcula costos y sazona
 * las recomendaciones.
 *
 * Sin embargo, al llegar el momento del postre —la compleja predicción de Inteligencia Artificial—
 * el Cocinero Jefe sabe que esa delicia no nace de su estación. Confecciona una comanda interna
 * y solicita el saber del Repostero Titular (Servicio Python).
 *
 * Mas, si el Repostero Titular ha ausentado su presencia o sus hornos se han enfriado (falla de red),
 * el Cocinero Jefe no desespera ni envía una bandeja vacía al salón: acude de inmediato al
 * Repostero Ayudante (MlModelClientMock). Éste, en un acto de perfecta resiliencia, emplata
 * la réplica exacta en la misma vajilla dorada. El comensal en el salón jamás sabrá del drama
 * en la cocina; solo disfrutará de la excelencia de su banquete.
 */

@Service
public class AnalisisEnergeticoServiceImpl implements AnalisisEnergeticoService {

    private final MlModelClient realClient;
    private final MlModelClient mockClient;

    public AnalisisEnergeticoServiceImpl(
            @Qualifier("mlModelClientImpl") MlModelClient realClient,
            @Qualifier("mlModelClientMock") MlModelClient mockClient) {
        this.realClient = realClient;
        this.mockClient = mockClient;
    }

    @Override
    public AnalisisEnergeticoResponse analizar(ConsumoEnergeticoRequest request) {

        // 1. Transformación al DTO que requiere Python
        MlPrediccionRequest mlRequest = mapToMlRequest(request);

        MlPrediccionResultado resultadoMl;

        // 2. Estrategia de Fallback (Resiliencia ante fallas de red)
        try {
            resultadoMl = realClient.predecir(mlRequest);
        } catch (Exception e) {
            System.err.println("⚠️ ALERTA: No se pudo conectar con el servicio de IA en Python (" + e.getMessage() + ")");
            System.out.println("🔄 ACTIVANDO MODO FALLBACK: Utilizando MlModelClientMock para responder...");

            resultadoMl = mockClient.predecir(mlRequest);
        }

        // 3. Cálculos de negocio propios de Java (Costo Estimado Mensual)
        Double consumoTotal = request.getConsumoTotalMesAnterior();
        Double costoEstimado = (consumoTotal != null) ? consumoTotal * 0.75 : 0.0;

        // 4. Construcción de recomendaciones personalizadas según perfil e IA
        List<String> recomendaciones = generarRecomendaciones(request, resultadoMl);

        // 5. Retorno de la respuesta
        return new AnalisisEnergeticoResponse(
                resultadoMl.getCategoria(),
                resultadoMl.getProbabilidad(),
                recomendaciones,
                costoEstimado
        );
    }

    private MlPrediccionRequest mapToMlRequest(ConsumoEnergeticoRequest request) {
        MlPrediccionRequest mlReq = new MlPrediccionRequest();
        mlReq.setHouseholdSize(request.getHouseholdSize());
        mlReq.setHasAc(request.getHasAc());
        mlReq.setHomeOffice(request.getHomeOffice());

        if (request.getHousingType() != null) {
            mlReq.setHousingType(request.getHousingType().name());
        }

        mlReq.setEquipmentCount(request.getEquipmentCount());

        if (request.getConsumoTotalMesAnterior() != null) {
            mlReq.setAvgEnergyConsumptionKwh(request.getConsumoTotalMesAnterior() / 31.0);
        } else {
            mlReq.setAvgEnergyConsumptionKwh(0.0);
        }

        if (request.getPeakUsageLevel() != null) {
            mlReq.setPeakUsageLevel(request.getPeakUsageLevel().name());
        }

        return mlReq;
    }

    /**
     * Motor de reglas acumulativo para sugerencias personalizadas
     */
    private List<String> generarRecomendaciones(ConsumoEnergeticoRequest request, MlPrediccionResultado resultadoMl) {
        List<String> recomendaciones = new ArrayList<>();

        String categoria = resultadoMl.getCategoria();

        // 1. Regla según la clasificación de la IA
        if ("Ineficiente".equalsIgnoreCase(categoria)) {
            recomendaciones.add("Su nivel de consumo es elevado. Se recomienda auditar los equipos de mayor potencia para reducir el impacto económico.");
        } else if ("Moderado".equalsIgnoreCase(categoria)) {
            recomendaciones.add("Su consumo es aceptable, pero existen oportunidades de optimización en horarios de pico.");
        } else if ("Eficiente".equalsIgnoreCase(categoria)) {
            recomendaciones.add("¡Felicitaciones! Su hogar mantiene un patrón de uso de energía altamente eficiente.");
        } else {
            recomendaciones.add("Consumo clasificado como: " + categoria + ".");
        }

        // 2. Regla por Trabajo Remoto (Home Office)
        if (Boolean.TRUE.equals(request.getHomeOffice())) {
            recomendaciones.add("Al trabajar desde casa, utilice zapatillas con interruptor para apagar computadoras, monitores y cargadores al finalizar su jornada (evite el consumo 'vampiro').");
        }

        // 3. Regla por Climatización (Aire Acondicionado + Pico de consumo alto)
        boolean tieneAc = request.getHasAc() != null && request.getHasAc() == 1;
        boolean usoPicoAlto = request.getPeakUsageLevel() != null && "HIGH".equalsIgnoreCase(request.getPeakUsageLevel().name());

        if (tieneAc && usoPicoAlto) {
            recomendaciones.add("Mantenga el aire acondicionado fijado a 24°C y limpie los filtros mensualmente para evitar sobreexigir el compresor.");
        } else if (tieneAc) {
            recomendaciones.add("Recuerde programar el aire acondicionado en modo ECO o mantener la temperatura recomendada de 24°C.");
        }

        // 4. Regla por Equipamiento y Densidad de Hogar
        boolean altaCantidadEquipos = request.getEquipmentCount() != null && request.getEquipmentCount() >= 5;
        boolean hogarNumeroso = request.getHouseholdSize() != null && request.getHouseholdSize() >= 4;

        if (altaCantidadEquipos || hogarNumeroso) {
            recomendaciones.add("Evite encender simultáneamente electrodomésticos de gran potencia (lavarropas, horno eléctrico, plancha) durante las horas de mayor consumo.");
        }

        return recomendaciones;
    }
}