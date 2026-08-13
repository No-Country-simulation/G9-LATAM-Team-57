import { ChangeDetectionStrategy, Component, inject, output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

import { EnergyAnalysisRequest, HousingType, PeakUsageLevel } from '../../models';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-energy-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatIconModule],
  templateUrl: './energy-form.html',
  styleUrl: './energy-form.scss',
})
export class EnergyForm {
  private readonly fb = inject(FormBuilder);

  readonly submitForm = output<EnergyAnalysisRequest>();

  readonly housingTypes = [
    { value: HousingType.CASA, label: 'Casa' },
    { value: HousingType.DEPARTAMENTO, label: 'Departamento' },
    { value: HousingType.MONOAMBIENTE, label: 'Monoambiente' },
  ];

  readonly peakUsageLevels = [
    { value: PeakUsageLevel.LOW, label: '0–2 horas' },
    { value: PeakUsageLevel.MEDIUM, label: '3–5 horas' },
    { value: PeakUsageLevel.HIGH, label: 'Más de 5 horas' },
  ];

  readonly form = this.fb.group({
    householdSize: [null as number | null, [Validators.required, Validators.min(1)]],
    hasAc: [null as boolean | null, [Validators.required]],
    homeOffice: [null as boolean | null, [Validators.required]],
    housingType: [null as HousingType | null, [Validators.required]],
    equipmentCount: [null as number | null, [Validators.required, Validators.min(0)]],
    consumoTotalMesAnterior: [null as number | null, [Validators.required, Validators.min(0.01)]],
    peakUsageLevel: [null as PeakUsageLevel | null, [Validators.required]],
    costoPorKwh: [0.75 as number | null, [Validators.required, Validators.min(0.01)]],
  });

  onSubmit(): void {
    if (this.form.valid) {
      const formValue = this.form.getRawValue();
      const request: EnergyAnalysisRequest = {
        householdSize: formValue.householdSize!,
        hasAc: formValue.hasAc!,
        homeOffice: formValue.homeOffice!,
        housingType: formValue.housingType!,
        equipmentCount: formValue.equipmentCount!,
        consumoTotalMesAnterior: formValue.consumoTotalMesAnterior!,
        peakUsageLevel: formValue.peakUsageLevel!,
        costoPorKwh: formValue.costoPorKwh!,
      };
      this.submitForm.emit(request);
    } else {
      this.form.markAllAsTouched();
    }
  }
}
