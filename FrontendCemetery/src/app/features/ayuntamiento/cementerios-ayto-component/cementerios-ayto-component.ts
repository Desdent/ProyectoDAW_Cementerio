import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CementerioService } from '../../../core/services/cementerioService';
import { ZonaService } from '../../../core/services/zonaService';
import { Zona } from '../../../interfaces/zona/zona';
import { Cementerio } from '../../../interfaces/cementerio/cementerio';
import { Cliente } from '../../../interfaces/cliente/cliente';
import { Difunto } from '../../../interfaces/difunto/difunto';
import { Servicio } from '../../../interfaces/servicio';
import { ServicioService } from '../../../core/services/servicioService';
import { ClienteService } from '../../../core/services/clienteService';
import { DifuntoService } from '../../../core/services/difuntoService';
import { ParcelaService } from '../../../core/services/parcelaService';
import * as bootstrap from 'bootstrap';
import { Validadores } from '../../../validators/validadores';
@Component({
  selector: 'app-cementerios-ayto-component',
  imports: [ReactiveFormsModule],
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
  private fb = inject(FormBuilder);

  // Formularios Reactivos
  cementerioForm: FormGroup;
  zonaForm: FormGroup;
  parcelaForm: FormGroup;

  id: number = 0; // ID del cementerio actual
  zonaEditandoId = signal<number | null>(null); // ID de la zona que se está editando

  archivoParaSubir: File | null = null;
  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;
  mapaPreview = signal<string | null>(null);
  urlMapa = signal<string | null>(null);
  cementerioSeleccionadoNombre = signal<string>('');

  // Datos
  zonas = signal<Zona[]>([]);
  zonaSelected = signal<Zona | null>(null); // Usado solo para visualización
  tipos = signal<string[]>([]);
  disponibilidadParcelas = signal<{ fila: number; columna: number }[]>([]);
  zonaParaParcelas = signal<Zona | null>(null);
  parcelasDeZona = signal<any[]>([]);
  cementerios = signal<Cementerio[]>([]);
  clientes = signal<Cliente[]>([]);
  difuntos = signal<Difunto[]>([]);
  servicios = signal<Servicio[]>([]);

  // Computados
  amountCementerios = computed(() => this.cementerios().length);
  amountClientes = computed(() => this.clientes().length);
  amountDifuntos = computed(() => this.difuntos().length);
  amountServicios = computed(() => this.servicios().length);

  aytoId: number = 0;

  // Objeto simple para la visualización (Modal Ver) que no requiere form reactivo
  cementerioVisualizar: any = { nombre: '', email: '', telefono: '', direccion: '', mapa: '' };

  @ViewChild('htmlModal') modalElement!: ElementRef;
  @ViewChild('modalVer') modalVerRef!: ElementRef;
  @ViewChild('modalEditar') modalEditarRef!: ElementRef;
  @ViewChild('modalDelete') modalDeleteRef!: ElementRef;
  @ViewChild('modalMapa') modalMapaRef!: ElementRef;
  @ViewChild('verZonas') modalZonasRef!: ElementRef;
  @ViewChild('addZonas') modalAddZonasRef!: ElementRef;
  @ViewChild('modalParcelas') modalParcelasRef!: ElementRef;

  constructor() {
    // Inicialización de formularios con validadores
    this.cementerioForm = this.fb.group({
      nombre: ['', Validators.required],
      telefono: ['', [Validators.required, Validadores.telefono()]],
      direccion: ['', Validators.required],
      email: ['', [Validators.required, Validadores.emailValidator()]],
      mapa: [''],
      ayuntamientoId: [0, Validators.required],
    });

    this.zonaForm = this.fb.group({
      nombre: ['', Validators.required],
      tipo: ['', Validators.required],
      puntos: ['', [Validators.required, Validadores.coordenadas()]],
      filas: [0, [Validators.required, Validadores.numeroPositivo()]],
      columnas: [0, [Validators.required, Validadores.numeroPositivo()]],
      cementerioId: [0, Validators.required],
    });

    this.parcelaForm = this.fb.group({
      ubicacion: ['', Validators.required], // control auxiliar "fila-columna"
      fila: [0, Validators.required],
      columna: [0, Validators.required],
      concesionId: [0],
      zonaId: [0, Validators.required],
      estado: ['LIBRE'],
    });
  }

  /**
   * Inicializa el componente obteniendo el ID del ayuntamiento desde el token
   * y cargando todos los datos relacionados.
   */
  ngOnInit() {
    this.aytoId = this.obtenerIdUsuario()!;
    this.cargarCementerios(this.aytoId);
    this.cargarClientes(this.aytoId);
    this.cargarDifuntos(this.aytoId);
    this.cargarServicios(this.aytoId);
    this.zonaService.getAllTipos();
  }

  /**
   * Calcula el número total de páginas para la paginación.
   */
  totalPaginas() {
    return Math.ceil(this.amountCementerios() / this.elementosPorPagina) || 1;
  }

  paginas = computed(() => {
    const total = this.totalPaginas();
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  get cementeriosPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.cementerios().slice(inicio, fin);
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  /**
   * Abre el modal para crear un nuevo cementerio.
   * Resetea el formulario y asigna el ID del ayuntamiento.
   */
  abrirModal() {
    this.resetForms();
    this.cementerioForm.patchValue({ ayuntamientoId: this.aytoId });

    if (this.modalElement && this.modalElement.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalElement.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal de confirmación para eliminar.
   */
  abrirModal_delete(cementerio: any) {
    this.id = cementerio.id;
    this.modalBootstrap = new bootstrap.Modal(this.modalDeleteRef.nativeElement);
    this.modalBootstrap.show();
  }

  /**
   * Abre el modal para editar un cementerio.
   * Carga los datos en el formulario reactivo.
   */
  abrirModal_editar(cementerio: any) {
    this.id = cementerio.id;
    this.resetForms();
    this.findAllZonasByCementerio(this.id);

    this.obtenerCementerio(cementerio.id, (data) => {
      // Cargamos los datos en el formulario
      this.cementerioForm.patchValue({
        nombre: data.nombre,
        telefono: data.telefono,
        direccion: data.direccion,
        email: data.email,
        mapa: data.mapa,
        ayuntamientoId: this.aytoId,
      });
      // Si hay mapa, establecer preview si es necesario o manejarlo visualmente
      if (data.mapa) {
        this.mapaPreview.set(`http://localhost:8080/uploads/mapas/${data.mapa}`);
      }

      this.modalBootstrap = new bootstrap.Modal(this.modalEditarRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  /**
   * Abre el modal para ver detalles (Solo lectura).
   */
  abrirModal_ver(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.obtenerCementerio(cementerio.id, (data) => {
      this.cementerioVisualizar = data;
      this.modalBootstrap = new bootstrap.Modal(this.modalVerRef.nativeElement);
      this.modalBootstrap.show();
    });
  }

  /**
   * Abre el modal de gestión de zonas.
   */
  abrirModal_zonas(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.getAllTipo();
    this.resetForms();

    if (this.modalZonasRef && this.modalZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Abre el modal para añadir zona nueva.
   */
  abrirModal_addZonas(cementerio: any) {
    this.id = cementerio.id;
    this.getAllTipo();
    this.resetForms();
    // Preparamos el form con el ID del cementerio
    this.zonaForm.patchValue({ cementerioId: this.id });

    if (this.modalAddZonasRef && this.modalAddZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalAddZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Cierra el modal activo y limpia formularios.
   */
  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForms();
      this.modalBootstrap.hide();
    }
  }

  /**
   * Obtiene datos del cementerio del servidor.
   */
  obtenerCementerio(id: number, callback?: (data: any) => void) {
    this.cementerioService.find(id).subscribe((data) => {
      if (callback) callback(data);
    });
  }

  /**
   * Guarda un nuevo cementerio tras validar el formulario.
   */
  guardarCementerio() {
    if (this.cementerioForm.invalid) {
      this.cementerioForm.markAllAsTouched();
      return;
    }

    if (this.archivoParaSubir) {
      this.cementerioService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.cementerioForm.patchValue({ mapa: res.nombreArchivo });
          this.procederAGuardar();
        },
        error: (err) => console.error('Error al subir imagen', err),
      });
    } else {
      this.procederAGuardar();
    }
  }

  private procederAGuardar() {
    const datos = this.cementerioForm.value;
    this.cementerioService.save(datos).subscribe({
      next: (res) => {
        console.log('Cementerio guardado', res);
        this.cargarCementerios(this.aytoId);
        this.cerrarModal();
      },
      error: (err) => console.error('Error al guardar', err),
    });
  }

  /**
   * Actualiza un cementerio existente.
   */
  actuCementerio() {
    if (this.cementerioForm.invalid) {
      this.cementerioForm.markAllAsTouched();
      return;
    }

    if (this.archivoParaSubir) {
      this.cementerioService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.cementerioForm.patchValue({ mapa: res.nombreArchivo });
          this.procederActualizar(this.id);
        },
        error: (err) => console.error('Error al subir imagen', err),
      });
    } else {
      this.procederActualizar(this.id);
    }
  }

  procederActualizar(id: number) {
    const datos = this.cementerioForm.value;
    // El servicio espera un objeto Update, el form tiene los campos compatibles
    this.cementerioService.update(datos, id).subscribe({
      next: (res) => {
        console.log('Cementerio actualizado', res);
        this.cargarCementerios(this.aytoId);
        this.cerrarModal();
      },
      error: (err) => console.error('Error al actualizar', err),
    });
  }

  /**
   * Abre modal de parcelas.
   */
  abrirModal_parcelas(cementerio: any) {
    this.id = cementerio.id;
    this.findAllZonasByCementerio(this.id);
    this.resetForms();
    this.modalBootstrap = new bootstrap.Modal(this.modalParcelasRef.nativeElement);
    this.modalBootstrap.show();
  }

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

  deleteZona(idExterior: number) {
    this.zonaService.delete(idExterior).subscribe({
      next: () => {
        this.findAllZonasByCementerio(this.id);
        this.zonaEditandoId.set(null); // Reseteamos selección
        this.zonaForm.reset();
        // No cerramos modal para permitir seguir editando otras zonas
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
   * Resetea todos los formularios y estados temporales.
   */
  resetForms() {
    this.cementerioForm.reset();
    this.zonaForm.reset();
    this.parcelaForm.reset();

    // Valores por defecto tras reset
    this.parcelaForm.patchValue({ estado: 'LIBRE', concesionId: 0 });

    this.zonaEditandoId.set(null);
    this.zonaSelected.set(null);
    this.zonaParaParcelas.set(null);

    this.archivoParaSubir = null;
    this.mapaPreview.set(null);
  }

  limpiarMapaPreview() {
    this.mapaPreview.set(null);
    this.cementerioForm.patchValue({ mapa: '' });
    this.archivoParaSubir = null;
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      // Actualizamos el control visual del input (aunque es readonly)
      this.cementerioForm.patchValue({ mapa: file.name });

      const reader = new FileReader();
      reader.onload = () => {
        this.mapaPreview.set(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  }

  /**
   * Lógica para parcelas: Seleccionar zona.
   */
  onZonaSeleccionadaParaParcelas(event: any) {
    const zonaId = Number(event.target.value);
    const zona = this.zonas().find((z) => z.id === zonaId);

    if (zona) {
      this.zonaParaParcelas.set(zona);
      this.parcelaForm.patchValue({ zonaId: zona.id });

      this.parcelaService.findByZonaId(zonaId).subscribe((parcelasExistentes) => {
        this.parcelasDeZona.set(parcelasExistentes);
        this.calcularHuecosLibres(zona, parcelasExistentes);
      });
    }
  }

  calcularHuecosLibres(zona: Zona, existentes: any[]) {
    const huecos = [];
    for (let f = 1; f <= zona.filas; f++) {
      for (let c = 1; c <= zona.columnas; c++) {
        const existe = existentes.find((p) => p.fila === f && p.columna === c);
        if (!existe) {
          huecos.push({ fila: f, columna: c });
        }
      }
    }
    this.disponibilidadParcelas.set(huecos);
  }

  onSeleccionarHueco(event: any) {
    // El valor viene como "fila-columna"
    const [f, c] = event.target.value.split('-').map(Number);
    this.parcelaForm.patchValue({
      fila: f,
      columna: c,
    });
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

  /**
   * Obtiene datos de zona para visualización (Modal Ver).
   */
  onZonaChange(event: any) {
    const idSeleccionado = event.target.value;
    if (idSeleccionado) {
      this.zonaService.find(Number(idSeleccionado)).subscribe((res) => {
        this.zonaSelected.set(res);
      });
    }
  }

  /**
   * Obtiene datos de zona para Edición (Modal Editar Zonas) y rellena el form.
   */
  onZonaChangeForEdit(event: any) {
    const idSeleccionado = Number(event.target.value);
    if (idSeleccionado) {
      this.zonaEditandoId.set(idSeleccionado);
      this.zonaService.find(idSeleccionado).subscribe((res) => {
        this.zonaForm.patchValue({
          nombre: res.nombre,
          tipo: res.tipo,
          puntos: res.puntos,
          filas: res.filas,
          columnas: res.columnas,
          cementerioId: res.cementerioId,
        });
      });
    }
  }

  addZona() {
    if (this.zonaForm.invalid) {
      this.zonaForm.markAllAsTouched();
      return;
    }

    const data = this.zonaForm.value;
    this.zonaService.save(data).subscribe({
      next: (res) => {
        console.log('Zona guardada', res);
        this.findAllZonasByCementerio(this.id);
        this.cerrarModal();
      },
      error: (err) => console.error('Error al guardar zona', err),
    });
  }

  guardarZonas() {
    if (this.zonaForm.invalid) {
      this.zonaForm.markAllAsTouched();
      return;
    }

    // Necesitamos el ID que estamos editando
    const idZona = this.zonaEditandoId();
    if (!idZona) return;

    const data = this.zonaForm.value;
    // Aseguramos que el objeto tenga el ID para el backend si es necesario, o lo pasamos en URL
    this.zonaService.update(data, idZona).subscribe({
      next: (res) => {
        console.log('Zona actualizada', res);
        this.findAllZonasByCementerio(this.id);
        this.cerrarModal();
      },
      error: (err) => console.error('Error al actualizar zona', err),
    });
  }

  getAllTipo() {
    this.tipos.set(this.zonaService.tipos());
  }

  guardarParcela() {
    if (this.parcelaForm.invalid) return;

    const data = this.parcelaForm.value;
    // Eliminamos el campo auxiliar 'ubicacion' antes de enviar si el backend es estricto,
    // o creamos un objeto limpio.
    const parcelaToSend = {
      fila: data.fila,
      columna: data.columna,
      concesionId: data.concesionId,
      zonaId: data.zonaId,
      estado: data.estado,
    };

    console.log(parcelaToSend);

    this.parcelaService.save(parcelaToSend).subscribe({
      next: () => {
        console.log('Parcela creada');
        // Refrescamos huecos simulando cambio de selección
        this.onZonaSeleccionadaParaParcelas({ target: { value: data.zonaId } });

        // Reset parcial para seguir añadiendo
        this.parcelaForm.patchValue({
          fila: 0,
          columna: 0,
          ubicacion: '',
          estado: 'LIBRE',
        });

        alert('Parcela registrada correctamente en el inventario.');
      },
      error: (err) => console.error('Error al crear parcela', err),
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
        console.error('Error al decodificar token', error);
      }
    }
    return userId;
  }

  cargarCementerios(id: number) {
    this.cementerioService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => this.cementerios.set(data),
      error: (err) => console.error(err),
    });
  }

  cargarClientes(id: number) {
    this.clienteService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => this.clientes.set(data),
      error: (err) => console.error(err),
    });
  }

  cargarDifuntos(id: number) {
    this.difuntoService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => this.difuntos.set(data),
      error: (err) => console.error(err),
    });
  }

  cargarServicios(id: number) {
    this.servicioService.loadAllByAyuntamiento(id).subscribe({
      next: (data) => this.servicios.set(data),
      error: (err) => console.error(err),
    });
  }
}
