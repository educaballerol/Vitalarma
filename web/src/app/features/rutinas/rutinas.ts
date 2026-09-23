import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import { RUTINAS_RESUMEN, type AlarmaRutina, type DiaSemana } from '../../models/rutina.model';
import { ETIQUETA_TIPO } from '../../models/alarma.model';

interface ArrastreAlarma {
  rutinaId: string;
  indice: number;
}

@Component({
  selector: 'app-rutinas',
  imports: [TopBar],
  templateUrl: './rutinas.html',
  styleUrl: './rutinas.scss',
})
export class Rutinas {
  readonly etiquetaTipo = ETIQUETA_TIPO;
  readonly rutinas = RUTINAS_RESUMEN;

  readonly activas = signal<Record<string, boolean>>(
    Object.fromEntries(RUTINAS_RESUMEN.map((r) => [r.id, true])),
  );

  readonly diasEditables = signal<Record<string, DiaSemana[]>>(
    Object.fromEntries(RUTINAS_RESUMEN.map((r) => [r.id, r.dias.map((d) => ({ ...d }))])),
  );

  readonly alarmasEditables = signal<Record<string, AlarmaRutina[]>>(
    Object.fromEntries(RUTINAS_RESUMEN.map((r) => [r.id, [...r.alarmas]])),
  );

  private arrastrando: ArrastreAlarma | null = null;

  constructor(private router: Router) {}

  estaActiva(id: string): boolean {
    return this.activas()[id];
  }

  alternarActiva(id: string): void {
    this.activas.update((mapa) => ({ ...mapa, [id]: !mapa[id] }));
  }

  diasDe(id: string): DiaSemana[] {
    return this.diasEditables()[id];
  }

  alternarDia(id: string, indice: number): void {
    this.diasEditables.update((mapa) => ({
      ...mapa,
      [id]: mapa[id].map((dia, i) => (i === indice ? { ...dia, activo: !dia.activo } : dia)),
    }));
  }

  alarmasDe(id: string): AlarmaRutina[] {
    return this.alarmasEditables()[id];
  }

  habilitarArrastre(evento: MouseEvent): void {
    (evento.currentTarget as HTMLElement).closest('li')?.setAttribute('draggable', 'true');
  }

  quitarDraggable(evento: Event): void {
    (evento.currentTarget as HTMLElement).removeAttribute('draggable');
  }

  iniciarArrastre(rutinaId: string, indice: number, evento: DragEvent): void {
    this.arrastrando = { rutinaId, indice };
    evento.dataTransfer?.setData('text/plain', '');
    if (evento.dataTransfer) {
      evento.dataTransfer.effectAllowed = 'move';
    }
  }

  permitirSoltar(evento: DragEvent): void {
    evento.preventDefault();
  }

  soltarEnFila(rutinaId: string, indiceDestino: number, evento: DragEvent): void {
    evento.preventDefault();
    evento.stopPropagation();
    this.mover(rutinaId, indiceDestino);
  }

  soltarEnLista(rutinaId: string, evento: DragEvent): void {
    evento.preventDefault();
    this.mover(rutinaId, this.alarmasEditables()[rutinaId].length);
  }

  private mover(rutinaIdDestino: string, indiceDestino: number): void {
    if (!this.arrastrando) {
      return;
    }
    const { rutinaId: rutinaIdOrigen, indice: indiceOrigen } = this.arrastrando;
    this.arrastrando = null;

    this.alarmasEditables.update((mapa) => {
      const copia: Record<string, AlarmaRutina[]> = {};
      for (const [id, lista] of Object.entries(mapa)) {
        copia[id] = [...lista];
      }

      const [alarma] = copia[rutinaIdOrigen].splice(indiceOrigen, 1);
      let indiceFinal = indiceDestino;
      if (rutinaIdOrigen === rutinaIdDestino && indiceOrigen < indiceDestino) {
        indiceFinal -= 1;
      }
      copia[rutinaIdDestino].splice(indiceFinal, 0, alarma);
      return copia;
    });
  }

  editar(id: string): void {
    this.router.navigate(['/rutinas', id, 'editar']);
  }

  nuevaRutina(): void {
    this.router.navigate(['/rutinas/nueva']);
  }
}
