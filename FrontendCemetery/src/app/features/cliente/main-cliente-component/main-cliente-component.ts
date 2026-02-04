import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/authService';
import { DifuntoService } from '../../../core/services/difuntoService';
import { ConcesionService } from '../../../core/services/concesionService';
import { ServicioService } from '../../../core/services/servicioService';

@Component({
  selector: 'app-main-cliente-component',
  imports: [CommonModule],
  templateUrl: './main-cliente-component.html',
  styleUrl: './main-cliente-component.css',
})
export class MainClienteComponent {
  private authService = inject(AuthService);
  private difuntoService = inject(DifuntoService);
  private concesionService = inject(ConcesionService);
  private servicioService = inject(ServicioService);

  clienteId = signal<number>(0);

  difuntos = signal<any[]>([]);
  concesiones = signal<any[]>([]);
  servicios = signal<any[]>([]);

  amountDifuntos = computed(() => this.difuntos().length);

  amountCementerios = computed(() => {
    const uniqueCementerios = new Set(this.concesiones().map((c) => c.cementerioId));
    return uniqueCementerios.size;
  });

  amountServicios = computed(() => this.servicios().length);

  ngOnInit(): void {
    this.clienteId.set(this.authService.getUsuarioId());
    this.loadData();
  }

  loadData() {
    const id = this.clienteId();
    if (!id) return;

    // Cargar Difuntos
    this.difuntoService.loadAllByCliente(id).subscribe({
      next: (data) => this.difuntos.set(data),
      error: (err) => console.error('Error cargando difuntos', err),
    });

    // Cargar Concesiones (para calcular cementerios)

    this.concesionService.findAllByCliente(id).subscribe({
      next: (data) => this.concesiones.set(data),
      error: (err) => console.error('Error cargando concesiones', err),
    });
  }
}
