import { Component, computed, inject, signal } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { AyuntamientoService } from '../../../../core/services/ayuntamientoService';
import { ClienteService } from '../../../../core/services/clienteService';
import { ServicioService } from '../../../../core/services/servicioService';
import { ZonaService } from '../../../../core/services/zonaService';

@Component({
  selector: 'app-main-panel-admin-component',
  imports: [],
  templateUrl: './main-panel-admin-component.html',
  styleUrl: './main-panel-admin-component.css',
})
export class MainPanelAdminComponent {
  public cementerioService = inject(CementerioService);
  public ayuntamientoService = inject(AyuntamientoService);
  public clienteService = inject(ClienteService);
  public servicioService = inject(ServicioService);
  public zonaService = inject(ZonaService);

  initials = signal<string>((localStorage.getItem('email') || '').substring(0, 2).toUpperCase());

  ngOnInit() {
    this.cementerioService.loadAll();
    this.ayuntamientoService.loadAll();
    this.clienteService.loadAll();
    this.servicioService.loadAll();
  }

  public actividades = computed(() => {
    // Los últimos datos de cada servicio
    const cementerios = this.cementerioService.cementerios().slice(-2);
    const clientes = this.clienteService.clientes().slice(-1);
    const zonas = this.zonaService.zonas().slice(-2);

    // juntar todos los eventos en un solo array
    return [
      ...cementerios.map((c) => ({
        title: `Nuevo cementerio: ${c.nombre}`,
        meta: 'Hace un momento · Sistema',
        type: 'new',
        icon: 'bi-bank',
      })),
      ...zonas.map((z) => ({
        title: `Nueva zona registrada: ${z.nombre}`,
        meta: `Tipo: ${z.tipo}`,
        type: 'zona',
        icon: 'bi-grid-1x2',
      })),
      ...clientes.map((cl) => ({
        title: `Nueva familia: ${cl.apellido1}`,
        meta: 'Registro Manual',
        type: 'family',
        icon: 'bi-people',
      })),
    ];
  });
}
