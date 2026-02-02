import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { ClienteService } from '../../../core/services/clienteService';
import * as bootstrap from 'bootstrap';
import { CiudadService } from '../../../core/services/ciudadService';
import { ProvinciaService } from '../../../core/services/provinciaService';
import { ClientePost } from '../../../interfaces/cliente/clientePost';
import { FormsModule } from '@angular/forms';
import { ClienteUpdate } from '../../../interfaces/cliente/clienteUpdate';
import { Cliente } from '../../../interfaces/cliente/cliente';
import { ConcesionService } from '../../../core/services/concesionService';
import { DifuntoService } from '../../../core/services/difuntoService';

@Component({
  selector: 'app-clientes-ayto-component',
  imports: [FormsModule],
  templateUrl: './clientes-ayto-component.html',
  styleUrl: './clientes-ayto-component.css',
})
export class ClientesAytoComponent {
  public clienteService = inject(ClienteService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);
  private concesionService = inject(ConcesionService);
  private difuntoService = inject(DifuntoService);

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;
  @ViewChild('modalConcesiones') modalConcesionesRef!: ElementRef;
  @ViewChild('modalDifuntos') modalDifuntosRef!: ElementRef;

  aytoId: number = 0;
  clientes = signal<Cliente[]>([]);
  concesionesCliente = signal<any[]>([]);
  difuntosCliente = signal<any[]>([]);

  /**
   * Inicializo el componente recuperando el ID del ayuntamiento desde el token y cargando los datos iniciales.
   */
  ngOnInit(): void {
    this.aytoId = this.obtenerIdUsuario()!;
    this.cargarClientes();
    this.provinciaService.loadAll();
    console.log(this.clientes());
    this.paginaActual.set(1);
  }

  nuevoCliente: ClientePost = {
    nombre: '',
    dni: '',
    apellido1: '',
    apellido2: '',
    telefono: '',
    direccion: '',
    email: '',
    password: 'admin',
    ciudadId: 0,
  };

  clienteEditar: ClienteUpdate = {
    nombre: '',
    dni: '',
    apellido1: '',
    apellido2: '',
    telefono: '',
    direccion: '',
    localidad: '',
    provincia: '',
  };

  id: number = 0;

  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;
  provinciaSeleccionadaId = signal<number | null>(null);

