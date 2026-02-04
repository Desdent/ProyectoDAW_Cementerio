import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Difunto } from '../../../interfaces/difunto/difunto';
import { DifuntoService } from '../../../core/services/difuntoService';
import { AuthService } from '../../../core/services/authService';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-difuntos-cliente-component',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './difuntos-cliente-component.html',
  styleUrl: './difuntos-cliente-component.css',
})
export class DifuntosClienteComponent implements OnInit {
  private difuntoService = inject(DifuntoService);
  private authService = inject(AuthService);

  difuntos = signal<Difunto[]>([]);
  clienteId = signal<number>(0);
  filtroTexto = signal<string>('');
  typeSort = signal<string>('');

  rutaBaseFotos = 'http://localhost:8080/uploads/fotos/';

  fotosConError = new Set<number>();

  /**
   * Inicializa el componente recuperando el ID del cliente y sus difuntos.
   */
  ngOnInit(): void {
    this.clienteId.set(this.authService.getUsuarioId());
    this.getDifuntos();
  }

  /**
   * Carga la lista de difuntos asociados al cliente actual.
   */
  getDifuntos() {
    this.difuntoService.loadAllByCliente(this.clienteId()).subscribe({
      next: (res) => {
        console.log('=== DIFUNTOS CARGADOS ===');
        console.log('Total:', res.length);

        this.difuntos.set(res);
      },
      error: (err) => console.error('Error al obtener difuntos', err),
    });
  }

  /**
   * Maneja errores en la carga de imágenes de difuntos.
   * @param difuntoId ID del difunto cuya imagen falló.
   * @param nombreFoto Nombre del archivo que dio error.
   */
  onImageError(difuntoId: number, nombreFoto: string) {
    console.error(`Error cargando foto para difunto ID ${difuntoId}`);
    // Añadimos el ID al set para que la UI sepa que debe mostrar un placeholder.
    this.fotosConError.add(difuntoId);
  }

  /**
   * Log de confirmación cuando una imagen se carga correctamente.
   * @param difuntoId ID del difunto.
   */
  onImageLoad(difuntoId: number) {
    console.log(`Foto cargada correctamente para difunto ID ${difuntoId}`);
  }

  /**
   * Determina si se debe mostrar la imagen por defecto.
   * @param difuntoId ID del difunto a comprobar.
   * @returns True si la imagen falló o no existe.
   */
  shouldShowPlaceholder(difuntoId: number): boolean {
    return this.fotosConError.has(difuntoId);
  }

  difuntosFiltrados = computed(() => {
    // Obtengo la lista completa de cementerios desde el servicio.
    const todos = this.difuntos();
    // Normalizo el texto de búsqueda a minúsculas y elimino espacios en blanco.
    const busqueda = this.filtroTexto().toLowerCase().trim();

    // Si no hay texto de búsqueda, devuelvo la lista completa.
    if (!busqueda) return todos;

    // Filtro los cementerios comprobando si el nombre o la dirección contienen el texto buscado.
    return todos.filter(
      (c) =>
        (c.nombre + ' ' + c.apellido1 + ' ' + c.apellido2).toLowerCase().includes(busqueda) ||
        c.yearNacimiento.toString().toLowerCase().includes(busqueda) ||
        c.yearDefuncion.toString().toLowerCase().includes(busqueda) ||
        c.fechaEntierro.toString().includes(busqueda) ||
        c.id.toString().includes(busqueda),
    );
  });

  sort(term: string) {
    switch (term) {
      case 'nombre':
        if (this.typeSort() != 'nombreAsc') {
          this.typeSort.set('nombreAsc');
          this.difuntosFiltrados().sort((a, b) =>
            (a.nombre + ' ' + a.apellido1 + ' ' + a.apellido2).localeCompare(
              b.nombre + ' ' + b.apellido1 + ' ' + b.apellido2,
            ),
          );
        } else {
          this.typeSort.set('nombreDesc');
          this.difuntosFiltrados().sort((a, b) =>
            (b.nombre + ' ' + b.apellido1 + ' ' + b.apellido2).localeCompare(
              a.nombre + ' ' + a.apellido1 + ' ' + a.apellido2,
            ),
          );
        }
        break;
      case 'an':
        if (this.typeSort() != 'yearNacAsc') {
          this.typeSort.set('yearNacAsc');
          this.difuntosFiltrados().sort((a, b) => a.yearNacimiento - b.yearNacimiento);
        } else {
          this.typeSort.set('yearNacDesc');
          this.difuntosFiltrados().sort((a, b) => b.yearNacimiento - a.yearNacimiento);
        }
        break;
      case 'ad':
        if (this.typeSort() != 'yearDefAsc') {
          this.typeSort.set('yearDefAsc');
          this.difuntosFiltrados().sort((a, b) => a.yearDefuncion - b.yearDefuncion);
        } else {
          this.typeSort.set('yearDefDesc');
          this.difuntosFiltrados().sort((a, b) => b.yearDefuncion - a.yearDefuncion);
        }
        break;
      case 'fe':
        if (this.typeSort() != 'entierroAsc') {
          this.typeSort.set('entierroAsc');
          this.difuntosFiltrados().sort((a, b) =>
            a.fechaEntierro.toDateString().localeCompare(b.fechaEntierro.toDateString()),
          );
        } else {
          this.typeSort.set('entierroDesc');
          this.difuntosFiltrados().sort((a, b) =>
            b.fechaEntierro.toDateString().localeCompare(a.fechaEntierro.toDateString()),
          );
        }
        break;
      case 'u':
        if (this.typeSort() != 'ubiAsc') {
          this.typeSort.set('ubiAsc');
          this.difuntosFiltrados().sort((a, b) => a.parcelaId - b.parcelaId);
        } else {
          this.typeSort.set('ubiDesc');
          this.difuntosFiltrados().sort((a, b) => a.parcelaId - b.parcelaId);
        }
        break;
    }
  }
}
