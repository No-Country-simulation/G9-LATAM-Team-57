import { Component } from '@angular/core';
import { EnergyForm } from '../../components/energy-form/energy-form';
import { EnergyAnalysisRequest } from '../../models';

@Component({
  selector: 'app-analysis-page',
  standalone: true,
  imports: [EnergyForm],
  templateUrl: './analysis-page.html',
  styleUrl: './analysis-page.scss',
})
export class AnalysisPage {
  onFormSubmit(request: EnergyAnalysisRequest): void {
    // TODO: Integrar con EnergyAnalysisService en Sprint 3
  }
}