  /**
   * Cambio la página actual de la visualización paginada.
   * @param nuevaPagina El número de la página a la que deseo navegar.
   */
  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  /**
   * Calculo el número total de páginas basándome en la cantidad total de registros de clientes.
   * @returns El número total de páginas.
   */
  totalPaginas() {
    const totalRegistros = this.clienteService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  /**
   * Genero un array con los números de página disponibles para la navegación.
   */
  paginas = computed(() => {
    const total = Math.ceil(this.clienteService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  /**
   * Obtengo la lista de clientes que deben mostrarse en la página actual.
   * @returns Un subconjunto del array de clientes.
   */
  get clientesPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.clienteService.clientes().slice(inicio, fin);
  }

  /**
   * Abro el modal para la creación de un nuevo cliente.
   */
  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      // Utilizo la API de Bootstrap para instanciar y mostrar el modal manualmente.
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Preparo el modal de confirmación para eliminar un cliente.
   * @param cliente El objeto cliente que pretendo eliminar.
   */
  abrirModal_delete(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);

    if (this.modalDeleteRef && this.modalDeleteRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Cargo los datos del cliente y abro el modal para editar su información.
   * @param cliente El objeto cliente a editar.
   */
  abrirModal_editar(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);

    if (this.modalEditarRef && this.modalEditarRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abro el modal para visualizar los detalles completos de un cliente.
   * @param cliente El objeto cliente a visualizar.
   */
  abrirModal_ver(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);
    if (this.modalVerRef && this.modalVerRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Consulto las concesiones de un cliente específico filtradas por mi ayuntamiento y las muestro.
   * @param clienteId ID del cliente.
   */
  abrirModalConcesiones(clienteId: number) {
    // Llamamos al servicio de CONCESIONES
    this.concesionService.getConcesionesPorAyuntamiento(clienteId, this.aytoId).subscribe({
      next: (data) => {
        this.concesionesCliente.set(data);
        this.modalBootstrap = new bootstrap.Modal(this.modalConcesionesRef.nativeElement);
        this.modalBootstrap.show();
      },
      error: (err) => console.error('Error: Ruta no encontrada en ConcesionController', err),
    });
  }

  /**
   * Busco los difuntos asociados a un cliente dentro de mi ayuntamiento para mostrarlos en un modal.
   * @param clienteId ID del cliente.
   */
  abrirModalDifuntos(clienteId: number) {
    // Llamamos al servicio de DIFUNTOS
    this.difuntoService.getDifuntosPorAyuntamiento(clienteId, this.aytoId).subscribe({
      next: (data) => {
        this.difuntosCliente.set(data);
        this.modalBootstrap = new bootstrap.Modal(this.modalDifuntosRef.nativeElement);
        this.modalBootstrap.show();
      },
      error: (err) => console.error('Error: Ruta no encontrada en DifuntoController', err),
    });
  }

  /**
   * Cierro el modal de Bootstrap que esté activo en este momento.
   */
  cerrarModal() {
    this.modalBootstrap.hide();
  }

  /**
   * Gestiono el cambio en el selector de provincias para cargar las ciudades asociadas.
   * @param value El valor seleccionado (ID numérico o nombre).
   */
  onProvinciaChange(value: any) {
    let id: number | undefined;

    // Verifico si el valor recibido es el nombre de la provincia o su ID.
    if (isNaN(value)) {
      id = this.provinciaService.provincias().find((p) => p.nombre === value)?.id;
    } else {
      id = Number(value);
    }

    if (id) {
      // Si obtengo un ID válido, actualizo la señal y cargo las ciudades de esa provincia.
      this.provinciaSeleccionadaId.set(id);
      this.ciudadService.loadByProvinciaId(id);
    } else {
      this.provinciaSeleccionadaId.set(null);
    }
  }

  /**
   * Envío la información del nuevo cliente al servidor para guardarlo.
   */
  guardarCliente() {
    console.log(this.nuevoCliente);
    this.clienteService.save(this.nuevoCliente).subscribe({
      next: (res) => {
        console.log('Cliente guardado', res);
        this.cerrarModal();
        this.clienteService.loadAllByAyuntamiento(this.aytoId);
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Actualizo la información de un cliente existente en la base de datos.
   * @param id El identificador único del cliente a actualizar.
   */
  actuCliente(id: number) {
    console.log(this.nuevoCliente);
    this.clienteService.update(this.clienteEditar, id).subscribe({
      next: (res) => {
        console.log('Cliente actualizado', res);
        this.cerrarModal();
        this.clienteService.loadAllByAyuntamiento(this.aytoId);
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Solicito al servicio la carga de todos los clientes vinculados a mi ayuntamiento.
   */
  cargarClientes() {
    this.clienteService.loadAllByAyuntamiento(this.aytoId).subscribe({
      next: (data) => {
        this.clientes.set(data);

        // Sincronizo también los datos en el servicio para mantener la consistencia global.
        this.clienteService.clientes.set(data);

        console.log('Clientes cargados en componente y servicio:', data);
      },
      error: (err) => console.error('Error al cargar clientes', err),
    });
  }

  /**
   * Restablezco los objetos de datos y estados de selección a sus valores iniciales.
   */
  resetForm() {
    this.nuevoCliente = {
      nombre: '',
      dni: '',
      apellido1: '',
      apellido2: '',
      telefono: '',
      direccion: '',
      email: '',
      password: 'admin',
      ciudadId: 0,
    };
    this.clienteEditar = {
      nombre: '',
      dni: '',
      apellido1: '',
      apellido2: '',
      telefono: '',
      direccion: '',
      localidad: '',
      provincia: '',
    };
    this.provinciaSeleccionadaId.set(null);
  }

  /**
   * Recupero la información detallada de un cliente por su ID para preparar la edición.
   * @param id El identificador del cliente.
   */
  obtenerCliente(id: number) {
    this.clienteService.find(id).subscribe((data) => {
      // Mapeo los datos recibidos al objeto que utiliza el formulario de edición.
      this.clienteEditar = {
        nombre: data.nombre,
        dni: data.dni,
        apellido1: data.apellido1,
        apellido2: data.apellido2,
        telefono: data.telefono,
        direccion: data.direccion,
        localidad: data.localidad,
        provincia: data.provincia,
      };

      // Busco la provincia por nombre para activar automáticamente el selector de ciudades.
      const prov = this.provinciaService.provincias().find((p) => p.nombre === data.provincia);
      if (prov) {
        this.provinciaSeleccionadaId.set(prov.id);
        this.ciudadService.loadByProvinciaId(prov.id);
      }
    });
  }

  /**
   * Elimino a un cliente del sistema tras la confirmación del usuario.
   * @param idExterior El identificador del cliente a eliminar.
   */
  delete(idExterior: number) {
    this.clienteService.delete(idExterior).subscribe({
      next: () => {
        console.log('Cliente eliminado');
        this.clienteService.loadAllByAyuntamiento(this.aytoId);
        this.cerrarModal();

        // Si la página se queda vacía al eliminar, retrocedo una página si es posible.
        if (this.clientesPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }

  /**
   * Extraigo el ID de mi usuario decodificando el token JWT almacenado en el navegador.
   * @returns El ID del usuario o null si no puedo obtenerlo.
   */
  obtenerIdUsuario(): number | null {
    let userId: number | null = null;
    const token = localStorage.getItem('token');

    if (token) {
      try {
        // Divido el token para obtener el payload (la segunda parte).
        // Decodifico el Base64 y convierto el JSON resultante en un objeto.
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
}
