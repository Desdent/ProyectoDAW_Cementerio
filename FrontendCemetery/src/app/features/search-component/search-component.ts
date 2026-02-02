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

  /**
   * Calculo la lista de cementerios filtrados basándome en el texto de búsqueda introducido.
   */
  cementeriosFiltrados = computed(() => {
    // Obtengo la lista completa de cementerios desde el servicio.
    const todos = this.cementerioService.cementerios();
    // Normalizo el texto de búsqueda a minúsculas y elimino espacios en blanco.
    const busqueda = this.filtroTexto().toLowerCase().trim();

    // Si no hay texto de búsqueda, devuelvo la lista completa.
    if (!busqueda) return todos;

    // Filtro los cementerios comprobando si el nombre o la dirección contienen el texto buscado.
    return todos.filter(
      (c) =>
        c.nombre.toLowerCase().includes(busqueda) || c.direccion.toLowerCase().includes(busqueda),
    );
  });

  /**
   * Inicializo el componente solicitando la carga de todos los cementerios disponibles.
   */
  ngOnInit() {
    this.cementerioService.loadAll();
  }

  /**
   * Solicito al servicio que refresque el listado completo de cementerios.
   */
  private obtenerListadoCompleto() {
    this.cementerioService.loadAll();
  }

  /**
   * Registro en consola la apertura de los detalles de un cementerio específico.
   * @param cementerio Objeto del cementerio seleccionado.
   */
  verMapa(cementerio: Cementerio) {
    console.log('Abriendo detalles de:', cementerio.nombre);
  }
}
