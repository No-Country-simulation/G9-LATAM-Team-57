import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';

import { environment as productionEnvironment } from '../../../../environments/environment.production';
import { environment } from '../../../../environments/environment';
import { EnergyAnalysisService } from './energy-analysis.service';
import { HousingType, PeakUsageLevel } from '../models';
import { EnergyAnalysisResponse } from '../models/energy-analysis-response.interface';

describe('EnergyAnalysisService API route', () => {
  let service: EnergyAnalysisService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(EnergyAnalysisService);
  });

  it('builds the current Java endpoint in development', () => {
    const apiUrl = (service as unknown as { apiUrl: string }).apiUrl;

    expect(environment.useMock).toBe(true);
    expect(apiUrl).toBe('http://localhost:8080/analisis-energetico');
  });

  it('builds a relative endpoint in production', () => {
    expect(productionEnvironment.useMock).toBe(false);
    expect(productionEnvironment.apiUrl).toBe('');
  });

  it('returns a mock response with the expected shape', async () => {
    const response: EnergyAnalysisResponse = await firstValueFrom(
      service.analyzeConsumption({
        householdSize: 4,
        hasAc: 1,
        homeOffice: true,
        housingType: HousingType.CASA,
        equipmentCount: 10,
        consumoTotalMesAnterior: 420,
        peakUsageLevel: PeakUsageLevel.HIGH,
      })
    );

    expect(response.categoria).toBeDefined();
    expect(response.recomendaciones.length).toBeGreaterThan(0);
    expect(response.costoEstimadoMensual).toBeDefined();
    expect(response.simulado).toBeUndefined();
  });
});
