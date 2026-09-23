import { Component, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { TopBar } from '../../shared/top-bar/top-bar';
import {
  RUTINAS,
  SIN_RUTINA,
  TIPOS,
  ETIQUETA_TIPO,
  type TipoAlarma,
} from '../../models/alarma.model';

interface DiaSemana {
  etiqueta: string;
  activo: boolean;
}

interface RespaldoDispositivo {
  nombre: string;
  demora: string;
  tipo: 'dispositivo' | 'persona';
}

const DETALLE_TIPO: Record<TipoAlarma, string> = {
  critica: 'sirena ascendente',
  cauta: 'vibración corta',
  compartida: 'timbre doble',
  recordatorio: 'un toque',
};

const AREAS_DE_VIDA: readonly string[] = ['Trabajo', 'Estudio', 'Personal', 'Deporte'];

const OPCIONES_APAGADO: readonly string[] = [
  'Escanear un objeto al azar',
  'Resolver un cálculo',
  'Caminar hasta otro cuarto',
];

const RESPALDOS: readonly RespaldoDispositivo[] = [
  { nombre: 'Reloj de Humberto', demora: 'a los 5 minutos', tipo: 'dispositivo' },
  { nombre: 'Parlante de la sala', demora: 'a los 10 minutos', tipo: 'dispositivo' },
  { nombre: 'Eduardo Caballero', demora: 'a los 10 minutos', tipo: 'persona' },
];

@Component({
  selector: 'app-nueva-alarma',
  imports: [TopBar, RouterLink],
  templateUrl: './nueva-alarma.html',
  styleUrl: './nueva-alarma.scss',
})
export class NuevaAlarma {
  readonly tipos = TIPOS;
  readonly etiquetaTipo = ETIQUETA_TIPO;
  readonly detalleTipo = DETALLE_TIPO;
  readonly rutinas = RUTINAS;
  readonly sinRutina = SIN_RUTINA;
  readonly areas = AREAS_DE_VIDA;
  readonly opcionesApagado = OPCIONES_APAGADO;

  readonly hora = signal('06:00');
  readonly titulo = signal('Salir hacia la oficina');
  readonly tipo = signal<TipoAlarma>('critica');
  readonly comoSeApaga = signal(OPCIONES_APAGADO[0]);
  readonly rutina = signal(RUTINAS[0]);
  readonly area = signal(AREAS_DE_VIDA[0]);
  readonly respaldos = RESPALDOS;

  readonly dias = signal<DiaSemana[]>([
    { etiqueta: 'Lun', activo: true },
    { etiqueta: 'Mar', activo: true },
    { etiqueta: 'Mié', activo: true },
    { etiqueta: 'Jue', activo: true },
    { etiqueta: 'Vie', activo: true },
    { etiqueta: 'Sáb', activo: false },
    { etiqueta: 'Dom', activo: false },
  ]);

  constructor(private router: Router) {}

  escribirHora(evento: Event): void {
    this.hora.set((evento.target as HTMLInputElement).value);
  }

  escribirTitulo(evento: Event): void {
    this.titulo.set((evento.target as HTMLInputElement).value);
  }

  elegirTipo(tipo: TipoAlarma): void {
    this.tipo.set(tipo);
  }

  elegirApagado(evento: Event): void {
    this.comoSeApaga.set((evento.target as HTMLSelectElement).value);
  }

  elegirRutina(evento: Event): void {
    this.rutina.set((evento.target as HTMLSelectElement).value);
  }

  elegirArea(evento: Event): void {
    this.area.set((evento.target as HTMLSelectElement).value);
  }

  alternarDia(indice: number): void {
    this.dias.update((dias) =>
      dias.map((dia, i) => (i === indice ? { ...dia, activo: !dia.activo } : dia)),
    );
  }

  crearAlarma(): void {
    // Maquetación: sin backend, no persiste nada. Solo navega de vuelta.
    this.router.navigate(['/alarmas']);
  }

  cancelar(): void {
    this.router.navigate(['/alarmas']);
  }
}
