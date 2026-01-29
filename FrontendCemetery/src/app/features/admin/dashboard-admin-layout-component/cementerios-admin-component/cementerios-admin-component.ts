import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { CementerioPost } from '../../../../interfaces/cementerio/cementerioPost';
import { CementerioUpdate } from '../../../../interfaces/cementerio/cementerioUpdate';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import * as bootstrap from 'bootstrap';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cementerios-admin-component',
  imports: [FormsModule],
  templateUrl: './cementerios-admin-component.html',
  styleUrl: './cementerios-admin-component.css',
})
export class CementeriosAdminComponent {
  public cementerioService = inject(CementerioService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);

  id: number = 0;

  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;

  nuevoCementerio: CementerioPost = {
    nombre: '',
    telefono: '',
    direccion: '',
    email: '',
    ayuntamientoId: 0,
  };

  cementerioEditar: CementerioUpdate = {
    nombre: '',
    telefono: '',
    direccion: '',
    email: '',
  };

  ngOnInit(): void {
    this.cementerioService.loadAll();
  }

  totalPaginas() {
    const totalRegistros = this.cementerioService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  paginas = computed(() => {
    const total = Math.ceil(this.cementerioService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.cementerioService.cementerios().slice(inicio, fin);
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
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

  obtenerCliente(id: number) {
    this.cementerioService.find(id).subscribe((data) => {
      this.cementerioEditar = {
        nombre: data.nombre,
        telefono: data.telefono,
        direccion: data.direccion,
        email: data.email,
      };
    });
  }

  guardarCementerio() {
    console.log(this.nuevoCementerio);
    this.cementerioService.save(this.nuevoCementerio).subscribe({
      next: (res) => {
        console.log('Cliente guardado', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  actuCementerio(id: number) {
    console.log(this.nuevoCementerio);
    this.cementerioService.update(this.cementerioEditar, id).subscribe({
      next: (res) => {
        console.log('Cliente actualizado', res);
        this.cerrarModal();
        this.cementerioService.loadAll(); // Refrescar la tabla
        this.resetForm(); // Limpiar el objeto
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  resetForm() {
    this.nuevoCementerio = {
      nombre: '',
      telefono: '',
      direccion: '',
      email: '',
      ayuntamientoId: 0,
    };
    this.cementerioEditar = {
      nombre: '',
      telefono: '',
      direccion: '',
      email: '',
    }; // Bloquear de nuevo el select de ciudades
  }

  delete(idExterior: number) {
    this.cementerioService.delete(idExterior).subscribe({
      next: () => {
        console.log('Cliente eliminado');
        this.cementerioService.loadAll();
        this.cerrarModal();

        if (this.cementeriosPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }
}
