import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { CementerioService } from '../../../core/services/cementerioService';
import { ZonaService } from '../../../core/services/zonaService';
import { Zona } from '../../../interfaces/zona/zona';
import { CementerioPost } from '../../../interfaces/cementerio/cementerioPost';
import { CementerioUpdate } from '../../../interfaces/cementerio/cementerioUpdate';
import { zonaPost } from '../../../interfaces/zona/zonaPost';
import * as bootstrap from 'bootstrap';
import { Cementerio } from '../../../interfaces/cementerio/cementerio';
import { Cliente } from '../../../interfaces/cliente/cliente';
import { Difunto } from '../../../interfaces/difunto/difunto';
import { Servicio } from '../../../interfaces/servicio';
import { ServicioService } from '../../../core/services/servicioService';
import { ClienteService } from '../../../core/services/clienteService';
import { DifuntoService } from '../../../core/services/difuntoService';
import { FormsModule } from '@angular/forms';
import { ParcelaService } from '../../../core/services/parcelaService';
import { ParcelaPost } from '../../../interfaces/parcela/parcelaPost';

@Component({
  selector: 'app-cementerios-ayto-component',
  imports: [FormsModule],
  templateUrl: './cementerios-ayto-component.html',
  styleUrl: './cementerios-ayto-component.css',
})
export class CementeriosAytoComponent {
  public cementerioService = inject(CementerioService);
  public zonaService = inject(ZonaService);
  public servicioService = inject(ServicioService);
  public clienteService = inject(ClienteService);
  public difuntoService = inject(DifuntoService);
  public parcelaService = inject(ParcelaService);

  id: number = 0;
  archivoParaSubir: File | null = null;

  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;
  mapaPreview = signal<string | null>(null);
  urlMapa = signal<string | null>(null);
  cementerioSeleccionadoNombre = signal<string>('');
  zonas = signal<Zona[]>([]);
  zonaSelected = signal<Zona | null>(null);
  tipos = signal<string[]>([]);
  disponibilidadParcelas = signal<{ fila: number; columna: number }[]>([]);
  zonaParaParcelas = signal<Zona | null>(null);
  parcelasDeZona = signal<any[]>([]);

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
   * Inicializa el componente obteniendo el ID del ayuntamiento desde el token
   * y cargando todos los datos relacionados (cementerios, clientes, difuntos, servicios).
   */
  ngOnInit() {
    this.aytoId = this.obtenerIdUsuario()!;
    this.cargarCementerios(this.aytoId);
    this.cargarClientes(this.aytoId);
    this.cargarDifuntos(this.aytoId);
    this.cargarServicios(this.aytoId);
    this.zonaService.getAllTipos();
  }

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;
  @ViewChild('modalMapa') modalMapaRef!: ElementRef;
  @ViewChild('verZonas') modalZonasRef!: ElementRef;
  @ViewChild('addZonas') modalAddZonasRef!: ElementRef;
  @ViewChild('modalParcelas') modalParcelasRef!: ElementRef;

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

