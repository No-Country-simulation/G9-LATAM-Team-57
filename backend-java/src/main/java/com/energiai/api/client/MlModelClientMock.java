package com.energiai.api.client;

import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import com.energiai.api.model.dto.response.Categoria;

import org.springframework.stereotype.Component;





@Component
public class MlModelClientMock implements MlModelClient {



    @Override
    public ResultadoPrediccion predict(PrediccionRequest datos) {


        // Regla simple, sin llamar a ningún servicio externo:
        // si el consumo promedio diario es alto, decimos "Ineficiente".
        Categoria categoria;

        if (datos.avg_energy_consumption_kwh()>15){
            categoria= Categoria.Ineficiente;

        } else if (datos.avg_energy_consumption_kwh() > 8) {

            categoria = Categoria.Moderado;
        } else {
            categoria = Categoria.Eficiente;
        }

        return new ResultadoPrediccion(new AnalisisEnergeticoResponse(categoria,0.5), true);


    }
}