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

  ngOnInit(): void {
    this.clienteId.set(this.authService.getUsuarioId());
    this.getDifuntos();
  }

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

  onImageError(difuntoId: number, nombreFoto: string) {
    console.error(`Error cargando foto para difunto ID ${difuntoId}`);

    this.fotosConError.add(difuntoId);
  }

  onImageLoad(difuntoId: number) {
    console.log(`Foto cargada correctamente para difunto ID ${difuntoId}`);
  }

  shouldShowPlaceholder(difuntoId: number): boolean {
    return this.fotosConError.has(difuntoId);
  }
}
