import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoadingService {
  private readonly _loading = signal(false);

  /** Whether the app is in a loading state */
  readonly isLoading = this._loading.asReadonly();

  /** Text to display during loading */
  readonly loadingText = signal('Analizando consumo energético...');

  show(text?: string): void {
    if (text) {
      this.loadingText.set(text);
    }
    this._loading.set(true);
  }

  hide(): void {
    this._loading.set(false);
    this.loadingText.set('Analizando consumo energético...');
  }
}
