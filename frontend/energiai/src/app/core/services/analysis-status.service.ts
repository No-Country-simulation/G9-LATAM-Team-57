import { Injectable, signal } from '@angular/core';

export type AnalysisStatus = 'idle' | 'conectado' | 'fallback';

@Injectable({
  providedIn: 'root',
})
export class AnalysisStatusService {
  private readonly _status = signal<AnalysisStatus>('idle');

  /** Estado actual del origen de datos del análisis */
  readonly status = this._status.asReadonly();

  /** Marca el análisis como respondido en tiempo real por la IA */
  markConectado(): void {
    this._status.set('conectado');
  }

  /** Marca el análisis como respondido por el modo de contingencia */
  markFallback(): void {
    this._status.set('fallback');
  }

  /** Vuelve al estado neutral (inicio o nuevo análisis) */
  reset(): void {
    this._status.set('idle');
  }
}
