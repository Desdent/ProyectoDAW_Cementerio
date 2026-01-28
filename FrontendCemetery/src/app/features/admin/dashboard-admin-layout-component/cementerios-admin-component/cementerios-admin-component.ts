import { Component, inject, signal } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { Cementerio } from '../../../../interfaces/cementerio';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-cementerios-admin-component',
  imports: [],
  templateUrl: './cementerios-admin-component.html',
  styleUrl: './cementerios-admin-component.css',
})
export class CementeriosAdminComponent {
  public cementerioService = inject(CementerioService);

  ngOnInit(): void {
    this.cementerioService.loadAll();
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
    return Math.ceil(this.cementerioService.amount() / this.elementosPorPagina);
  }

  // obtener solo los cementerios de la página actual
  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.cementerioService.cementerios().slice(inicio, fin);
  }
}
