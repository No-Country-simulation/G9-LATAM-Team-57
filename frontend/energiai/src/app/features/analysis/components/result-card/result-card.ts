import { ChangeDetectionStrategy, Component, input, computed } from '@angular/core';
import { NgClass } from '@angular/common';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-result-card',
  standalone: true,
  imports: [NgClass],
  templateUrl: './result-card.html',
  styleUrl: './result-card.scss',
})
export class ResultCard {
  readonly categoria = input.required<string>();
  readonly probabilidad = input.required<number>();

  readonly badgeClass = computed(() => {
    switch (this.categoria().toLowerCase()) {
      case 'eficiente':
        return 'badge--eficiente';
      case 'moderado':
        return 'badge--moderado';
      case 'ineficiente':
        return 'badge--ineficiente';
      default:
        return 'badge--moderado';
    }
  });

  readonly progressClass = computed(() => {
    switch (this.categoria().toLowerCase()) {
      case 'eficiente':
        return 'result-card__progress-bar--eficiente';
      case 'moderado':
        return 'result-card__progress-bar--moderado';
      case 'ineficiente':
        return 'result-card__progress-bar--ineficiente';
      default:
        return 'result-card__progress-bar--moderado';
    }
  });

  readonly progressWidth = computed(() => `${Math.round(this.probabilidad() * 100)}%`);
}
