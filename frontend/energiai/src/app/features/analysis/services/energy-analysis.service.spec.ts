import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { environment as productionEnvironment } from '../../../../environments/environment.production';
import { environment } from '../../../../environments/environment';
import { EnergyAnalysisService } from './energy-analysis.service';

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

  it('builds the current Java endpoint in production', () => {
    const apiUrl = new URL('/analisis-energetico', productionEnvironment.apiUrl).toString();

    expect(productionEnvironment.useMock).toBe(false);
    expect(apiUrl).toBe('https://api.energiai.cloud/analisis-energetico');
  });
});
