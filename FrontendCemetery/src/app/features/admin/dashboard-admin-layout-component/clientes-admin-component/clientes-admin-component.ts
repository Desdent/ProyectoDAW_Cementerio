import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { ClienteService } from '../../../../core/services/clienteService';
import * as bootstrap from 'bootstrap';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import { ClientePost } from '../../../../interfaces/cliente/clientePost';
import { ClienteUpdate } from '../../../../interfaces/cliente/clienteUpdate';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Validadores } from '../../../../validators/validadores';

@Component({
  selector: 'app-clientes-admin-component',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './clientes-admin-component.html',
  styleUrl: './clientes-admin-component.css',
})
export class ClientesAdminComponent {
  public clienteService = inject(ClienteService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);

  // ─── Reactive Forms ──────────────────────────────────────────────
  public formCrearCliente!: FormGroup;
  public formEditarCliente!: FormGroup;

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;

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

  constructor() {
    this.inicializarFormularios();
  }

  /**
   * Crea las instancias de FormGroup con sus validadores.
   */
  private inicializarFormularios() {
    this.formCrearCliente = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required, Validadores.dni()]),
      email: new FormControl('', [Validators.required, Validadores.emailValidator()]),
      apellido1: new FormControl('', [Validators.required]),
      apellido2: new FormControl(''), // opcional
      telefono: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validadores.telefono(),
      ]),
      direccion: new FormControl('', [Validators.required]),
      provincia: new FormControl('', [Validators.required]),
      ciudadId: new FormControl('', [Validators.required]),
    });

    this.formEditarCliente = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required, Validadores.dni()]),
      apellido1: new FormControl('', [Validators.required]),
      apellido2: new FormControl(''), // opcional
      telefono: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validadores.telefono(),
      ]),
      direccion: new FormControl('', [Validators.required]),
      provincia: new FormControl('', [Validators.required]),
      localidad: new FormControl('', [Validators.required]),
    });
  }

  // ─── Inicialización ───────────────────────────────────────────────
  ngOnInit(): void {
    this.clienteService.loadAll();
    this.provinciaService.loadAll();
  }

  // ─── Paginación ───────────────────────────────────────────────────
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

  // ─── Modales ──────────────────────────────────────────────────────
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
    // obtenerCliente hace el patchValue y abre el modal en el callback
    this.obtenerCliente(cliente.id, () => {
      if (this.modalEditarRef && this.modalEditarRef.nativeElement) {
        this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
        this.modalBootstrap.show();
      }
    });
  }

  abrirModal_ver(cliente: any) {
    this.id = cliente.id;
    this.obtenerCliente(cliente.id, () => {
      if (this.modalVerRef && this.modalVerRef.nativeElement) {
        this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
        this.modalBootstrap.show();
      }
    });
  }

  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForm();
      this.modalBootstrap.hide();
    }
  }

  // ─── Provincia / Ciudad ───────────────────────────────────────────
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

  // ─── CRUD Cliente ─────────────────────────────────────────────────
  guardarCliente() {
    if (this.formCrearCliente.invalid) {
      this.formCrearCliente.markAllAsTouched();
      return;
    }

    // Construimos el objeto con los valores validados del formulario
    this.nuevoCliente = {
      ...this.nuevoCliente,
      ...this.formCrearCliente.value,
    };

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

  actuCliente(id: number) {
    if (this.formEditarCliente.invalid) {
      this.formEditarCliente.markAllAsTouched();
      return;
    }

    // Actualizamos clienteEditar con los valores frescos del formulario
    this.clienteEditar = {
      ...this.clienteEditar,
      ...this.formEditarCliente.value,
    };

    this.clienteService.update(this.clienteEditar, id).subscribe({
      next: (res) => {
        console.log('Cliente actualizado', res);
        this.cerrarModal();
        this.clienteService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Obtiene los datos de un cliente por su ID, rellena el formulario reactivo
   * y ejecuta un callback opcional al finalizar (para abrir el modal después).
   */
  obtenerCliente(id: number, callback?: () => void) {
    this.clienteService.find(id).subscribe((data) => {
      // Objeto para el modal "ver" (lectura directa con [value])
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

      // Rellenamos el formulario reactivo de editar
      this.formEditarCliente.patchValue({
        nombre: data.nombre,
        dni: data.dni,
        apellido1: data.apellido1,
        apellido2: data.apellido2,
        telefono: data.telefono,
        direccion: data.direccion,
        provincia: data.provincia,
        localidad: data.localidad,
      });

      // Cargamos ciudades de la provincia correspondiente
      const prov = this.provinciaService.provincias().find((p) => p.nombre === data.provincia);
      if (prov) {
        this.provinciaSeleccionadaId.set(prov.id);
        this.ciudadService.loadByProvinciaId(prov.id);
      }

      if (callback) callback();
    });
  }

  // ─── Utilidades ───────────────────────────────────────────────────
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

    this.formCrearCliente.reset();
    this.formEditarCliente.reset();
    this.provinciaSeleccionadaId.set(null);
  }

  guardarId(idExterior: number) {
    this.id = idExterior;
  }

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
