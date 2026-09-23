import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'panel' },
  {
    path: 'panel',
    title: 'Panel de tiempo · Vitalarma',
    loadComponent: () => import('./features/panel/panel').then((m) => m.Panel),
  },
  {
    path: 'alarmas',
    title: 'Alarmas · Vitalarma',
    loadComponent: () => import('./features/alarmas/alarmas').then((m) => m.Alarmas),
  },
  {
    path: 'area/:id',
    title: 'Detalle de área · Vitalarma',
    loadComponent: () => import('./features/area/area').then((m) => m.Area),
  },
  {
    path: 'alarmas/nueva',
    title: 'Nueva alarma · Vitalarma',
    loadComponent: () => import('./features/nueva-alarma/nueva-alarma').then((m) => m.NuevaAlarma),
  },
  {
    path: 'rutinas',
    title: 'Rutinas · Vitalarma',
    loadComponent: () => import('./features/rutinas/rutinas').then((m) => m.Rutinas),
  },
  {
    path: 'rutinas/nueva',
    title: 'Nueva rutina · Vitalarma',
    loadComponent: () =>
      import('./features/editor-rutina/editor-rutina').then((m) => m.EditorRutina),
  },
  {
    path: 'rutinas/:id/editar',
    title: 'Editar rutina · Vitalarma',
    loadComponent: () =>
      import('./features/editor-rutina/editor-rutina').then((m) => m.EditorRutina),
  },
  { path: '**', redirectTo: 'panel' },
];
