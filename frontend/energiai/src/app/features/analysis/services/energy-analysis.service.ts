import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, delay, timeout } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { EnergyAnalysisRequest } from '../models/energy-analysis-request.interface';
import { EnergyAnalysisResponse } from '../models/energy-analysis-response.interface';

/**
 * Service to consume the energy analysis API.
 *
 * The backend Java exposes POST /analisis-energetico. Mock mode is configured
 * by the active environment to keep development independent from the API.
 */
@Injectable({
  providedIn: 'root',
})
export class EnergyAnalysisService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl
    ? new URL('/analisis-energetico', environment.apiUrl).toString()
    : '/analisis-energetico';
  private readonly useMock = environment.useMock;

  /**
   * Analyzes energy consumption based on the provided request.
   * Returns the classification, cost estimate, and recommendations.
   */
  analyzeConsumption(request: EnergyAnalysisRequest): Observable<EnergyAnalysisResponse> {
    if (this.useMock) {
      return this.getMockResponse(request);
    }

    return this.http.post<EnergyAnalysisResponse>(this.apiUrl, request).pipe(timeout(10_000));
  }

  /**
   * Mock response for development.
   * Simulates a 1.5s delay like the real API.
   */
  private getMockResponse(request: EnergyAnalysisRequest): Observable<EnergyAnalysisResponse> {
    // Simulate category based on consumption
    const consumo = request.consumoTotalMesAnterior;
    const equipos = request.equipmentCount;
    const personas = request.householdSize;

    let categoria: string;
    let probabilidad: number;
    let costoEstimadoMensual: number;
    let recomendaciones: string[];

    // Simple mock logic to simulate different outcomes
    const consumoPerCapita = consumo / personas;

    if (consumoPerCapita < 80 && equipos < 8) {
      categoria = 'Eficiente';
      probabilidad = 0.87;
      costoEstimadoMensual = consumo * 0.65;
      recomendaciones = [
        'Mantener los buenos hábitos de consumo energético.',
        'Considerar energías renovables para reducir aún más el impacto.',
        'Revisar periódicamente el estado de los equipos eléctricos.',
      ];
    } else if (consumoPerCapita > 150 || (equipos > 12 && request.hasAc === 1)) {
      categoria = 'Ineficiente';
      probabilidad = 0.78;
      costoEstimadoMensual = consumo * 0.85;
      recomendaciones = [
        'Reducir significativamente el uso de equipos durante horarios pico (18:00 - 22:00).',
        'Reemplazar equipos antiguos por modelos con certificación de eficiencia energética.',
        'Implementar un sistema de apagado automático para equipos en standby.',
        'Considerar aislamiento térmico para reducir el uso de aire acondicionado.',
        'Distribuir las actividades de alto consumo en diferentes horarios del día.',
      ];
    } else {
      categoria = 'Moderado';
      probabilidad = 0.82;
      costoEstimadoMensual = consumo * 0.75;
      recomendaciones = [
        'Reducir el uso de equipos durante horarios pico (18:00 - 22:00).',
        'Evaluar la eficiencia de equipos con más de 5 años de antigüedad.',
        'Distribuir actividades de mayor consumo en diferentes horarios.',
        'Considerar iluminación LED en toda la vivienda.',
      ];
    }

    const response: EnergyAnalysisResponse = {
      categoria,
      probabilidad,
      costoEstimadoMensual: Math.round(costoEstimadoMensual * 100) / 100,
      recomendaciones,
    };

    // Simulate network delay (1.5 seconds)
    return of(response).pipe(delay(1500));
  }
}
