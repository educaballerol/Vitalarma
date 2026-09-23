import type { TipoAlarma } from './alarma.model';

export interface DiaSemana {
  etiqueta: string;
  activo: boolean;
}

export interface AlarmaRutina {
  id: string;
  hora: string;
  titulo: string;
  tipo: TipoAlarma;
}

export interface RutinaResumen {
  id: string;
  nombre: string;
  dias: DiaSemana[];
  alarmas: AlarmaRutina[];
}

export const DIAS_SEMANA_BASE: readonly string[] = [
  'Lun',
  'Mar',
  'Mié',
  'Jue',
  'Vie',
  'Sáb',
  'Dom',
];

export const RUTINAS_RESUMEN: readonly RutinaResumen[] = [
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
      { id: 'r1-a1', hora: '6:00', titulo: 'Salir hacia la oficina', tipo: 'critica' },
      { id: 'r1-a2', hora: '13:30', titulo: 'Reunión con el cliente', tipo: 'cauta' },
      { id: 'r1-a3', hora: '17:00', titulo: 'Salir hacia la clase', tipo: 'critica' },
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
      { id: 'r2-a1', hora: '9:00', titulo: 'Bloque de lectura', tipo: 'recordatorio' },
      { id: 'r2-a2', hora: '15:00', titulo: 'Entrega de UX', tipo: 'recordatorio' },
    ],
  },
];
