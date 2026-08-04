import { Component, inject, signal } from '@angular/core';
import { NgClass, DecimalPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

import { EnergyForm } from '../../components/energy-form/energy-form';
import { EnergyAnalysisRequest, EnergyAnalysisResponse } from '../../models';
import { EnergyAnalysisService } from '../../services/energy-analysis.service';
import { LoadingService } from '../../../../core/services/loading.service';
import { NotificationService } from '../../../../core/services/notification.service';

type AnalysisState = 'form' | 'loading' | 'result' | 'error';

@Component({
  selector: 'app-analysis-page',
  standalone: true,
  imports: [EnergyForm, MatIconModule, NgClass, DecimalPipe],
  templateUrl: './analysis-page.html',
  styleUrl: './analysis-page.scss',
})
export class AnalysisPage {
  private readonly analysisService = inject(EnergyAnalysisService);
  private readonly loadingService = inject(LoadingService);
  private readonly notification = inject(NotificationService);

  /** Current state of the analysis flow */
  readonly state = signal<AnalysisState>('form');

  /** Analysis result */
  readonly result = signal<EnergyAnalysisResponse | null>(null);

  /** Error message */
  readonly error = signal<string | null>(null);

  /** Last submitted request (for retry) */
  private lastRequest: EnergyAnalysisRequest | null = null;

  onFormSubmit(request: EnergyAnalysisRequest): void {
    this.lastRequest = request;
    this.executeAnalysis(request);
  }

  onRetry(): void {
    if (this.lastRequest) {
      this.executeAnalysis(this.lastRequest);
    }
  }

  onNewAnalysis(): void {
    this.state.set('form');
    this.result.set(null);
    this.error.set(null);
  }

  /**
   * Returns the CSS class for the category badge
   */
  getCategoryClass(categoria: string): string {
    switch (categoria.toLowerCase()) {
      case 'eficiente':
        return 'badge--eficiente';
      case 'moderado':
        return 'badge--moderado';
      case 'ineficiente':
        return 'badge--ineficiente';
      default:
        return 'badge--moderado';
    }
  }

  /**
   * Returns the progress bar width based on probability
   */
  getProgressWidth(probabilidad: number): string {
    return `${Math.round(probabilidad * 100)}%`;
  }

  /**
   * Returns the progress bar CSS class based on category
   */
  getProgressClass(categoria: string): string {
    switch (categoria.toLowerCase()) {
      case 'eficiente':
        return 'result-card__progress-bar--eficiente';
      case 'moderado':
        return 'result-card__progress-bar--moderado';
      case 'ineficiente':
        return 'result-card__progress-bar--ineficiente';
      default:
        return 'result-card__progress-bar--moderado';
    }
  }

  private executeAnalysis(request: EnergyAnalysisRequest): void {
    // Prevent multiple simultaneous submissions
    if (this.state() === 'loading') {
      return;
    }

    this.state.set('loading');
    this.error.set(null);
    this.loadingService.show();

    this.analysisService.analyzeConsumption(request).subscribe({
      next: (response) => {
        this.result.set(response);
        this.state.set('result');
        this.loadingService.hide();
      },
      error: (err) => {
        const errorMessage =
          err?.error?.message || 'No fue posible realizar el análisis. Intente nuevamente.';
        this.error.set(errorMessage);
        this.state.set('error');
        this.loadingService.hide();
      },
    });
  }
}
