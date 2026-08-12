package com.energiai.api.client;

import com.energiai.api.model.dto.request.PrediccionRequest;
import com.energiai.api.model.dto.response.AnalisisEnergeticoResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MlModelClientConFallback implements MlModelClient{

    private final MlModelClientImpl implementacion;
    private final MlModelClientMock mock;

    public MlModelClientConFallback(MlModelClientImpl implementacion, MlModelClientMock mock) {
        this.implementacion = implementacion;
        this.mock = mock;
    }

    @Override
    public ResultadoPrediccion predict(PrediccionRequest datos) {
        try{
            return implementacion.predict(datos);
        }
        catch (Exception e){
            return mock.predict(datos);
        }

    }
}
