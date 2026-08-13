import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HousingType, PeakUsageLevel } from '../../models';
import { EnergyAnalysisRequest } from '../../models/energy-analysis-request.interface';
import { EnergyForm } from './energy-form';

describe('EnergyForm', () => {
  let fixture: ComponentFixture<EnergyForm>;
  let component: EnergyForm;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnergyForm],
    }).compileComponents();

    fixture = TestBed.createComponent(EnergyForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('emits a request with boolean hasAc and costoPorKwh', () => {
    let emitted: EnergyAnalysisRequest | undefined;
    component.submitForm.subscribe((value) => (emitted = value));

    component.form.setValue({
      householdSize: 4,
      hasAc: true,
      homeOffice: false,
      housingType: HousingType.CASA,
      equipmentCount: 10,
      consumoTotalMesAnterior: 420,
      peakUsageLevel: PeakUsageLevel.HIGH,
      costoPorKwh: 0.8,
    });

    component.onSubmit();

    expect(emitted).toBeDefined();
    expect(emitted?.hasAc).toBe(true);
    expect(emitted?.costoPorKwh).toBe(0.8);
    expect(emitted?.housingType).toBe(HousingType.CASA);
  });

  it('has a default tariff of 0.75', () => {
    expect(component.form.get('costoPorKwh')?.value).toBe(0.75);
  });

  it('does not emit when the form is invalid', () => {
    let emitted = false;
    component.submitForm.subscribe(() => (emitted = true));

    component.form.get('householdSize')?.setValue(null);
    component.onSubmit();

    expect(emitted).toBe(false);
  });
});
