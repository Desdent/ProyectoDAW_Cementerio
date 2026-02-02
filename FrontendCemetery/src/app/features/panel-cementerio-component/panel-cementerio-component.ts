import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CementerioService } from '../../core/services/cementerioService';
import { ZonaService } from '../../core/services/zonaService';
import { Cementerio } from '../../interfaces/cementerio/cementerio';
import { Zona } from '../../interfaces/zona/zona';
import { CommonModule, SlicePipe } from '@angular/common';
import { Parcela } from '../../interfaces/parcela/parcela';
import { ParcelaService } from '../../core/services/parcelaService';

@Component({
  selector: 'app-panel-cementerio-component',
  standalone: true,
  imports: [CommonModule, SlicePipe, RouterLink],
  templateUrl: './panel-cementerio-component.html',
  styleUrl: './panel-cementerio-component.css',
})
export class PanelCementerioComponent {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cementerioService = inject(CementerioService);
  private zonaService = inject(ZonaService);
  private parcelaService = inject(ParcelaService);

  cementerio = signal<Cementerio | null>(null);
  zonas = signal<Zona[]>([]);
  parcelas = signal<Parcela[]>([]);
  zonaSeleccionada = signal<Zona | null>(null);
  carrito = signal<Parcela[]>([]);

  /**
   * Inicializo el componente obteniendo el identificador de la ruta y disparando la carga de datos y del carrito.
   */
  ngOnInit(): void {
    // Extraigo el parámetro 'id' de la URL actual para saber qué cementerio mostrar.
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.cargarDatos(Number(id));
    } else {
      // Si no hay ID en la ruta, regreso al buscador por seguridad.
      this.volver();
    }

    this.cargarCarrito();
  }

  /**
   * Solicito al servicio los detalles del cementerio y sus zonas asociadas mediante peticiones asíncronas.
   * @param id Identificador del cementerio.
   */
  private cargarDatos(id: number): void {
    this.cementerioService.find(id).subscribe({
      next: (data) => this.cementerio.set(data),
      error: (err) => console.error('Error al cargar cementerio:', err),
    });

    this.zonaService.findAllByCementerioId(id).subscribe({
      next: (data) => this.zonas.set(data),
      error: (err) => console.error('Error al cargar zonas:', err),
    });
  }

  /**
   * Recupero los elementos almacenados en el carrito del navegador para sincronizar el estado local.
   */
  private cargarCarrito(): void {
    const data = localStorage.getItem('carrito');
    // Si existen datos previos, los transformo de JSON a objeto; si no, inicializo un array vacío.
    this.carrito.set(data ? JSON.parse(data) : []);
  }

  /**
   * Redirijo al usuario de vuelta a la pantalla del buscador.
   */
  volver(): void {
    this.router.navigate(['/buscador']);
  }

  /**
   * Establezco la zona seleccionada y consulto al servicio todas las parcelas que pertenecen a dicha zona para mostrarlas.
   * @param zona Objeto de la zona a consultar.
   */
  verParcelas(zona: Zona): void {
    this.zonaSeleccionada.set(zona);
    this.parcelaService.findByZonaId(zona.id).subscribe({
      next: (data) => {
        this.parcelas.set(data);
        this.abrirModalParcelas();
      },
      error: (err) => console.error('Error al cargar parcelas:', err),
    });
  }

  /**
   * Muestro el modal de parcelas manipulando directamente las clases de Bootstrap sobre el elemento del DOM.
   */
  abrirModalParcelas() {
    const modal = document.getElementById('modalParcelas');
    // Al no usar ViewChild aquí, accedo directamente al elemento para añadir las clases de visibilidad.
    if (modal) (modal as any).classList.add('show', 'd-block');
  }

  /**
   * Oculto el modal de parcelas eliminando las clases de visualización de Bootstrap.
   */
  cerrarModalParcelas() {
    const modal = document.getElementById('modalParcelas');
    if (modal) (modal as any).classList.remove('show', 'd-block');
  }

  /**
   * Añado una parcela al carrito persistiendo la información en el almacenamiento local del navegador.
   * @param parcela Parcela a añadir.
   */
  addAlCarrito(parcela: Parcela) {
    const carrito = JSON.parse(localStorage.getItem('carrito') || '[]');
    carrito.push(parcela);
    localStorage.setItem('carrito', JSON.stringify(carrito));
    alert(`Parcela F:${parcela.fila} C:${parcela.columna} añadida.`);
  }

  /**
   * Gestiono la inclusión o eliminación de una parcela en el carrito, actualizando tanto la señal reactiva como el almacenamiento persistente.
   * @param parcela Parcela a alternar.
   */
  toggleCarrito(parcela: Parcela): void {
    // Creo una copia del estado actual para no mutar la señal directamente durante la búsqueda.
    let items = [...this.carrito()];
    const index = items.findIndex((p) => p.id === parcela.id);

    if (index === -1) {
      // Si no está en la lista, la añado al final del array.
      items.push(parcela);
    } else {
      // Si ya existe, la elimino del array usando su posición.
      items.splice(index, 1);
    }

    // Sincronizo los cambios con el almacenamiento local y actualizo la señal para refrescar la UI.
    localStorage.setItem('carrito', JSON.stringify(items));
    this.carrito.set(items);
  }

  /**
   * Compruebo si una parcela específica ya se encuentra dentro de los elementos del carrito.
   * @param id ID de la parcela.
   */
  estaEnCarrito(id: number): boolean {
    return this.carrito().some((p) => p.id === id);
  }
}
