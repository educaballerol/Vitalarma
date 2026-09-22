import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'panel' },
  {
    path: 'panel',
    title: 'Panel de tiempo · Vitalarma',
    loadComponent: () => import('./features/panel/panel').then((m) => m.Panel),
  },
  {
    path: 'area/:id',
    title: 'Detalle de área · Vitalarma',
    loadComponent: () => import('./features/area/area').then((m) => m.Area),
  },
  { path: '**', redirectTo: 'panel' },
];
