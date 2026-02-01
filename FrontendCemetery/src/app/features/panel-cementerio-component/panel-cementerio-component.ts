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

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.cargarDatos(Number(id));
    } else {
      this.volver();
    }

    this.cargarCarrito();
  }

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

  private cargarCarrito(): void {
    const data = localStorage.getItem('carrito');
    this.carrito.set(data ? JSON.parse(data) : []);
  }

  volver(): void {
    this.router.navigate(['/buscador']);
  }

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

  abrirModalParcelas() {
    const modal = document.getElementById('modalParcelas');
    if (modal) (modal as any).classList.add('show', 'd-block');
  }

  cerrarModalParcelas() {
    const modal = document.getElementById('modalParcelas');
    if (modal) (modal as any).classList.remove('show', 'd-block');
  }

  addAlCarrito(parcela: Parcela) {
    const carrito = JSON.parse(localStorage.getItem('carrito') || '[]');
    carrito.push(parcela);
    localStorage.setItem('carrito', JSON.stringify(carrito));
    alert(`Parcela F:${parcela.fila} C:${parcela.columna} añadida.`);
  }

  toggleCarrito(parcela: Parcela): void {
    let items = [...this.carrito()];
    const index = items.findIndex((p) => p.id === parcela.id);

    if (index === -1) {
      // Si no está, lo añadimos
      items.push(parcela);
    } else {
      // Si ya está, lo quitamos
      items.splice(index, 1);
    }

    localStorage.setItem('carrito', JSON.stringify(items));
    this.carrito.set(items);
  }

  estaEnCarrito(id: number): boolean {
    return this.carrito().some((p) => p.id === id);
  }
}
