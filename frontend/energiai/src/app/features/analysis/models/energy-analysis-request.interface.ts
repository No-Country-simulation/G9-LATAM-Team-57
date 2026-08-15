import { HousingType } from './housing-type.enum';
import { PeakUsageLevel } from './peak-usage-level.enum';

export interface EnergyAnalysisRequest {
  householdSize: number;
  hasAc: boolean;
  homeOffice: boolean;
  housingType: HousingType;
  equipmentCount: number;
  consumoTotalMesAnterior: number;
  peakUsageLevel: PeakUsageLevel;
  costoPorKwh?: number;
}
