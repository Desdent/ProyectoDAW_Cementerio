import { Component, inject, signal } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { AyuntamientoService } from '../../../../core/services/ayuntamientoService';
import { ClienteService } from '../../../../core/services/clienteService';
import { ServicioService } from '../../../../core/services/servicioService';

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

  initials = signal<string>((localStorage.getItem('email') || '').substring(0, 2).toUpperCase());

  ngOnInit() {
    this.cementerioService.loadAll();
    this.ayuntamientoService.loadAll();
    this.clienteService.loadAll();
    this.servicioService.loadAll();
  }
}
