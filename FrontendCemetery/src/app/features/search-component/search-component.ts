import { Component, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CementerioService } from '../../core/services/cementerioService';
import { Cementerio } from '../../interfaces/cementerio/cementerio';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-search-component',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './search-component.html',
  styleUrl: './search-component.css',
})
export class SearchComponent {
  private cementerioService = inject(CementerioService);

  cementerios = signal<Cementerio[]>([]);
  filtroTexto = signal<string>('');

  cementeriosFiltrados = computed(() => {
    const todos = this.cementerioService.cementerios();
    const busqueda = this.filtroTexto().toLowerCase().trim();

    if (!busqueda) return todos;

    return todos.filter(
      (c) =>
        c.nombre.toLowerCase().includes(busqueda) || c.direccion.toLowerCase().includes(busqueda),
    );
  });

  ngOnInit() {
    this.cementerioService.loadAll();
  }

  private obtenerListadoCompleto() {
    this.cementerioService.loadAll();
  }

  verMapa(cementerio: Cementerio) {
    console.log('Abriendo detalles de:', cementerio.nombre);
  }
}
