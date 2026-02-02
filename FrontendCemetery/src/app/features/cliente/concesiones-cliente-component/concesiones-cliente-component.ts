import { Component, computed, ElementRef, inject, signal, ViewChild, OnInit } from '@angular/core';
import * as bootstrap from 'bootstrap';
import { FormsModule } from '@angular/forms';
import { Concesion } from '../../../interfaces/concesion/concesion';
import { AuthService } from '../../../core/services/authService';
import { ConcesionService } from '../../../core/services/concesionService';
import { CementerioService } from '../../../core/services/cementerioService';
import { Cementerio } from '../../../interfaces/cementerio/cementerio';
import { DifuntoPost } from '../../../interfaces/difunto/difuntoPost';
import { DifuntoService } from '../../../core/services/difuntoService';
import { ParcelaService } from '../../../core/services/parcelaService';

@Component({
  selector: 'app-concesiones-cliente-component',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './concesiones-cliente-component.html',
  styleUrl: './concesiones-cliente-component.css',
})
export class ConcesionesClienteComponent implements OnInit {
  public authService = inject(AuthService);
  public concesionService = inject(ConcesionService);
  public cementerioService = inject(CementerioService);
  public difuntoService = inject(DifuntoService);
  public parcelaService = inject(ParcelaService);

  id = signal<number>(0);
  archivoParaSubir: File | null = null;
  fotoPreview = signal<string | null>(null);

  elementosPorPagina = 5;
  paginaActual = signal(1);
  modalBootstrap: any;
  concesiones = signal<Concesion[]>([]);
  cantConcesiones = signal<number>(0);
  nombresCementerios = signal<Record<number, string>>({});
  parcelasDisponibles = signal<any[]>([]);
  parcelaSeleccionadaId = signal<number | null>(null);

  // NUEVA SEÑAL: Para gestionar la exhumación
  difuntoEnParcela = signal<any | null>(null);

  @ViewChild('modalAddDifunto') addDifunto!: ElementRef;

  difunto: DifuntoPost = {
    nombre: '',
    apellido1: '',
    apellido2: '',
    yearNacimiento: 0,
    yearDefuncion: 0,
    fechaEntierro: new Date().toLocaleDateString('en-CA'),
    mensaje: '',
    foto: '',
    parcelaId: 0,
  };

  /**
   * Inicializa el componente obteniendo el ID del cliente y cargando sus concesiones.
   */
  ngOnInit(): void {
    this.id.set(this.authService.getUsuarioId());
    this.getConcesiones();
  }

