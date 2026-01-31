import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { ClienteService } from '../../../core/services/clienteService';
import * as bootstrap from 'bootstrap';
import { CiudadService } from '../../../core/services/ciudadService';
import { ProvinciaService } from '../../../core/services/provinciaService';
import { ClientePost } from '../../../interfaces/cliente/clientePost';
import { FormsModule } from '@angular/forms';
import { ClienteUpdate } from '../../../interfaces/cliente/clienteUpdate';

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

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;

  aytoId: number = 0;

  ngOnInit(): void {
    this.aytoId = this.obtenerIdUsuario()!;
    this.clienteService.loadAllByAyuntamiento(this.aytoId);
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

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  totalPaginas() {
    const totalRegistros = this.clienteService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  paginas = computed(() => {
    const total = Math.ceil(this.clienteService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  get clientesPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.clienteService.clientes().slice(inicio, fin);
  }

  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_delete(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);

    if (this.modalDeleteRef && this.modalDeleteRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_editar(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);

    if (this.modalEditarRef && this.modalEditarRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_ver(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id);
    if (this.modalVerRef && this.modalVerRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  cerrarModal() {
    this.modalBootstrap.hide();
  }

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

  delete(idExterior: number) {
    this.clienteService.delete(idExterior).subscribe({
      next: () => {
        console.log('Cliente eliminado');
        this.clienteService.loadAllByAyuntamiento(this.aytoId);
        this.cerrarModal();

        if (this.clientesPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }

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
}
