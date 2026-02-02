import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { CementerioService } from '../../../../core/services/cementerioService';
import { CementerioPost } from '../../../../interfaces/cementerio/cementerioPost';
import { CementerioUpdate } from '../../../../interfaces/cementerio/cementerioUpdate';
import { CiudadService } from '../../../../core/services/ciudadService';
import { ProvinciaService } from '../../../../core/services/provinciaService';
import * as bootstrap from 'bootstrap';
import { FormsModule } from '@angular/forms';
import { Zona } from '../../../../interfaces/zona/zona';
import { ZonaService } from '../../../../core/services/zonaService';
import { zonaPost } from '../../../../interfaces/zona/zonaPost';

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
  public zonaService = inject(ZonaService);

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
    cementerioId: this.id,
  };

  /**
   * Inicializa el componente cargando todos los cementerios y los tipos de zonas disponibles.
   */
  ngOnInit(): void {
    this.cementerioService.loadAll();
    this.zonaService.getAllTipos();
  }

  /**
   * Calcula el número total de páginas para la paginación de cementerios.
   * @returns El número total de páginas.
   */
  totalPaginas() {
    const totalRegistros = this.cementerioService.amount();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  /**
   * Señal computada que devuelve un array con los números de página.
   */
  paginas = computed(() => {
    const total = Math.ceil(this.cementerioService.amount() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  /**
   * Obtiene los cementerios que corresponden a la página actual.
   * @returns Un subconjunto del array de cementerios.
   */
  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.cementerioService.cementerios().slice(inicio, fin);
  }

  /**
   * Cambia la página actual de la tabla.
   * @param nuevaPagina El número de página al que navegar.
   */
  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  // --- GESTIÓN DE MODALES CON CARGA ASÍNCRONA ---

  /**
   * Abre el modal para crear un nuevo cementerio.
   */
  abrirModal() {
    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal de confirmación para eliminar un cementerio.
   * @param cementerio El objeto cementerio a eliminar.
   */
  abrirModal_delete(cementerio: any) {
    this.id = cementerio.id;
    this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
    this.modalBootstrap.show();
  }

  /**
   * Abre el modal para editar un cementerio, cargando sus datos y zonas previamente.
   * @param cementerio El objeto cementerio a editar.
   */
  abrirModal_editar(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.obtenerCementerio(cementerio.id, () => {
      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  /**
   * Abre el modal para ver los detalles de un cementerio.
   * @param cementerio El objeto cementerio a visualizar.
   */
  abrirModal_ver(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.obtenerCementerio(cementerio.id, () => {
      this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  /**
   * Abre el modal de gestión de zonas de un cementerio.
   * @param cementerio El objeto cementerio cuyas zonas se quieren gestionar.
   */
  abrirModal_zonas(cementerio: any) {
    console.log(this.getAllTipo());
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    console.log(this.tipos());

    if (this.modalZonasRef && this.modalZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal para añadir una nueva zona a un cementerio específico.
   * @param cementerio El objeto cementerio al que se añadirá la zona.
   */
  abrirModal_addZonas(cementerio: any) {
    this.id = cementerio.id;
    console.log(this.id);
    console.log(this.getAllTipo());
    this.resetForm();
    this.nuevaZona.cementerioId = cementerio.id;
    if (this.modalAddZonasRef && this.modalAddZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalAddZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Cierra el modal activo y resetea los formularios.
   */
  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForm();
      this.modalBootstrap.hide();
    }
  }

  /**
   * Recupera la información de un cementerio por su ID y ejecuta un callback al finalizar.
   * @param id ID del cementerio.
   * @param callback Función opcional a ejecutar tras la carga.
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
      if (callback) callback();
    });
  }

  // --- PERSISTENCIA (SUBIDA DE IMAGEN + JSON) ---

  /**
   * Inicia el proceso de guardado de un cementerio, subiendo la imagen primero si existe.
   */
  guardarCementerio() {
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

  /**
   * Realiza la petición HTTP para guardar los datos del cementerio en la base de datos.
   */
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

  /**
   * Inicia el proceso de actualización de un cementerio, gestionando la subida de una nueva imagen si se ha seleccionado.
   * @param id ID del cementerio a actualizar.
   */
  actuCementerio(id: number) {
    if (this.archivoParaSubir) {
      // Si el usuario seleccionó una imagen nueva, la subimos primero
      this.cementerioService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.cementerioEditar.mapa = res.nombreArchivo;
          this.procederActualizar(id);
        },
        error: (err) => console.error('Error al subir nueva imagen', err),
      });
    } else {
      // Si no cambió la imagen, actualizamos directamente los textos
      this.procederActualizar(id);
    }
  }

  /**
   * Realiza la petición HTTP para actualizar los datos del cementerio.
   * @param id ID del cementerio.
   */
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

  /**
   * Elimina un cementerio del sistema.
   * @param idExterior ID del cementerio a eliminar.
   */
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

  /**
   * Elimina una zona específica.
   * @param idExterior ID de la zona a eliminar.
   */
  deleteZona(idExterior: number) {
    this.zonaService.delete(idExterior).subscribe({
      next: () => {
        this.cementerioService.loadAll();
        this.cerrarModal();
      },
      error: (err) => console.error('Error al eliminar', err),
    });
  }

  /**
   * Carga todas las zonas asociadas a un cementerio.
   * @param idExterior ID del cementerio.
   */
  findAllZonasByCementerio(idExterior: number) {
    this.zonaService.findAllByCementerioId(idExterior).subscribe({
      next: (res) => {
        console.log('Zonas recibidas del cementerio:', res);
        this.zonas.set(res); // Ahora sí, guardamos el array de zonas en la señal
      },
      error: (err) => console.error('Error al cargar zonas:', err),
    });
  }

  // --- UTILIDADES ---

  /**
   * Restablece los modelos de datos y estados de previsualización a sus valores por defecto.
   */
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
    this.id = 0;
    this.zonaSelected.set(null);
    this.archivoParaSubir = null;
    this.mapaPreview.set(null);
  }

  /**
   * Maneja la selección de un archivo de imagen, generando una previsualización local.
   * @param event Evento de cambio del input file.
   */
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      // Actualizamos el nombre en el objeto que estemos usando (nuevo o editar)
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

  /**
   * Configura la URL del mapa y abre el modal para visualizarlo.
   * @param cementerio Objeto cementerio que contiene el nombre del archivo del mapa.
   */
  verMapa(cementerio: any) {
    this.cementerioSeleccionadoNombre.set(cementerio.nombre);
    const rutaBase = 'http://localhost:8080/uploads/mapas/';
    this.urlMapa.set(rutaBase + cementerio.mapa);

    if (this.modalMapaRef && this.modalMapaRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalMapaRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Obtiene los datos detallados de una zona por su ID.
   * @param idExterior ID de la zona.
   */
  obtainDatosZona(idExterior: number) {
    this.zonaService.find(idExterior).subscribe({
      next: (res) => {
        this.zonaSelected.set(res);
        console.log(res);
      },
      error: (error) => {
        console.log('Error al obtener los datos: ', error);
      },
    });
  }

  /**
   * Envía la información para crear una nueva zona en el cementerio actual.
   */
  addZona() {
    console.log(this.nuevaZona);
    this.zonaService.save(this.nuevaZona).subscribe({
      next: (res) => {
        console.log('Zona guardada', res);
        console.log('En el cementerio con ID: ', this.id);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar datos', err),
    });
  }

  /**
   * Carga los tipos de zonas disponibles desde el servicio.
   */
  getAllTipo() {
    this.tipos.set(this.zonaService.tipos());
  }

  /**
   * Actualiza la información de una zona existente.
   * @param idExterior ID de la zona a actualizar.
   */
  guardarZonas(idExterior: number) {
    console.log(this.zonaSelected()!.id);
    this.zonaService.update(this.zonaSelected()!, idExterior).subscribe({
      next: (res) => {
        console.log('Zona actualizada', res);
        this.cerrarModal();
        this.cementerioService.loadAll();
        this.resetForm();
      },
      error: (err) => console.error('Error al actualizar', err),
    });
  }

  /**
   * Maneja el cambio de selección en el listado de zonas para cargar sus datos.
   * @param event Evento de cambio del selector.
   */
  onZonaChange(event: any) {
    const idSeleccionado = event.target.value;
    if (idSeleccionado) {
      this.obtainDatosZona(Number(idSeleccionado));
    }
  }
}
