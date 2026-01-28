import { Component, inject } from '@angular/core';
import { CementerioService } from '../../../core/services/cementerioService';
import { AyuntamientoService } from '../../../core/services/ayuntamientoService';
import { ClienteService } from '../../../core/services/clienteService';
import { ServicioService } from '../../../core/services/servicioService';

@Component({
  selector: 'app-dashboard-admin-component',
  imports: [],
  templateUrl: './dashboard-admin-component.html',
  styleUrl: './dashboard-admin-component.css',
})
export class DashboardAdminComponent {
  public cementerioService = inject(CementerioService);
  public ayuntamientoService = inject(AyuntamientoService);
  public clienteService = inject(ClienteService);
  public servicioService = inject(ServicioService);

  ngOnInit() {
    this.cementerioService.loadAll();
    this.ayuntamientoService.loadAll();
    this.clienteService.loadAll();
    this.servicioService.loadAll();
  }
}
