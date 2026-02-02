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

  /**
   * Inicializo el componente obteniendo el ID del ayuntamiento desde el token y disparando la carga de todos los datos estadísticos.
   */
  ngOnInit() {
    this.aytoId = this.obtenerIdUsuario()!;
    this.cargarCementerios(this.aytoId);
    this.cargarClientes(this.aytoId);
    this.cargarDifuntos(this.aytoId);
    this.cargarServicios(this.aytoId);
  }

  /**
   * Extraigo el identificador del usuario decodificando el payload del token JWT almacenado en el navegador.
   * @returns El ID del usuario o null si el token no existe o es inválido.
   */
  obtenerIdUsuario(): number | null {
    let userId: number | null = null;
    const token = localStorage.getItem('token');

    if (token) {
      try {
        // El token JWT consta de tres partes: Header, Payload y Signature, separadas por puntos.
        // Accedo a la segunda parte (índice 1), que contiene los datos del usuario.
        const payloadPart = token.split('.')[1];

        // Decodifico la cadena Base64 mediante atob y convierto el JSON resultante en un objeto literal.
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

  /**
   * Solicito al servicio la lista de cementerios pertenecientes al ayuntamiento y actualizo la señal correspondiente.
   * @param id Identificador del ayuntamiento.
   */
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

  /**
   * Recupero los clientes registrados en el ayuntamiento a través del servicio y los almaceno en la señal de clientes.
   * @param id Identificador del ayuntamiento.
   */
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

  /**
   * Obtengo el listado de difuntos asociados a este ayuntamiento para mostrar el recuento en el panel.
   * @param id Identificador del ayuntamiento.
   */
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

  /**
   * Cargo los servicios disponibles en el ayuntamiento invocando al servicio correspondiente.
   * @param id Identificador del ayuntamiento.
   */
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
