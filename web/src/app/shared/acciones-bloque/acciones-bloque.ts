import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-acciones-bloque',
  templateUrl: './acciones-bloque.html',
  styleUrl: './acciones-bloque.scss',
})
export class AccionesBloque {
  total = input.required<number>();

  deseleccionarTodo = output<void>();
  cambiarTipo = output<void>();
  moverARutina = output<void>();
  eliminar = output<void>();
}
