import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CementerioService } from '../../core/services/cementerioService';
import { Cementerio } from '../../interfaces/cementerio/cementerio';
import { RouterLink } from '@angular/router';
import * as bootstrap from 'bootstrap';
import { ZonaService } from '../../core/services/zonaService';
import { Zona } from '../../interfaces/zona/zona';
import { Router } from '@angular/router';
// import imageMapResize from 'image-map-resizer';

@Component({
  selector: 'app-search-component',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './search-component.html',
  styleUrl: './search-component.css',
})
export class SearchComponent {
  private cementerioService = inject(CementerioService);
  private zonaService = inject(ZonaService);
  private router = inject(Router);

  @ViewChild('htmlModal') htmlModal!: ElementRef;

  modalBootstrap: bootstrap.Modal | undefined;
  cementerios = signal<Cementerio[]>([]);
  filtroTexto = signal<string>('');
  mapaSeleccionado = signal<string>('');
  coordenadas = signal<string[]>([]);
  zonasCementerio = signal<Zona[]>([]);
  cementerioIdSeleccionado = signal<number>(0);

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

  abrirModal(id: number) {
    this.cementerioIdSeleccionado.set(id);
    this.cementerioService.find(id).subscribe({
      next: (data) => {
        this.mapaSeleccionado.set(data.mapa!);
      },
    });
    this.zonaService.findAllByCementerioId(id).subscribe({
      next: (data) => {
        this.zonasCementerio.set(data);
      },
    });
    if (this.htmlModal && this.htmlModal.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.htmlModal.nativeElement);
      this.modalBootstrap.show();
    }
  }

  cerrarModal() {
    if (this.modalBootstrap) {
      this.modalBootstrap.hide();
    }
  }

  viewBoxValue = signal<string>('0 0 1000 1000'); // Valor por defecto

  // Método para detectar el tamaño real de la imagen al cargar
  onImageLoad(event: any) {
    const img = event.target;
    // Ajustamos el sistema de coordenadas del SVG al tamaño real de la imagen
    this.viewBoxValue.set(`0 0 ${img.naturalWidth} ${img.naturalHeight}`);
  }

  navegarAZona(zonaId: number) {
    this.cerrarModal(); // Cerramos el modal antes de irnos
    // Navegamos usando el router de Angular
    this.router.navigate(['/cementerio', this.cementerioIdSeleccionado(), { zona: zonaId }]);
  }
}
