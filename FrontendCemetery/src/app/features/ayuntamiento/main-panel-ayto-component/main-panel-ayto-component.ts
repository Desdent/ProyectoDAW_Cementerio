import { Component, computed, inject, signal } from '@angular/core';
import { CementerioService } from '../../../core/services/cementerioService';
import { AyuntamientoService } from '../../../core/services/ayuntamientoService';
import { ClienteService } from '../../../core/services/clienteService';
import { ServicioService } from '../../../core/services/servicioService';
import { Cementerio } from '../../../interfaces/cementerio/cementerio';
import { DifuntoService } from '../../../core/services/difuntoService';
import { Cliente } from '../../../interfaces/cliente/cliente';
import { Difunto } from '../../../interfaces/difunto/difunto';
import { Servicio } from '../../../interfaces/servicio';

@Component({
  selector: 'app-main-panel-ayto-component',
  imports: [],
  templateUrl: './main-panel-ayto-component.html',
  styleUrl: './main-panel-ayto-component.css',
})
export class MainPanelAytoComponent {
  private cementerioService = inject(CementerioService);
  private difuntoService = inject(DifuntoService);
  private clienteService = inject(ClienteService);
  private servicioService = inject(ServicioService);

  cementerios = signal<Cementerio[]>([]);
  amountCementerios = computed(() => this.cementerios().length);

  clientes = signal<Cliente[]>([]);
  amountClientes = computed(() => this.clientes().length);

  difuntos = signal<Difunto[]>([]);
  amountDifuntos = computed(() => this.difuntos().length);

  servicios = signal<Servicio[]>([]);
  amountServicios = computed(() => this.servicios().length);

  aytoId: number = 0;

  ngOnInit() {
    this.aytoId = this.obtenerIdUsuario()!;
    this.cargarCementerios(this.aytoId);
    this.cargarClientes(this.aytoId);
    this.cargarDifuntos(this.aytoId);
    this.cargarServicios(this.aytoId);
  }

  // Esto es full IA, no tenia ni idea de como obtener la ID del token
  obtenerIdUsuario(): number | null {
    let userId: number | null = null;
    const token = localStorage.getItem('token');

    if (token) {
      try {
        const payloadPart = token.split('.')[1];

        const decodedPayload = JSON.parse(atob(payloadPart));

        if (decodedPayload && decodedPayload.id) {
          userId = Number(decodedPayload.id);
        }
      } catch (error) {
        console.error('Error al decodificar el token:', error);
        userId = null;
      }
    }

    return userId;
  }

  cargarCementerios(id: number) {
    this.cementerioService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => {
        this.cementerios.set(data);
      },
      error: (err) => {
        console.error('Error al cargar cementerios:', err);
      },
    });
  }

  cargarClientes(id: number) {
    this.clienteService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => {
        this.clientes.set(data);
        console.log(data);
      },
      error: (err) => {
        console.error('Error al cargar clientes:', err);
      },
    });
  }

  cargarDifuntos(id: number) {
    this.difuntoService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => {
        this.difuntos.set(data);
      },
      error: (err) => {
        console.error('Error al cargar difuntos:', err);
      },
    });
  }

  cargarServicios(id: number) {
    this.servicioService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => {
        this.servicios.set(data);
      },
      error: (err) => {
        console.error('Error al cargar servicios:', err);
      },
    });
  }
}
