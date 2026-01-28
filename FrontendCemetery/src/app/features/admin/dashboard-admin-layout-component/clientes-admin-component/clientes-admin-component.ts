import { Component, inject, signal } from '@angular/core';
import { ClienteService } from '../../../../core/services/clienteService';

@Component({
  selector: 'app-clientes-admin-component',
  imports: [],
  templateUrl: './clientes-admin-component.html',
  styleUrl: './clientes-admin-component.css',
})
export class ClientesAdminComponent {
  public clienteService = inject(ClienteService);

  ngOnInit(): void {
    this.clienteService.loadAll();
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
    return Math.ceil(this.clienteService.amount() / this.elementosPorPagina);
  }

  // obtener solo los cementerios de la página actual
  get clientesPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.clienteService.clientes().slice(inicio, fin);
  }
}
