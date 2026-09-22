export type TipoAlarma = 'critica' | 'cauta' | 'compartida' | 'recordatorio';

export type EstadoActivacion = 'activa' | 'apagada';

export interface Alarma {
  id: string;
  hora: string;
  titulo: string;
  tipo: TipoAlarma;
  rutina: string;
  estado: EstadoActivacion;
}

export const ETIQUETA_TIPO: Record<TipoAlarma, string> = {
  critica: 'Crítica',
  cauta: 'Cauta',
  compartida: 'Compartida',
  recordatorio: 'Recordatorio',
};

export const ETIQUETA_ACTIVACION: Record<EstadoActivacion, string> = {
  activa: 'Activa',
  apagada: 'Apagada',
};

export const SIN_RUTINA = 'Sin rutina';

export const ALARMAS: readonly Alarma[] = [
  {
    id: 'a1',
    hora: '6:00',
    titulo: 'Salir hacia la oficina',
    tipo: 'critica',
    rutina: 'Día de oficina',
    estado: 'activa',
  },
  {
    id: 'a2',
    hora: '6:30',
    titulo: 'Salir a trotar',
    tipo: 'critica',
    rutina: 'Entrenamiento',
    estado: 'activa',
  },
  {
    id: 'a3',
    hora: '8:00',
    titulo: 'Medicina de mamá',
    tipo: 'compartida',
    rutina: 'Todos los días',
    estado: 'activa',
  },
  {
    id: 'a4',
    hora: '9:00',
    titulo: 'Partido del sábado',
    tipo: 'compartida',
    rutina: 'Fin de semana',
    estado: 'activa',
  },
  {
    id: 'a5',
    hora: '12:30',
    titulo: 'Almorzar sin pantalla',
    tipo: 'cauta',
    rutina: 'Día de oficina',
    estado: 'apagada',
  },
  {
    id: 'a6',
    hora: '13:30',
    titulo: 'Reunión con el cliente',
    tipo: 'cauta',
    rutina: 'Día de oficina',
    estado: 'activa',
  },
  {
    id: 'a7',
    hora: '17:00',
    titulo: 'Salir hacia la clase',
    tipo: 'critica',
    rutina: 'Día de estudio',
    estado: 'activa',
  },
  {
    id: 'a8',
    hora: '18:00',
    titulo: 'Gimnasio',
    tipo: 'recordatorio',
    rutina: 'Entrenamiento',
    estado: 'activa',
  },
  {
    id: 'a9',
    hora: '19:00',
    titulo: 'Entrega de UX',
    tipo: 'recordatorio',
    rutina: 'Día de estudio',
    estado: 'apagada',
  },
  {
    id: 'a10',
    hora: '20:00',
    titulo: 'Leer el capítulo 4',
    tipo: 'recordatorio',
    rutina: 'Día de estudio',
    estado: 'activa',
  },
  {
    id: 'a11',
    hora: '21:30',
    titulo: 'Repasar para el parcial',
    tipo: 'cauta',
    rutina: 'Día de estudio',
    estado: 'apagada',
  },
  {
    id: 'a12',
    hora: '22:00',
    titulo: 'Tomar medicamento',
    tipo: 'compartida',
    rutina: SIN_RUTINA,
    estado: 'activa',
  },
];

export const TIPOS: readonly TipoAlarma[] = ['critica', 'cauta', 'compartida', 'recordatorio'];

export const RUTINAS: readonly string[] = [
  'Día de oficina',
  'Día de estudio',
  'Entrenamiento',
  'Fin de semana',
  'Todos los días',
  SIN_RUTINA,
];
