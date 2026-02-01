import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Parcela } from '../../interfaces/parcela/parcela';
import { ConcesionService } from '../../core/services/concesionService';
import { concesionPost } from '../../interfaces/concesion/concesionPost';
import { PagoPost } from '../../interfaces/pago/pagoPost';
import { AuthService } from '../../core/services/authService';

@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './carrito-component.html',
  styleUrl: './carrito-component.css',
})
export class CarritoComponent implements OnInit {
  private router = inject(Router);
  private concesionService = inject(ConcesionService);
  private authService = inject(AuthService);

  items = signal<Parcela[]>([]);
  total = computed(() => this.items().length * 1500.0);

  ngOnInit(): void {
    this.cargarDesdeStorage();
  }

  private cargarDesdeStorage(): void {
    const data = localStorage.getItem('carrito');
    if (data) {
      this.items.set(JSON.parse(data));
    }
  }

  eliminar(id: number): void {
    const actualizados = this.items().filter((p) => p.id !== id);
    this.items.set(actualizados);
    localStorage.setItem('carrito', JSON.stringify(actualizados));
  }

  procesarCompra(): void {
    if (this.items().length === 0) return;

    const usuarioId = this.authService.getUsuarioId();
    const hoy = new Date().toISOString().split('T')[0];

    const concesionDto: concesionPost = {
      precio: this.total(),
      fechaInicio: hoy,
      fechaFin: this.sumarAnios(hoy, 10),
      clienteId: usuarioId,
      parcelaIds: this.items().map((p) => p.id),
      pagoId: 0,
    };

    const pagoDto: PagoPost = {
      importe: this.total(),
      fecha: hoy,
      metodo: 'VISA',
      transaccionId: crypto.randomUUID(),
      estado: 'APROBADO',
    };

    this.concesionService.comprarConcesion(concesionDto, pagoDto).subscribe({
      next: (res) => {
        alert(`¡Compra exitosa! Referencia: ${pagoDto.transaccionId}`);
        localStorage.removeItem('carrito');
        this.router.navigate(['/mis-concesiones']);
      },
      error: (err) => {
        console.error('Error en la compra:', err);
        const msg = err.error?.message || 'Error en la transacción bancaria simulada';
        alert('No se pudo completar la compra: ' + msg);
      },
    });
  }

  private sumarAnios(fechaStr: string, anios: number): string {
    const fecha = new Date(fechaStr);
    fecha.setFullYear(fecha.getFullYear() + anios);
    return fecha.toISOString().split('T')[0];
  }
}