  nuevaParcela: ParcelaPost = {
    fila: 0,
    columna: 0,
    concesionId: 0,
    zonaId: 0,
    estado: 'LIBRE',
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
   * Calcula el número total de páginas para la paginación de cementerios.
   * @returns El número total de páginas.
   */
  totalPaginas() {
    return Math.ceil(this.amountCementerios() / this.elementosPorPagina) || 1;
  }

  /**
   * Señal computada que devuelve un array con los números de página.
   */
  paginas = computed(() => {
    const total = this.totalPaginas();
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  /**
   * Obtiene los cementerios que corresponden a la página actual.
   * @returns Un subconjunto del array de cementerios.
   */
  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.cementerios().slice(inicio, fin);
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
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.getAllTipo();

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
    this.getAllTipo();
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
        this.cargarCementerios(this.aytoId);
        this.cerrarModal();
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

  /**
   * Realiza la petición HTTP para actualizar los datos del cementerio.
   * @param id ID del cementerio.
   */
  procederActualizar(id: number) {
    this.cementerioService.update(this.cementerioEditar, id).subscribe({
      next: (res) => {
        console.log('Cementerio actualizado', res);
        this.cargarCementerios(this.aytoId);
        this.cerrarModal();
        this.resetForm();
      },
      error: (err) => console.error('Error al actualizar', err),
    });
  }

  /**
   * Abre el modal de gestión de parcelas para un cementerio.
   * @param cementerio El objeto cementerio.
   */
  abrirModal_parcelas(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.modalBootstrap = new bootstrap.Modal(this.modalParcelasRef.nativeElement);
    this.modalBootstrap.show();
  }

  /**
   * Elimina un cementerio del sistema.
   * @param idExterior ID del cementerio a eliminar.
   */
  delete(idExterior: number) {
    this.cementerioService.delete(idExterior).subscribe({
      next: () => {
        this.cargarCementerios(this.aytoId);
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
        this.findAllZonasByCementerio(this.id);
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
        this.zonas.set(res);
      },
      error: (err) => console.error('Error al cargar zonas:', err),
    });
  }

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
   * Maneja la selección de una zona para gestionar sus parcelas.
   * @param event Evento de cambio del selector de zonas.
   */
  onZonaSeleccionadaParaParcelas(event: any) {
    const zonaId = Number(event.target.value);
    const zona = this.zonas().find((z) => z.id === zonaId);

    if (zona) {
      this.zonaParaParcelas.set(zona);
      // Buscamos qué parcelas existen ya en esa zona para determinar los huecos libres
      // basándonos en la capacidad total de la zona (filas x columnas).
      this.parcelaService.findByZonaId(zonaId).subscribe((parcelasExistentes) => {
        this.parcelasDeZona.set(parcelasExistentes);
        this.calcularHuecosLibres(zona, parcelasExistentes);
      });
    }
  }

  /**
   * Calcula los huecos libres en una zona basándose en su cuadrícula (filas x columnas).
   * @param zona La zona a analizar.
   * @param existentes Lista de parcelas ya registradas en esa zona.
   */
  calcularHuecosLibres(zona: Zona, existentes: any[]) {
    const huecos = [];
    // Iteramos por la cuadrícula definida para la zona.
    for (let f = 1; f <= zona.filas; f++) {
      for (let c = 1; c <= zona.columnas; c++) {
        // Verificamos si la posición (fila, columna) ya está ocupada por una parcela existente.
        const existe = existentes.find((p) => p.fila === f && p.columna === c);
        if (!existe) {
          huecos.push({ fila: f, columna: c });
        }
      }
    }
    this.disponibilidadParcelas.set(huecos);
  }

  /**
   * Maneja la selección de un hueco libre para preparar la creación de una nueva parcela.
   * @param event Evento de cambio del selector de huecos.
   */
  onSeleccionarHueco(event: any) {
    // El valor viene en formato "fila-columna"
    const [f, c] = event.target.value.split('-').map(Number);

    this.nuevaParcela = {
      fila: f,
      columna: c,
      concesionId: 0,
      zonaId: this.zonaParaParcelas()?.id || 0,
      estado: 'LIBRE',
    };
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
    this.zonaService.save(this.nuevaZona).subscribe({
      next: (res) => {
        console.log('Zona guardada', res);
        this.findAllZonasByCementerio(this.id);
        this.cerrarModal();
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
   * Guarda una nueva parcela en el sistema y refresca la disponibilidad de la zona.
   */
  guardarParcela() {
    const data = this.nuevaParcela;
    console.log(data);

    this.parcelaService.save(data).subscribe({
      next: () => {
        console.log('Parcela creada:', data);
        // Refrescamos la lista de huecos libres tras guardar la nueva parcela.
        this.onZonaSeleccionadaParaParcelas({ target: { value: data.zonaId } });
        this.nuevaParcela = {
          fila: 0,
          columna: 0,
          concesionId: 0,
          zonaId: data.zonaId,
          estado: 'LIBRE',
        };
        alert('Parcela registrada correctamente en el inventario.');
      },
      error: (err) => console.error('Error al crear parcela', err),
    });
  }

  /**
   * Actualiza la información de una zona existente.
   * @param idExterior ID de la zona a actualizar.
   */
  guardarZonas(idExterior: number) {
    this.zonaService.update(this.zonaSelected()!, idExterior).subscribe({
      next: (res) => {
        console.log('Zona actualizada', res);
        this.findAllZonasByCementerio(this.id);
        this.cerrarModal();
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

  /**
   * Extrae el ID del usuario (ayuntamiento) decodificando el token JWT almacenado en localStorage.
   * @returns El ID del usuario o null si no se puede obtener.
   */
  obtenerIdUsuario(): number | null {
    let userId: number | null = null;
    const token = localStorage.getItem('token');

    if (token) {
      try {
        // El token JWT se compone de Header.Payload.Signature.
        // El payload (índice 1) contiene los datos del usuario.
        const payloadPart = token.split('.')[1];

        // atob() decodifica la cadena Base64.
        // Luego parseamos el JSON resultante para acceder a la propiedad 'id'.
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
   * Carga todos los cementerios asociados a un ayuntamiento específico.
   * @param id ID del ayuntamiento.
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
   * Carga todos los clientes asociados a un ayuntamiento específico.
   * @param id ID del ayuntamiento.
   */
  cargarClientes(id: number) {
    this.clienteService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => {
        this.clientes.set(data);
      },
      error: (err) => {
        console.error('Error al cargar clientes:', err);
      },
    });
  }

  /**
   * Carga todos los difuntos asociados a un ayuntamiento específico.
   * @param id ID del ayuntamiento.
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
   * Carga todos los servicios asociados a un ayuntamiento específico.
   * @param id ID del ayuntamiento.
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
