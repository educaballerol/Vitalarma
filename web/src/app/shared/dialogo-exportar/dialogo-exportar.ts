import {
  AfterViewInit,
  Component,
  ElementRef,
  computed,
  input,
  output,
  signal,
  viewChild,
} from '@angular/core';

type Formato = 'pdf' | 'hoja';

@Component({
  selector: 'app-dialogo-exportar',
  templateUrl: './dialogo-exportar.html',
  styleUrl: './dialogo-exportar.scss',
})
export class DialogoExportar implements AfterViewInit {
  readonly area = input.required<string>();
  readonly cerrado = output<void>();

  private readonly dialogo = viewChild.required<ElementRef<HTMLDialogElement>>('dialogo');

  readonly periodo = signal('Últimas 4 semanas');
  readonly incluyeGrafica = signal(true);
  readonly incluyeTabla = signal(true);
  readonly incluyeNotas = signal(false);
  readonly formato = signal<Formato>('pdf');
  readonly descargado = signal(false);

  readonly periodos = [
    'Esta semana',
    'Últimas 4 semanas',
    'Últimos 3 meses',
    'Este año',
  ];

  readonly nombreArchivo = computed(() => {
    const area = this.area().toLowerCase().replace(/\s+/g, '-');
    const extension = this.formato() === 'pdf' ? 'pdf' : 'xlsx';
    return `vitalarma-${area}.${extension}`;
  });

  readonly nadaSeleccionado = computed(
    () => !this.incluyeGrafica() && !this.incluyeTabla() && !this.incluyeNotas(),
  );

  ngAfterViewInit(): void {
    this.dialogo().nativeElement.showModal();
  }

  cerrar(): void {
    this.dialogo().nativeElement.close();
  }

  alCerrarse(): void {
    this.cerrado.emit();
  }

  descargar(): void {
    this.descargado.set(true);
  }

  elegirPeriodo(evento: Event): void {
    this.periodo.set((evento.target as HTMLSelectElement).value);
  }

  elegirFormato(valor: Formato): void {
    this.formato.set(valor);
  }

  alternarGrafica(evento: Event): void {
    this.incluyeGrafica.set((evento.target as HTMLInputElement).checked);
  }

  alternarTabla(evento: Event): void {
    this.incluyeTabla.set((evento.target as HTMLInputElement).checked);
  }

  alternarNotas(evento: Event): void {
    this.incluyeNotas.set((evento.target as HTMLInputElement).checked);
  }
}
