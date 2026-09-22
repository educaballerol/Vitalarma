export type AreaId = 'trabajo' | 'estudio' | 'personal' | 'deporte';

export interface AreaDeVida {
  id: AreaId;
  nombre: string;
  horas: number;
  porcentaje: number;
}

export interface DiaDeLaSemana {
  etiqueta: string;
  nombre: string;
  horas: Partial<Record<AreaId, number>>;
}

export interface AlarmaFallida {
  titulo: string;
  cuando: string;
  motivo: string;
}

export const AREAS: readonly AreaDeVida[] = [
  { id: 'trabajo', nombre: 'Trabajo', horas: 41, porcentaje: 48 },
  { id: 'estudio', nombre: 'Estudio', horas: 22, porcentaje: 26 },
  { id: 'personal', nombre: 'Personal', horas: 15, porcentaje: 17 },
  { id: 'deporte', nombre: 'Deporte', horas: 8, porcentaje: 9 },
];

export const SEMANA: readonly DiaDeLaSemana[] = [
  { etiqueta: 'Lun', nombre: 'Lunes', horas: { trabajo: 8, estudio: 4, personal: 2, deporte: 1 } },
  { etiqueta: 'Mar', nombre: 'Martes', horas: { trabajo: 9, estudio: 3, personal: 1 } },
  { etiqueta: 'Mié', nombre: 'Miércoles', horas: { trabajo: 8, estudio: 4, personal: 2, deporte: 1 } },
  { etiqueta: 'Jue', nombre: 'Jueves', horas: { trabajo: 9, estudio: 2, personal: 2 } },
  { etiqueta: 'Vie', nombre: 'Viernes', horas: { trabajo: 7, estudio: 4, personal: 2, deporte: 1 } },
  { etiqueta: 'Sáb', nombre: 'Sábado', horas: { estudio: 3, personal: 4, deporte: 3 } },
  { etiqueta: 'Dom', nombre: 'Domingo', horas: { estudio: 2, personal: 2, deporte: 2 } },
];

export const ALARMAS_FALLIDAS: readonly AlarmaFallida[] = [
  { titulo: 'Salir hacia la oficina', cuando: 'martes 6:00', motivo: 'pospuesta 3 veces' },
  { titulo: 'Entrega de UX', cuando: 'jueves 19:00', motivo: 'apagada sin cumplir' },
  { titulo: 'Tomar medicamento', cuando: 'viernes 8:00', motivo: 'sin respuesta' },
];
