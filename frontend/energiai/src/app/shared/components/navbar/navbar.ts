import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { AnalysisStatusService } from '../../../core/services/analysis-status.service';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, MatToolbarModule, MatButtonModule, MatIconModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
})
export class NavbarComponent {
  private readonly statusService = inject(AnalysisStatusService);

  readonly status = this.statusService.status;

  readonly statusLabel = computed(() => {
    switch (this.status()) {
      case 'conectado':
        return 'API CONECTADA';
      case 'fallback':
        return 'MODO FALLBACK';
      default:
        return '';
    }
  });

  readonly statusClass = computed(() => `navbar__status--${this.status()}`);
}
