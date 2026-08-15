import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-error',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './error.html',
  styleUrl: './error.scss',
})
export class ErrorComponent {
  readonly message = input<string>('No fue posible realizar el análisis. Intente nuevamente.');
  readonly retry = output<void>();

  onRetry(): void {
    this.retry.emit();
  }
}
