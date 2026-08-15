import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./features/home/home.routes').then((m) => m.HOME_ROUTES),
  },
  {
    path: 'analysis',
    loadChildren: () =>
      import('./features/analysis/analysis.routes').then((m) => m.ANALYSIS_ROUTES),
  },
  {
    path: '**',
    redirectTo: '',
  },
];
