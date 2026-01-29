import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { AyuntamientoService } from '../../../../core/services/ayuntamientoService';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import { ayuntamientoPost } from '../../../../interfaces/ayuntamiento/ayuntamientoPost';
import { ayuntamientoUpdate } from '../../../../interfaces/ayuntamiento/ayuntamientoUpdate';
import * as bootstrap from 'bootstrap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ayuntamientos-admin-component',
  imports: [FormsModule],
  templateUrl: './ayuntamientos-admin-component.html',
  styleUrl: './ayuntamientos-admin-component.css',
})
export class AyuntamientosAdminComponent {
  public ayuntamientoService = inject(AyuntamientoService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;

  nuevoAyuntamiento: ayuntamientoPost = {
    nif: '',
    nombre: '',
    telefono: '',
    direccion: '',
    escudo: '',
    ciudadId: 0,
    email: '',
    password: 'admin',
  };

  ayuntamientoEditar: ayuntamientoUpdate = {
    nombre: '',
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

  ngOnInit(): void {
    this.ayuntamientoService.loadAll();
    this.provinciaService.loadAll();
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  // Cálculo de páginas
  totalPaginas() {
    const totalRegistros = this.ayuntamientoService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  paginas = computed(() => {
    const total = Math.ceil(this.ayuntamientoService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  // obtener solo los cementerios de la página actual
  get ayuntamientosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.ayuntamientoService.ayuntamientos().slice(inicio, fin);
  }

  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_delete(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);

    if (this.modalDeleteRef && this.modalDeleteRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_editar(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);

    if (this.modalEditarRef && this.modalEditarRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_ver(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);
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

  guardarAyuntamiento() {
    console.log(this.nuevoAyuntamiento);
    this.ayuntamientoService.save(this.nuevoAyuntamiento).subscribe({
      next: (res) => {
        console.log('Cliente guardado', res);
        this.cerrarModal();
        this.ayuntamientoService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }
  actuAyuntamiento(id: number) {
    console.log(this.nuevoAyuntamiento);
    this.ayuntamientoService.update(this.ayuntamientoEditar, id).subscribe({
      next: (res) => {
        console.log('Cliente actualizado', res);
        this.cerrarModal();
        this.ayuntamientoService.loadAll(); // Refrescar la tabla
        this.resetForm(); // Limpiar el objeto
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  resetForm() {
    this.nuevoAyuntamiento = {
      nif: '',
      nombre: '',
      telefono: '',
      direccion: '',
      escudo: '',
      ciudadId: 0,
      email: '',
      password: 'admin',
    };
    this.ayuntamientoEditar = {
      nombre: '',
      telefono: '',
      direccion: '',
      localidad: '',
      provincia: '',
    };
    this.provinciaSeleccionadaId.set(null); // Bloquear de nuevo el select de ciudades
  }

  obtenerAyuntamiento(id: number) {
    this.ayuntamientoService.find(id).subscribe((data) => {
      this.ayuntamientoEditar = {
        nombre: data.nombre,
        telefono: data.telefono,
        direccion: data.direccion,
        localidad: data.nombreCiudad,
        provincia: data.nombreProvincia,
      };

      const prov = this.provinciaService
        .provincias()
        .find((p) => p.nombre === data.nombreProvincia);
      if (prov) {
        this.provinciaSeleccionadaId.set(prov.id);
        this.ciudadService.loadByProvinciaId(prov.id);
      }
    });
  }

  guardarId(idExterior: number) {
    this.id = idExterior;
  }

  delete(idExterior: number) {
    this.ayuntamientoService.delete(idExterior).subscribe({
      next: () => {
        console.log('Cliente eliminado');
        this.ayuntamientoService.loadAll();
        this.cerrarModal();

        if (this.ayuntamientosPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }
}
