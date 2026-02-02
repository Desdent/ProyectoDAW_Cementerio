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

  /**
   * Inicializa el componente cargando la lista completa de ayuntamientos y provincias.
   */
  ngOnInit(): void {
    this.ayuntamientoService.loadAll();
    this.provinciaService.loadAll();
  }

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
    const totalRegistros = this.ayuntamientoService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  /**
   * Señal computada que genera un array con los números de página disponibles.
   */
  paginas = computed(() => {
    const total = Math.ceil(this.ayuntamientoService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  /**
   * Obtiene la lista de ayuntamientos que deben mostrarse en la página actual.
   * @returns Un subconjunto del array de ayuntamientos.
   */
  get ayuntamientosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.ayuntamientoService.ayuntamientos().slice(inicio, fin);
  }

  /**
   * Abre el modal para la creación de un nuevo ayuntamiento.
   */
  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal de confirmación para eliminar un ayuntamiento.
   * @param cliente El objeto ayuntamiento que se pretende eliminar.
   */
  abrirModal_delete(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);

    if (this.modalDeleteRef && this.modalDeleteRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal para editar la información de un ayuntamiento existente.
   * @param cliente El objeto ayuntamiento a editar.
   */
  abrirModal_editar(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);

    if (this.modalEditarRef && this.modalEditarRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal para visualizar los detalles de un ayuntamiento.
   * @param cliente El objeto ayuntamiento a visualizar.
   */
  abrirModal_ver(cliente: any) {
    this.id = cliente.id;
    this.obtenerAyuntamiento(cliente.id);
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
   * Envía la información del nuevo ayuntamiento al servidor para guardarlo.
   */
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

  /**
   * Actualiza la información de un ayuntamiento existente.
   * @param id El identificador único del ayuntamiento a actualizar.
   */
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

  /**
   * Restablece los objetos de datos y estados de selección a sus valores iniciales.
   */
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

  /**
   * Obtiene los datos de un ayuntamiento por su ID y prepara el objeto de edición.
   * @param id El identificador del ayuntamiento.
   */
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

  /**
   * Guarda temporalmente el ID de un ayuntamiento.
   * @param idExterior El ID a almacenar.
   */
  guardarId(idExterior: number) {
    this.id = idExterior;
  }

  /**
   * Elimina un ayuntamiento del sistema tras la confirmación.
   * @param idExterior El identificador del ayuntamiento a eliminar.
   */
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
