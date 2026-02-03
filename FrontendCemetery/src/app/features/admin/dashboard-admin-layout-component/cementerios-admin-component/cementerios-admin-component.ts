import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { CementerioPost } from '../../../../interfaces/cementerio/cementerioPost';
import { CementerioUpdate } from '../../../../interfaces/cementerio/cementerioUpdate';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import * as bootstrap from 'bootstrap';
import {
  FormsModule,
  ReactiveFormsModule,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { Zona } from '../../../../interfaces/zona/zona';
import { ZonaService } from '../../../../core/services/zonaService';
import { zonaPost } from '../../../../interfaces/zona/zonaPost';
import { Validadores } from '../../../../validators/validadores';

@Component({
  selector: 'app-cementerios-admin-component',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './cementerios-admin-component.html',
  styleUrl: './cementerios-admin-component.css',
})
export class CementeriosAdminComponent {
  public cementerioService = inject(CementerioService);
  public ciudadService = inject(CiudadService);
  public provinciaService = inject(ProvinciaService);
  public zonaService = inject(ZonaService);

  // ─── Reactive Forms ────────────────────────────────────────────────
  public formCrearCementerio!: FormGroup;
  public formEditarCementerio!: FormGroup;
  public formAddZona!: FormGroup;
  public formEditarZona!: FormGroup;

  id: number = 0;
  archivoParaSubir: File | null = null;

  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;
  mapaPreview = signal<string | null>(null);
  urlMapa = signal<string | null>(null);
  cementerioSeleccionadoNombre = signal<string>('');
  zonas = signal<Zona[]>([]);
  zonaId: number = 0;
  zonaSelected = signal<Zona | null>(null);
  tipos = signal<string[]>([]);

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;
  @ViewChild('modalMapa') modalMapaRef!: ElementRef;
  @ViewChild('verZonas') modalZonasRef!: ElementRef;
  @ViewChild('addZonas') modalAddZonasRef!: ElementRef;

  // ─── Objetos de datos (se mantienen para la lógica interna) ──────
  nuevoCementerio: CementerioPost = {
    nombre: '',
    telefono: '',
    direccion: '',
    email: '',
    mapa: '',
    ayuntamientoId: 0,
  };

  cementerioEditar: CementerioUpdate = {
    nombre: '',
    telefono: '',
    direccion: '',
    email: '',
    mapa: '',
  };

  nuevaZona: zonaPost = {
    nombre: '',
    tipo: '',
    puntos: '',
    filas: 0,
    columnas: 0,
    cementerioId: 0,
  };

  constructor() {
    this.inicializarFormularios();
  }

  /**
   * Crea (o resetea) todas las instancias de FormGroup con sus validadores.
   */
  private inicializarFormularios() {
    this.formCrearCementerio = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validadores.emailValidator()]),
      telefono: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validadores.telefono(),
      ]),
      direccion: new FormControl('', [Validators.required]),
      ayuntamientoId: new FormControl<number | null>(null, [
        Validators.required,
        Validadores.numeroPositivo(),
      ]),
      // mapa no necesita validación obligatoria
    });

    this.formEditarCementerio = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validadores.emailValidator()]),
      telefono: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validadores.telefono(),
      ]),
      direccion: new FormControl('', [Validators.required]),
    });

    this.formAddZona = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      tipo: new FormControl('', [Validators.required]),
      puntos: new FormControl('', [Validators.required, Validadores.coordenadas()]),
      filas: new FormControl<number | null>(null, [
        Validators.required,
        Validadores.numeroPositivo(),
      ]),
      columnas: new FormControl<number | null>(null, [
        Validators.required,
        Validadores.numeroPositivo(),
      ]),
    });

    this.formEditarZona = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      tipo: new FormControl('', [Validators.required]),
      puntos: new FormControl('', [Validators.required, Validadores.coordenadas()]),
      filas: new FormControl<number | null>(null, [
        Validators.required,
        Validadores.numeroPositivo(),
      ]),
      columnas: new FormControl<number | null>(null, [
        Validators.required,
        Validadores.numeroPositivo(),
      ]),
    });
  }

  // ─── Inicialización ─────────────────────────────────────────────────
  ngOnInit(): void {
    this.cementerioService.loadAll();
    this.zonaService.getAllTipos();
  }

  // ─── Paginación ─────────────────────────────────────────────────────
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

  // ─── Modales ────────────────────────────────────────────────────────
  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_delete(cementerio: any) {
    this.id = cementerio.id;
    this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
    this.modalBootstrap.show();
  }

  abrirModal_editar(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.obtenerCementerio(cementerio.id, () => {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  abrirModal_ver(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.obtenerCementerio(cementerio.id, () => {
      this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  abrirModal_zonas(cementerio: any) {
    this.id = cementerio.id;
    this.getAllTipo();
    this.findAllZonasByCementerio(this.id);

    if (this.modalZonasRef && this.modalZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  abrirModal_addZonas(cementerio: any) {
    this.id = cementerio.id;
    this.getAllTipo();
    this.nuevaZona.cementerioId = cementerio.id;
    this.formAddZona.reset();

    if (this.modalAddZonasRef && this.modalAddZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalAddZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForm();
      this.modalBootstrap.hide();
    }
  }

  // ─── Obtener datos ──────────────────────────────────────────────────
  /**
   * Carga un cementerio por ID y rellena formEditarCementerio con patchValue.
   */
  obtenerCementerio(id: number, callback?: () => void) {
    this.cementerioService.find(id).subscribe((data) => {
      this.cementerioEditar = {
        nombre: data.nombre,
        telefono: data.telefono,
        direccion: data.direccion,
        email: data.email,
        mapa: data.mapa,
      };

      // Rellenamos el formulario reactivo
      this.formEditarCementerio.patchValue({
        nombre: data.nombre,
        telefono: data.telefono,
        direccion: data.direccion,
        email: data.email,
      });

      if (callback) callback();
    });
  }

  // ─── CRUD Cementerio ────────────────────────────────────────────────
  guardarCementerio() {
    if (this.formCrearCementerio.invalid) {
      this.formCrearCementerio.markAllAsTouched();
      return;
    }

    // Construimos el objeto con los valores del formulario
    this.nuevoCementerio = {
      ...this.nuevoCementerio,
      ...this.formCrearCementerio.value,
    };

    if (this.archivoParaSubir) {
      this.cementerioService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.nuevoCementerio.mapa = res.nombreArchivo;
          this.procederAGuardar();
        },
        error: (err) => console.error('Error al subir imagen', err),
      });
    } else {
      this.procederAGuardar();
    }
  }

  private procederAGuardar() {
    this.cementerioService.save(this.nuevoCementerio).subscribe({
      next: (res) => {
        console.log('Cementerio guardado', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar datos', err),
    });
  }

  actuCementerio(id: number) {
    if (this.formEditarCementerio.invalid) {
      this.formEditarCementerio.markAllAsTouched();
      return;
    }

    // Actualizamos cementerioEditar con los valores frescos del formulario
    this.cementerioEditar = {
      ...this.cementerioEditar,
      ...this.formEditarCementerio.value,
    };

    if (this.archivoParaSubir) {
      this.cementerioService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.cementerioEditar.mapa = res.nombreArchivo;
          this.procederActualizar(id);
        },
        error: (err) => console.error('Error al subir nueva imagen', err),
      });
    } else {
      this.procederActualizar(id);
    }
  }

  private procederActualizar(id: number) {
    this.cementerioService.update(this.cementerioEditar, id).subscribe({
      next: (res) => {
        console.log('Cementerio actualizado', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al actualizar', err),
    });
  }

  delete(idExterior: number) {
    this.cementerioService.delete(idExterior).subscribe({
      next: () => {
        this.cementerioService.loadAll();
        this.cerrarModal();
        if (this.cementeriosPaginados.length === 0 && this.paginaActual() > 1) {
          this.paginaActual.update((p) => p - 1);
        }
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }

  // ─── CRUD Zonas ─────────────────────────────────────────────────────
  addZona() {
    if (this.formAddZona.invalid) {
      this.formAddZona.markAllAsTouched();
      return;
    }

    this.nuevaZona = {
      ...this.nuevaZona,
      ...this.formAddZona.value,
    };

    this.zonaService.save(this.nuevaZona).subscribe({
      next: (res) => {
        console.log('Zona guardada', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar datos', err),
    });
  }

  guardarZonas(idExterior: number) {
    if (this.formEditarZona.invalid) {
      this.formEditarZona.markAllAsTouched();
      return;
    }

    // Volcamos los valores del formulario al objeto zonaSelected
    const datosPara = this.formEditarZona.value;
    const zonaActualizada: Zona = {
      ...this.zonaSelected()!,
      ...datosPara,
    };

    this.zonaService.update(zonaActualizada, idExterior).subscribe({
      next: (res) => {
        console.log('Zona actualizada', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al actualizar', err),
    });
  }

  deleteZona(idExterior: number) {
    this.zonaService.delete(idExterior).subscribe({
      next: () => {
        this.cementerioService.loadAll();
        this.cerrarModal();
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }

  findAllZonasByCementerio(idExterior: number) {
    this.zonaService.findAllByCementerioId(idExterior).subscribe({
      next: (res) => {
        this.zonas.set(res);
      },
      error: (err) => console.error('Error al cargar zonas:', err),
    });
  }

  /**
   * Cuando el usuario selecciona una zona del dropdown, la cargamos
   * y rellenamos formEditarZona con sus datos.
   */
  onZonaChange(event: any) {
    const idSeleccionado = event.target.value;
    if (idSeleccionado) {
      this.zonaService.find(Number(idSeleccionado)).subscribe({
        next: (res) => {
          this.zonaSelected.set(res);
          // Rellenamos el formulario de edición de zona
          this.formEditarZona.patchValue({
            nombre: res.nombre,
            tipo: res.tipo,
            puntos: res.puntos,
            filas: res.filas,
            columnas: res.columnas,
          });
        },
        error: (err) => console.error('Error al obtener zona:', err),
      });
    }
  }

  // ─── Utilidades ─────────────────────────────────────────────────────
  resetForm() {
    this.nuevoCementerio = {
      nombre: '',
      telefono: '',
      direccion: '',
      email: '',
      mapa: '',
      ayuntamientoId: 0,
    };
    this.cementerioEditar = { nombre: '', telefono: '', direccion: '', email: '', mapa: '' };
    this.nuevaZona = {
      nombre: '',
      tipo: '',
      puntos: '',
      filas: 0,
      columnas: 0,
      cementerioId: this.id,
    };

    // Resetear todos los formularios reactivos
    this.formCrearCementerio.reset();
    this.formEditarCementerio.reset();
    this.formAddZona.reset();
    this.formEditarZona.reset();

    this.zonaSelected.set(null);
    this.archivoParaSubir = null;
    this.mapaPreview.set(null);
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      if (this.cementerioEditar.nombre !== '') {
        this.cementerioEditar.mapa = file.name;
      } else {
        this.nuevoCementerio.mapa = file.name;
      }

      const reader = new FileReader();
      reader.onload = () => {
        this.mapaPreview.set(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  }

  verMapa(cementerio: any) {
    this.cementerioSeleccionadoNombre.set(cementerio.nombre);
    const rutaBase = 'http://localhost:8080/uploads/mapas/';
    this.urlMapa.set(rutaBase + cementerio.mapa);

    if (this.modalMapaRef && this.modalMapaRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalMapaRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  getAllTipo() {
    this.tipos.set(this.zonaService.tipos());
  }
}
