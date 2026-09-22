import { Component, computed, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import {
  ALARMAS_FALLIDAS,
  AREAS,
  SEMANA,
  type AreaId,
  type DiaDeLaSemana,
} from '../../models/panel.model';

interface Segmento {
  areaId: AreaId;
  nombre: string;
  horas: number;
  alto: number;
}

interface Columna {
  etiqueta: string;
  nombre: string;
  total: number;
  alto: number;
  segmentos: Segmento[];
}

@Component({
  selector: 'app-panel',
  imports: [TopBar, RouterLink],
  templateUrl: './panel.html',
  styleUrl: './panel.scss',
})
export class Panel {
  readonly areas = AREAS;
  readonly alarmasFallidas = ALARMAS_FALLIDAS;

  private readonly topeEje = 16;
  readonly marcasEje = [16, 12, 8, 4, 0];

  readonly vista = signal<'grafica' | 'tabla'>('grafica');

  readonly totalSemana = computed(() =>
    this.areas.reduce((suma, area) => suma + area.horas, 0),
  );

  readonly columnas = computed<Columna[]>(() =>
    SEMANA.map((dia) => this.aColumna(dia)),
  );

  private aColumna(dia: DiaDeLaSemana): Columna {
    const segmentos = this.areas
      .map((area) => ({
        areaId: area.id,
        nombre: area.nombre,
        horas: dia.horas[area.id] ?? 0,
        alto: ((dia.horas[area.id] ?? 0) / this.topeEje) * 100,
      }))
      .filter((segmento) => segmento.horas > 0);

    const total = segmentos.reduce((suma, segmento) => suma + segmento.horas, 0);

    return {
      etiqueta: dia.etiqueta,
      nombre: dia.nombre,
      total,
      alto: (total / this.topeEje) * 100,
      segmentos,
    };
  }

  horasDe(columna: Columna, areaId: AreaId): number {
    return columna.segmentos.find((s) => s.areaId === areaId)?.horas ?? 0;
  }

  mostrar(vista: 'grafica' | 'tabla'): void {
    this.vista.set(vista);
  }
}