  /**
   * Abre el modal para añadir un difunto a una concesión, cargando las parcelas disponibles.
   * @param concesion La concesión seleccionada.
   */
  abrirModal(concesion: Concesion) {
    this.resetForm();
    this.parcelasDisponibles.set([]);
    this.parcelaService.findAllByConcesionId(concesion.id).subscribe({
      next: (parcelas) => this.parcelasDisponibles.set(parcelas),
      error: (err) => console.error('Error al cargar parcelas', err),
    });

    if (this.addDifunto && this.addDifunto.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.addDifunto.nativeElement);
      this.modalBootstrap.show();
    }
  }

  /**
   * Maneja la selección de una parcela y verifica si está ocupada para permitir exhumaciones.
   * @param parcela El objeto parcela seleccionado.
   */
  seleccionarParcela(parcela: any) {
    this.parcelaSeleccionadaId.set(parcela.id);
    this.difunto.parcelaId = parcela.id;
    this.difuntoEnParcela.set(null);

    // Si la parcela está ocupada, buscamos al difunto enterrado en ella
    // para habilitar la opción de exhumación.
    if (parcela.estado === 'OCUPADA') {
      this.difuntoService.loadAllByParcela(parcela.id).subscribe({
        next: (res) => {
          if (res && res.length > 0) this.difuntoEnParcela.set(res[0]);
        },
      });
    }
  }

  /**
   * Gestiona el proceso de exhumación de un familiar tras confirmación del usuario.
   */
  exhumarFamiliar() {
    const d = this.difuntoEnParcela();
    if (!d) return;

    if (
      confirm(
        `¿Desea exhumar a ${d.nombre}? Se aplicará la tasa de exhumación y la parcela quedará libre.`,
      )
    ) {
      this.difuntoService.exhumar(d.id).subscribe({
        next: () => {
          alert('Exhumación realizada con éxito.');
          this.cerrarModal();
          this.getConcesiones();
        },
        error: (err) => alert('Error: Pago rechazado o fallo en el servidor.'),
      });
    }
  }

  /**
   * Inicia el proceso de guardado de un difunto, subiendo la foto primero si existe.
   */
  guardarDifunto() {
    if (this.archivoParaSubir) {
      this.difuntoService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          this.difunto.foto = res.nombreArchivo;
          this.procederAGuardarDifunto();
        },
        error: (err) => console.error('Error al subir foto', err),
      });
    } else {
      this.procederAGuardarDifunto();
    }
  }

  /**
   * Realiza la petición HTTP para guardar los datos del difunto.
   */
  private procederAGuardarDifunto() {
    this.difuntoService.save(this.difunto).subscribe({
      next: () => {
        this.cerrarModal();
        this.getConcesiones();
      },
      error: (err) => console.error('Error al guardar difunto', err),
    });
  }

  // --- MÉTODOS DE APOYO (Paginación y reset) ---
  /**
   * Recupera todas las concesiones del cliente actual y carga los nombres de los cementerios asociados.
   */
  getConcesiones() {
    this.concesionService.findAllByCliente(this.id()).subscribe({
      next: (res) => {
        this.concesiones.set(res);
        this.cantConcesiones.set(res.length);
        res.forEach((c) => this.cargarNombreCementerio(c.id));
      },
    });
  }

  /**
   * Carga el nombre del cementerio asociado a una concesión.
   * @param concesionId ID de la concesión.
   */
  private cargarNombreCementerio(concesionId: number) {
    this.cementerioService.findByConcesion(concesionId).subscribe({
      // Actualizamos el mapa de nombres de cementerios de forma reactiva.
      next: (cem) => this.nombresCementerios.update((m) => ({ ...m, [concesionId]: cem.nombre })),
    });
  }

  /**
   * Calcula el número total de páginas para la lista de concesiones.
   */
  totalPaginas = () => Math.ceil(this.cantConcesiones() / this.elementosPorPagina) || 1;

  /**
   * Señal computada que genera un array con los números de página.
   */
  paginas = computed(() => Array.from({ length: this.totalPaginas() }, (_, i) => i + 1));

  /**
   * Obtiene las concesiones que deben mostrarse en la página actual.
   */
  get camposPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    return this.concesiones().slice(inicio, inicio + this.elementosPorPagina);
  }

  /**
   * Cambia la página actual de la visualización.
   */
  cambiarPagina(n: number) {
    if (n >= 1 && n <= this.totalPaginas()) this.paginaActual.set(n);
  }

  /**
   * Cierra el modal activo y limpia el formulario.
   */
  cerrarModal() {
    if (this.modalBootstrap) this.modalBootstrap.hide();
    this.resetForm();
  }

  /**
   * Restablece el objeto difunto y los estados de selección a sus valores iniciales.
   */
  resetForm() {
    this.parcelaSeleccionadaId.set(null);
    this.difuntoEnParcela.set(null);
    this.fotoPreview.set(null);
    this.archivoParaSubir = null;
    this.difunto = {
      nombre: '',
      apellido1: '',
      apellido2: '',
      yearNacimiento: 0,
      yearDefuncion: 0,
      fechaEntierro: new Date().toLocaleDateString('en-CA'),
      mensaje: '',
      foto: '',
      parcelaId: 0,
    };
  }

  /**
   * Maneja la selección de una foto, generando una previsualización local.
   * @param event Evento de cambio del input file.
   */
  onFotoSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      const reader = new FileReader();
      reader.onload = () => this.fotoPreview.set(reader.result as string);
      reader.readAsDataURL(file);
    }
  }
}
