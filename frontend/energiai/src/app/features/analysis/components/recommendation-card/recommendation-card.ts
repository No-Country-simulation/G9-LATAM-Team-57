import { Component, input } from '@angular/core';

@Component({
  selector: 'app-recommendation-card',
  standalone: true,
  imports: [],
  templateUrl: './recommendation-card.html',
  styleUrl: './recommendation-card.scss',
})
export class RecommendationCard {
  readonly recomendaciones = input.required<string[]>();
}
