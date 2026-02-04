import { Component, OnInit, inject, signal, computed, ViewChild, ElementRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Parcela } from '../../interfaces/parcela/parcela';
import { ConcesionService } from '../../core/services/concesionService';
import { concesionPost } from '../../interfaces/concesion/concesionPost';
import { PagoPost } from '../../interfaces/pago/pagoPost';
import { AuthService } from '../../core/services/authService';
import * as bootstrap from 'bootstrap';

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

  @ViewChild('modalExito') modalExito!: ElementRef;
  private modalInstance: any;

  /**
   * Inicializo el componente disparando la carga de los elementos guardados previamente.
   */
  ngOnInit(): void {
    this.cargarDesdeStorage();
  }

  /**
   * Recupero los datos del carrito desde el almacenamiento local del navegador.
   */
  private cargarDesdeStorage(): void {
    // Intento obtener la cadena de texto bajo la clave 'carrito'.
    const data = localStorage.getItem('carrito');
    if (data) {
      // Si existen datos, los transformo de JSON a un array de objetos Parcela.
      this.items.set(JSON.parse(data));
    }
  }

  /**
   * Quito una parcela específica del carrito y actualizo el almacenamiento persistente.
   * @param id Identificador de la parcela a eliminar.
   */
  eliminar(id: number): void {
    const actualizados = this.items().filter((p) => p.id !== id);
    this.items.set(actualizados);
    localStorage.setItem('carrito', JSON.stringify(actualizados));
  }

  /**
   * Gestiono el proceso de finalización de compra, generando la concesión y el pago simulado.
   */
  procesarCompra(): void {
    if (this.items().length === 0) return;

    // Obtengo la identidad del usuario y la fecha actual para los registros.
    const usuarioId = this.authService.getUsuarioId();
    const hoy = new Date().toISOString().split('T')[0];

    // Construyo el objeto de la concesión vinculando todas las parcelas del carrito.
    const concesionDto: concesionPost = {
      precio: this.total(),
      fechaInicio: hoy,
      fechaFin: this.sumarAnios(hoy, 10),
      clienteId: usuarioId,
      parcelaIds: this.items().map((p) => p.id),
      pagoId: 0,
    };

    // Genero un DTO de pago con un identificador único para simular una pasarela bancaria.
    const pagoDto: PagoPost = {
      importe: this.total(),
      fecha: hoy,
      metodo: 'VISA',
      transaccionId: crypto.randomUUID(),
      estado: 'APROBADO',
    };

    // Realizo la petición al servicio para persistir la compra en la base de datos.
    this.concesionService.comprarConcesion(concesionDto, pagoDto).subscribe({
      next: (res) => {
        // Limpio el carrito tras el éxito de la operación.
        localStorage.removeItem('carrito');
        this.modalInstance = new bootstrap.Modal(this.modalExito.nativeElement);
        this.modalInstance.show();
        setTimeout(() => {
          this.modalInstance.hide();
          this.router.navigate(['/login']);
        }, 3000);
      },
      error: (err) => {
        console.error('Error en la compra:', err);
        const msg = err.error?.message || 'Error en la transacción bancaria simulada';
        alert('No se pudo completar la compra: ' + msg);
      },
    });
  }

  /**
   * Calculo una fecha de vencimiento sumando años a una fecha dada.
   * @param fechaStr Fecha base en formato string.
   * @param anios Cantidad de años a sumar.
   */
  private sumarAnios(fechaStr: string, anios: number): string {
    const fecha = new Date(fechaStr);
    fecha.setFullYear(fecha.getFullYear() + anios);
    return fecha.toISOString().split('T')[0];
  }
}
