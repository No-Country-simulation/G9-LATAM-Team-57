import { Component, input } from '@angular/core';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-cost-card',
  standalone: true,
  imports: [DecimalPipe],
  templateUrl: './cost-card.html',
  styleUrl: './cost-card.scss',
})
export class CostCard {
  readonly costoEstimado = input.required<number>();
}
