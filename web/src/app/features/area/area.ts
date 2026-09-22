import { Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import { ETIQUETA_ESTADO, detalleDe, type EstadoAlarma } from '../../models/area.model';

interface Punto {
  etiqueta: string;
  horas: number;
  x: number;
  y: number;
}

@Component({
  selector: 'app-area',
  imports: [TopBar, RouterLink],
  templateUrl: './area.html',
  styleUrl: './area.scss',
})
export class Area {
  readonly id = input<string>();

  readonly detalle = computed(() => detalleDe(this.id()));

  readonly totalPeriodo = computed(() => {
    const semanas = this.detalle().semanas;
    return semanas[semanas.length - 1].horas;
  });

  readonly promedio = computed(() => {
    const semanas = this.detalle().semanas;
    const suma = semanas.reduce((acc, s) => acc + s.horas, 0);
    return suma / semanas.length;
  });

  readonly variacion = computed(() => {
    const semanas = this.detalle().semanas;
    const anterior = semanas[semanas.length - 2]?.horas ?? 0;
    if (anterior === 0) {
      return 0;
    }
    return Math.round(((this.totalPeriodo() - anterior) / anterior) * 100);
  });

  readonly topeEje = computed(() => {
    const maximo = Math.max(...this.detalle().semanas.map((s) => s.horas));
    return Math.max(12, Math.ceil(maximo / 12) * 12);
  });

  readonly marcasEje = computed(() => {
    const tope = this.topeEje();
    return [tope, (tope * 3) / 4, tope / 2, tope / 4, 0];
  });

  readonly puntos = computed<Punto[]>(() => {
    const semanas = this.detalle().semanas;
    const tope = this.topeEje();
    return semanas.map((semana, i) => ({
      etiqueta: semana.etiqueta,
      horas: semana.horas,
      x: (i / (semanas.length - 1)) * 100,
      y: (semana.horas / tope) * 100,
    }));
  });

  readonly trazo = computed(() =>
    this.puntos()
      .map((p) => `${p.x},${100 - p.y}`)
      .join(' '),
  );

  readonly promedioFormateado = computed(() =>
    this.promedio().toLocaleString('es-CO', {
      minimumFractionDigits: 1,
      maximumFractionDigits: 1,
    }),
  );

  readonly variacionFormateada = computed(() => {
    const v = this.variacion();
    return `${v > 0 ? '+' : ''}${v}%`;
  });

  etiquetaEstado(estado: EstadoAlarma): string {
    return ETIQUETA_ESTADO[estado];
  }
}
