import { Component, inject, OnInit, signal } from '@angular/core';
import { Difunto } from '../../../interfaces/difunto/difunto';
import { DifuntoService } from '../../../core/services/difuntoService';
import { AuthService } from '../../../core/services/authService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-difuntos-cliente-component',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './difuntos-cliente-component.html',
  styleUrl: './difuntos-cliente-component.css',
})
export class DifuntosClienteComponent implements OnInit {
  private difuntoService = inject(DifuntoService);
  private authService = inject(AuthService);

  difuntos = signal<Difunto[]>([]);
  clienteId = signal<number>(0);

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
}
