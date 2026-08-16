package com.energiai.api.service;

import com.energiai.api.model.dto.request.AnalisisEnergeticoRequest;
import com.energiai.api.model.dto.request.PeakUsageLevel;
import com.energiai.api.model.dto.response.Categoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RecomendacionService {

    private static final Map<Categoria, List<String>> RECOMENDACIONES = Map.of(

            Categoria.Eficiente, List.of(
                    "Mantén tus hábitos actuales de consumo, lo estás haciendo bien.",
                    "Recuerda realizar el mantenimiento periódico de tus equipos."

            ),
            Categoria.Moderado, List.of(
                    "No olvides realizarle mantenimiento periódico a tus equipos.",
                    "Mejorar el aislamiento térmico de tu hogar puede ayudarte a reducir tu consumo de energía eléctrica.",
                    "Puedes remplazar focos halógenos/incandecentes por focos LED. Esto puede ayudarte a reducir tu consumo."
            ),Categoria.Ineficiente, List.of(

                    "Puedes reemplazar equipos viejos por equipos de alta eficiencia energética.",
                    "Usa el lavarropa/lavavajillas a carga completa y programas eco."
            )
    );

    public List<String> recomendacionesPara(Categoria categoria, AnalisisEnergeticoRequest request){
        List<String> recomendaciones = new ArrayList<>(RECOMENDACIONES.get(categoria));

        if (request.hasAc() != null && request.hasAc() == 1){
            recomendaciones.add("Programa el aire acondicionado unos grados más templado para reducir su consumo.");
            recomendaciones.add("Mantén las puertas y ventanas cerradas mientras funciona el aire condicionado.");
        }
        if (request.homeOffice()){
            recomendaciones.add("Aprovecha la luz natural durante tu jornada de home office.");
        }
        if (request.equipmentCount() >5){
            recomendaciones.add("Tienes muchos equipos conectados! Si no los estás usando puedes desenchufarlos.");
        }
        if (request.peakUsageLevel()== PeakUsageLevel.HIGH){
            recomendaciones.add("Distribuí el uso de equipos fuera del horario pico (18:00 a 22:00)");
        }


        return recomendaciones;
    }
}
