import { Component, computed, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import { ALARMAS, ETIQUETA_TIPO } from '../../models/alarma.model';
import {
  DIAS_SEMANA_BASE,
  RUTINAS_RESUMEN,
  type AlarmaRutina,
  type DiaSemana,
} from '../../models/rutina.model';

@Component({
  selector: 'app-editor-rutina',
  imports: [TopBar, RouterLink],
  templateUrl: './editor-rutina.html',
  styleUrl: './editor-rutina.scss',
})
export class EditorRutina {
  readonly etiquetaTipo = ETIQUETA_TIPO;
  readonly esNueva: boolean;

  readonly nombre = signal('');
  readonly activa = signal(true);
  readonly dias = signal<DiaSemana[]>(
    DIAS_SEMANA_BASE.map((etiqueta) => ({ etiqueta, activo: false })),
  );
  readonly alarmas = signal<AlarmaRutina[]>([]);

  readonly mostrandoSelector = signal(false);
  readonly alarmaParaAgregar = signal('');

  readonly alarmasDisponibles = computed(() => {
    const idsEnRutina = new Set(this.alarmas().map((a) => a.id));
    return ALARMAS.filter((a) => !idsEnRutina.has(a.id));
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {
    const id = this.route.snapshot.paramMap.get('id');
    this.esNueva = !id;

    if (id) {
      const rutina = RUTINAS_RESUMEN.find((r) => r.id === id);
      if (rutina) {
        this.nombre.set(rutina.nombre);
        this.dias.set(rutina.dias.map((d) => ({ ...d })));
        this.alarmas.set(rutina.alarmas.map((a) => ({ ...a })));
      }
    }
  }

  escribirNombre(evento: Event): void {
    this.nombre.set((evento.target as HTMLInputElement).value);
  }

  alternarDia(indice: number): void {
    this.dias.update((dias) =>
      dias.map((dia, i) => (i === indice ? { ...dia, activo: !dia.activo } : dia)),
    );
  }

  alternarActiva(): void {
    this.activa.update((valor) => !valor);
  }

  quitarAlarma(id: string): void {
    this.alarmas.update((lista) => lista.filter((a) => a.id !== id));
  }

  abrirSelector(): void {
    this.mostrandoSelector.set(true);
    this.alarmaParaAgregar.set(this.alarmasDisponibles()[0]?.id ?? '');
  }

  cerrarSelector(): void {
    this.mostrandoSelector.set(false);
  }

  elegirAlarmaParaAgregar(evento: Event): void {
    this.alarmaParaAgregar.set((evento.target as HTMLSelectElement).value);
  }

  confirmarAgregar(): void {
    const alarma = ALARMAS.find((a) => a.id === this.alarmaParaAgregar());
    if (!alarma) {
      return;
    }
    this.alarmas.update((lista) => [
      ...lista,
      { id: alarma.id, hora: alarma.hora, titulo: alarma.titulo, tipo: alarma.tipo },
    ]);
    this.cerrarSelector();
  }

  guardar(): void {
    // Maquetación: sin backend, no persiste nada. Solo navega de vuelta.
    this.router.navigate(['/rutinas']);
  }

  cancelar(): void {
    this.router.navigate(['/rutinas']);
  }
}
