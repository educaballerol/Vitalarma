import { Component, signal } from '@angular/core';
import { TopBar } from '../../shared/top-bar/top-bar';
import { ETIQUETA_TIPO, type TipoAlarma } from '../../models/alarma.model';

interface DiaSemana {
  etiqueta: string;
  activo: boolean;
}

interface AlarmaRutina {
  hora: string;
  titulo: string;
  tipo: TipoAlarma;
}

interface RutinaResumen {
  id: string;
  nombre: string;
  dias: DiaSemana[];
  alarmas: AlarmaRutina[];
}

interface ArrastreAlarma {
  rutinaId: string;
  indice: number;
}

const RUTINAS_RESUMEN: readonly RutinaResumen[] = [
  {
    id: 'oficina',
    nombre: 'Día de oficina',
    dias: [
      { etiqueta: 'Lun', activo: true },
      { etiqueta: 'Mar', activo: true },
      { etiqueta: 'Mié', activo: true },
      { etiqueta: 'Jue', activo: true },
      { etiqueta: 'Vie', activo: true },
      { etiqueta: 'Sáb', activo: false },
      { etiqueta: 'Dom', activo: false },
    ],
    alarmas: [
      { hora: '6:00', titulo: 'Salir hacia la oficina', tipo: 'critica' },
      { hora: '13:30', titulo: 'Reunión con el cliente', tipo: 'cauta' },
      { hora: '17:00', titulo: 'Salir hacia la clase', tipo: 'critica' },
    ],
  },
  {
    id: 'estudio',
    nombre: 'Día de estudio',
    dias: [
      { etiqueta: 'Lun', activo: false },
      { etiqueta: 'Mar', activo: false },
      { etiqueta: 'Mié', activo: false },
      { etiqueta: 'Jue', activo: false },
      { etiqueta: 'Vie', activo: false },
      { etiqueta: 'Sáb', activo: true },
      { etiqueta: 'Dom', activo: true },
    ],
    alarmas: [
      { hora: '9:00', titulo: 'Bloque de lectura', tipo: 'recordatorio' },
      { hora: '15:00', titulo: 'Entrega de UX', tipo: 'recordatorio' },
    ],
  },
];

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
    // Maquetación: pendiente conectar a W-11 (editor de rutina) cuando exista esa ruta.
  }

  nuevaRutina(): void {
    // Maquetación: sin backend, no crea nada. Destino final sin definir.
  }
}
