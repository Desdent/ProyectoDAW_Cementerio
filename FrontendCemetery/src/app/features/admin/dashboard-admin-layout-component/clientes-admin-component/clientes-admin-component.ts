import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { ClienteService } from '../../../../core/services/clienteService';
import * as bootstrap from 'bootstrap';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import { ClientePost } from '../../../../interfaces/cliente/clientePost';
import { FormsModule } from '@angular/forms';
import { ClienteUpdate } from '../../../../interfaces/cliente/clienteUpdate';

@Component({
  selector: 'app-clientes-admin-component',
  imports: [FormsModule],
  templateUrl: './clientes-admin-component.html',
  styleUrl: './clientes-admin-component.css',
})
export class ClientesAdminComponent {
  public clienteService = inject(ClienteService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;

  /**
   * Inicializa el componente cargando la lista completa de clientes y provincias.
   */
  ngOnInit(): void {
    this.clienteService.loadAll();
    this.provinciaService.loadAll();
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
   * Cambia la página actual de la visualización paginada.
   * @param nuevaPagina El número de la página a la que se desea navegar.
   */
  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  /**
   * Calcula el número total de páginas basándose en la cantidad total de registros.
   * @returns El número total de páginas (mínimo 1).
   */
  totalPaginas() {
    const totalRegistros = this.clienteService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  /**
   * Señal computada que genera un array con los números de página disponibles.
   */
  paginas = computed(() => {
    const total = Math.ceil(this.clienteService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  /**
   * Obtiene la lista de clientes que deben mostrarse en la página actual.
   * @returns Un subconjunto del array de clientes.
   */
  get clientesPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.clienteService.clientes().slice(inicio, fin);
  }

  /**
   * Abre el modal para la creación de un nuevo cliente.
   */
  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal de confirmación para eliminar un cliente.
   * @param cliente El objeto cliente que se pretende eliminar.
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
   * Abre el modal para editar la información de un cliente existente.
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
   * Abre el modal para visualizar los detalles de un cliente.
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
   * Cierra el modal de Bootstrap que esté activo.
   */
  cerrarModal() {
    this.modalBootstrap.hide();
  }

  /**
   * Maneja el cambio en el selector de provincias para cargar las ciudades asociadas.
   * @param value El valor seleccionado (puede ser ID numérico o nombre).
   */
  onProvinciaChange(value: any) {
    let id: number | undefined;

    if (isNaN(value)) {
      id = this.provinciaService.provincias().find((p) => p.nombre === value)?.id;
    } else {
      id = Number(value);
    }

    if (id) {
      this.provinciaSeleccionadaId.set(id);
      this.ciudadService.loadByProvinciaId(id);
    } else {
      this.provinciaSeleccionadaId.set(null);
    }
  }

  /**
   * Envía la información del nuevo cliente al servidor para guardarlo.
   */
  guardarCliente() {
    console.log(this.nuevoCliente);
    this.clienteService.save(this.nuevoCliente).subscribe({
      next: (res) => {
        console.log('Cliente guardado', res);
        this.cerrarModal();
        this.clienteService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Actualiza la información de un cliente existente.
   * @param id El identificador único del cliente a actualizar.
   */
  actuCliente(id: number) {
    console.log(this.nuevoCliente);
    this.clienteService.update(this.clienteEditar, id).subscribe({
      next: (res) => {
        console.log('Cliente actualizado', res);
        this.cerrarModal();
        this.clienteService.loadAll(); // Refrescar la tabla
        this.resetForm(); // Limpiar el objeto
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Restablece los objetos de datos y estados de selección a sus valores iniciales.
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
    this.provinciaSeleccionadaId.set(null); // Bloquear de nuevo el select de ciudades
  }

  /**
   * Obtiene los datos de un cliente por su ID y prepara el objeto de edición.
   * @param id El identificador del cliente.
   */
  obtenerCliente(id: number) {
    this.clienteService.find(id).subscribe((data) => {
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

      const prov = this.provinciaService.provincias().find((p) => p.nombre === data.provincia);
      if (prov) {
        this.provinciaSeleccionadaId.set(prov.id);
        this.ciudadService.loadByProvinciaId(prov.id);
      }
    });
  }

  /**
   * Guarda temporalmente el ID de un cliente.
   * @param idExterior El ID a almacenar.
   */
  guardarId(idExterior: number) {
    this.id = idExterior;
  }

  /**
   * Elimina un cliente del sistema tras la confirmación.
   * @param idExterior El identificador del cliente a eliminar.
   */
  delete(idExterior: number) {
    this.clienteService.delete(idExterior).subscribe({
      next: () => {
        console.log('Cliente eliminado');
        this.clienteService.loadAll();
        this.cerrarModal();

        if (this.clientesPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }
}
