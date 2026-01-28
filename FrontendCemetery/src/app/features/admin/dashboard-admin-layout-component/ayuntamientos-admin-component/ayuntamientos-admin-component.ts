import { Component, inject, signal } from '@angular/core';
import { AyuntamientoService } from '../../../../core/services/ayuntamientoService';

@Component({
  selector: 'app-ayuntamientos-admin-component',
  imports: [],
  templateUrl: './ayuntamientos-admin-component.html',
  styleUrl: './ayuntamientos-admin-component.css',
})
export class AyuntamientosAdminComponent {
  public ayuntamientoService = inject(AyuntamientoService);

  ngOnInit(): void {
    this.ayuntamientoService.loadAll();
  }

  elementosPorPagina = 5;
  paginaActual = signal(1);

  // cambiar de página
  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  // Cálculo de páginas
  totalPaginas() {
    return Math.ceil(this.ayuntamientoService.amount() / this.elementosPorPagina);
  }

  // obtener solo los cementerios de la página actual
  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.ayuntamientoService.ayuntamientos().slice(inicio, fin);
  }
}
