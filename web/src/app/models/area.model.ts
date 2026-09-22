import type { AreaId } from './panel.model';

export type EstadoAlarma = 'cumplida' | 'pospuesta' | 'fallida';

export interface AlarmaDelArea {
  hora: string;
  titulo: string;
  tipo: string;
  rutina: string;
  estado: EstadoAlarma;
}

export interface SemanaHistorica {
  etiqueta: string;
  horas: number;
}

export interface DetalleArea {
  id: AreaId;
  nombre: string;
  semanas: SemanaHistorica[];
  alarmas: AlarmaDelArea[];
}

export const ETIQUETA_ESTADO: Record<EstadoAlarma, string> = {
  cumplida: 'Cumplida',
  pospuesta: 'Pospuesta',
  fallida: 'Fallida',
};

const SEMANAS = ['Semana 1', 'Semana 2', 'Semana 3', 'Semana 4'];

function semanas(horas: number[]): SemanaHistorica[] {
  return horas.map((h, i) => ({ etiqueta: SEMANAS[i], horas: h }));
}

export const DETALLES: readonly DetalleArea[] = [
  {
    id: 'trabajo',
    nombre: 'Trabajo',
    semanas: semanas([34, 44, 38, 41]),
    alarmas: [
      {
        hora: '6:00',
        titulo: 'Salir hacia la oficina',
        tipo: 'Crítica',
        rutina: 'Día de oficina',
        estado: 'cumplida',
      },
      {
        hora: '13:30',
        titulo: 'Reunión con el cliente',
        tipo: 'Cauta',
        rutina: 'Día de oficina',
        estado: 'pospuesta',
      },
      {
        hora: '17:00',
        titulo: 'Salir hacia la clase',
        tipo: 'Crítica',
        rutina: 'Día de estudio',
        estado: 'cumplida',
      },
    ],
  },
  {
    id: 'estudio',
    nombre: 'Estudio',
    semanas: semanas([18, 25, 20, 22]),
    alarmas: [
      {
        hora: '19:00',
        titulo: 'Entrega de UX',
        tipo: 'Recordatorio',
        rutina: 'Día de estudio',
        estado: 'fallida',
      },
      {
        hora: '20:00',
        titulo: 'Leer el capítulo 4',
        tipo: 'Recordatorio',
        rutina: 'Día de estudio',
        estado: 'cumplida',
      },
      {
        hora: '21:30',
        titulo: 'Repasar para el parcial',
        tipo: 'Cauta',
        rutina: 'Día de estudio',
        estado: 'pospuesta',
      },
    ],
  },
  {
    id: 'personal',
    nombre: 'Personal',
    semanas: semanas([12, 14, 17, 15]),
    alarmas: [
      {
        hora: '8:00',
        titulo: 'Medicina de mamá',
        tipo: 'Compartida',
        rutina: 'Todos los días',
        estado: 'cumplida',
      },
      {
        hora: '12:30',
        titulo: 'Almorzar sin pantalla',
        tipo: 'Cauta',
        rutina: 'Día de oficina',
        estado: 'pospuesta',
      },
      {
        hora: '22:00',
        titulo: 'Llamar a la familia',
        tipo: 'Recordatorio',
        rutina: 'Fin de semana',
        estado: 'cumplida',
      },
    ],
  },
  {
    id: 'deporte',
    nombre: 'Deporte',
    semanas: semanas([6, 5, 9, 8]),
    alarmas: [
      {
        hora: '6:30',
        titulo: 'Salir a trotar',
        tipo: 'Crítica',
        rutina: 'Entrenamiento',
        estado: 'fallida',
      },
      {
        hora: '18:00',
        titulo: 'Gimnasio',
        tipo: 'Recordatorio',
        rutina: 'Entrenamiento',
        estado: 'cumplida',
      },
      {
        hora: '9:00',
        titulo: 'Partido del sábado',
        tipo: 'Compartida',
        rutina: 'Fin de semana',
        estado: 'cumplida',
      },
    ],
  },
];

export function detalleDe(id: string | undefined): DetalleArea {
  return DETALLES.find((d) => d.id === id) ?? DETALLES[0];
}
