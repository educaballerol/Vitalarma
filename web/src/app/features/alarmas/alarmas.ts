import { Component, computed, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import {
  ALARMAS,
  ETIQUETA_ACTIVACION,
  ETIQUETA_TIPO,
  RUTINAS,
  TIPOS,
  type Alarma,
  type EstadoActivacion,
  type TipoAlarma,
} from '../../models/alarma.model';

@Component({
  selector: 'app-alarmas',
  imports: [TopBar, RouterLink],
  templateUrl: './alarmas.html',
  styleUrl: './alarmas.scss',
})
export class Alarmas {
  readonly tipos = TIPOS;
  readonly rutinas = RUTINAS;
  readonly etiquetaTipo = ETIQUETA_TIPO;
  readonly etiquetaActivacion = ETIQUETA_ACTIVACION;

  readonly busqueda = signal('');
  readonly filtroTipo = signal<TipoAlarma | ''>('');
  readonly filtroRutina = signal('');
  readonly filtroEstado = signal<EstadoActivacion | ''>('');

  readonly seleccionadas = signal<ReadonlySet<string>>(new Set());

  readonly total = ALARMAS.length;
  readonly activas = ALARMAS.filter((a) => a.estado === 'activa').length;

  readonly visibles = computed<Alarma[]>(() => {
    const texto = this.busqueda().trim().toLowerCase();
    const tipo = this.filtroTipo();
    const rutina = this.filtroRutina();
    const estado = this.filtroEstado();

    return ALARMAS.filter((alarma) => {
      if (texto && !alarma.titulo.toLowerCase().includes(texto)) {
        return false;
      }
      if (tipo && alarma.tipo !== tipo) {
        return false;
      }
      if (rutina && alarma.rutina !== rutina) {
        return false;
      }
      if (estado && alarma.estado !== estado) {
        return false;
      }
      return true;
    });
  });

  readonly hayFiltros = computed(
    () => !!(this.busqueda() || this.filtroTipo() || this.filtroRutina() || this.filtroEstado()),
  );

  readonly totalSeleccionadas = computed(() => this.seleccionadas().size);

  readonly todasVisiblesMarcadas = computed(() => {
    const visibles = this.visibles();
    return visibles.length > 0 && visibles.every((a) => this.seleccionadas().has(a.id));
  });

  readonly algunaVisibleMarcada = computed(
    () =>
      this.visibles().some((a) => this.seleccionadas().has(a.id)) && !this.todasVisiblesMarcadas(),
  );

  estaMarcada(id: string): boolean {
    return this.seleccionadas().has(id);
  }

  alternar(id: string): void {
    const copia = new Set(this.seleccionadas());
    if (copia.has(id)) {
      copia.delete(id);
    } else {
      copia.add(id);
    }
    this.seleccionadas.set(copia);
  }

  alternarTodas(): void {
    if (this.todasVisiblesMarcadas()) {
      const copia = new Set(this.seleccionadas());
      this.visibles().forEach((a) => copia.delete(a.id));
      this.seleccionadas.set(copia);
      return;
    }

    const copia = new Set(this.seleccionadas());
    this.visibles().forEach((a) => copia.add(a.id));
    this.seleccionadas.set(copia);
  }

  limpiarFiltros(): void {
    this.busqueda.set('');
    this.filtroTipo.set('');
    this.filtroRutina.set('');
    this.filtroEstado.set('');
  }

  escribirBusqueda(evento: Event): void {
    this.busqueda.set((evento.target as HTMLInputElement).value);
  }

  elegirTipo(evento: Event): void {
    this.filtroTipo.set((evento.target as HTMLSelectElement).value as TipoAlarma | '');
  }

  elegirRutina(evento: Event): void {
    this.filtroRutina.set((evento.target as HTMLSelectElement).value);
  }

  elegirEstado(evento: Event): void {
    this.filtroEstado.set((evento.target as HTMLSelectElement).value as EstadoActivacion | '');
  }
}
