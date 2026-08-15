import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: 'app-footer',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './footer.html',
  styleUrl: './footer.scss',
})
export class FooterComponent {
  readonly currentYear = new Date().getFullYear();
}
