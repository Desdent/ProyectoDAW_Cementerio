import { Component, computed, ElementRef, inject, signal, ViewChild, OnInit } from '@angular/core';
import * as bootstrap from 'bootstrap';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Concesion } from '../../../interfaces/concesion/concesion';
import { AuthService } from '../../../core/services/authService';
import { ConcesionService } from '../../../core/services/concesionService';
import { CementerioService } from '../../../core/services/cementerioService';
import { DifuntoPost } from '../../../interfaces/difunto/difuntoPost';
import { DifuntoService } from '../../../core/services/difuntoService';
import { ParcelaService } from '../../../core/services/parcelaService';
import { Validadores } from '../../../validators/validadores';

@Component({
  selector: 'app-concesiones-cliente-component',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './concesiones-cliente-component.html',
  styleUrl: './concesiones-cliente-component.css',
})
export class ConcesionesClienteComponent implements OnInit {
  public authService = inject(AuthService);
  public concesionService = inject(ConcesionService);
  public cementerioService = inject(CementerioService);
  public difuntoService = inject(DifuntoService);
  public parcelaService = inject(ParcelaService);

  // ─── Reactive Form ────────────────────────────────────────────────
  public formDifunto!: FormGroup;

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
  difuntoEnParcela = signal<any | null>(null);

  @ViewChild('modalAddDifunto') addDifunto!: ElementRef;

  // Objeto que se envía al backend (foto se gestiona fuera del form)
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

  constructor() {
    this.inicializarFormulario();
  }

  /**
   * Crea la instancia del FormGroup con sus validadores.
   */
  private inicializarFormulario() {
    this.formDifunto = new FormGroup(
      {
        nombre: new FormControl('', [Validators.required]),
        apellido1: new FormControl('', [Validators.required]),
        apellido2: new FormControl(''),
        yearNacimiento: new FormControl<number | null>(null, [
          Validators.required,
          Validadores.numeroPositivo(),
          Validadores.anioMinimo(1900),
        ]),
        yearDefuncion: new FormControl<number | null>(null, [
          Validators.required,
          Validadores.numeroPositivo(),
          Validadores.anioMinimo(1900),
        ]),
        fechaEntierro: new FormControl(new Date().toLocaleDateString('en-CA'), [
          Validators.required,
        ]),
        mensaje: new FormControl(''),
        // foto NO está en el FormGroup: se gestiona manualmente con onFotoSelected
      },
      {
        validators: [
          Validadores.anioDefuncionPosterior(),
          Validadores.fechaEntierroPosteriorNacimiento(),
        ],
      },
    );
  }

  // ─── Inicialización ───────────────────────────────────────────────
  ngOnInit(): void {
    this.id.set(this.authService.getUsuarioId());
    this.getConcesiones();
  }

  // ─── Modal ────────────────────────────────────────────────────────
  /**
   * Abre el modal cargando las parcelas de la concesión seleccionada.
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

  cerrarModal() {
    if (this.modalBootstrap) this.modalBootstrap.hide();
    this.resetForm();
  }

  // ─── Parcelas ─────────────────────────────────────────────────────
  /**
   * Selecciona una parcela y verifica si está ocupada para habilitar exhumación.
   */
  seleccionarParcela(parcela: any) {
    this.parcelaSeleccionadaId.set(parcela.id);
    this.difunto.parcelaId = parcela.id;
    this.difuntoEnParcela.set(null);

    if (parcela.estado === 'OCUPADA') {
      this.difuntoService.loadAllByParcela(parcela.id).subscribe({
        next: (res) => {
          if (res && res.length > 0) this.difuntoEnParcela.set(res[0]);
        },
      });
    }
  }

  /**
   * Gestiona la exhumación de un familiar tras confirmación.
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
        error: () => alert('Error: Pago rechazado o fallo en el servidor.'),
      });
    }
  }

  // ─── CRUD Difunto ─────────────────────────────────────────────────
  guardarDifunto() {
    if (this.formDifunto.invalid) {
      this.formDifunto.markAllAsTouched();
      return;
    }

    // Volcamos los valores del formulario al objeto difunto
    this.difunto = {
      ...this.difunto, // mantiene parcelaId y foto (gestionados fuera del form)
      ...this.formDifunto.value, // sobreescribe los campos del form
    };

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

  private procederAGuardarDifunto() {
    this.difuntoService.save(this.difunto).subscribe({
      next: () => {
        this.cerrarModal();
        this.getConcesiones();
      },
      error: (err) => console.error('Error al guardar difunto', err),
    });
  }

  // ─── Concesiones y Cementerios ────────────────────────────────────
  getConcesiones() {
    this.concesionService.findAllByCliente(this.id()).subscribe({
      next: (res) => {
        this.concesiones.set(res);
        this.cantConcesiones.set(res.length);
        res.forEach((c) => this.cargarNombreCementerio(c.id));
      },
    });
  }

  private cargarNombreCementerio(concesionId: number) {
    this.cementerioService.findByConcesion(concesionId).subscribe({
      next: (cem) => this.nombresCementerios.update((m) => ({ ...m, [concesionId]: cem.nombre })),
    });
  }

  // ─── Paginación ───────────────────────────────────────────────────
  totalPaginas = () => Math.ceil(this.cantConcesiones() / this.elementosPorPagina) || 1;

  paginas = computed(() => Array.from({ length: this.totalPaginas() }, (_, i) => i + 1));

  get camposPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    return this.concesiones().slice(inicio, inicio + this.elementosPorPagina);
  }

  cambiarPagina(n: number) {
    if (n >= 1 && n <= this.totalPaginas()) this.paginaActual.set(n);
  }

  // ─── Utilidades ───────────────────────────────────────────────────
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
    this.formDifunto.reset({
      fechaEntierro: new Date().toLocaleDateString('en-CA'),
    });
  }

  onFotoSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      this.difunto.foto = file.name;
      const reader = new FileReader();
      reader.onload = () => this.fotoPreview.set(reader.result as string);
      reader.readAsDataURL(file);
    }
  }
}
