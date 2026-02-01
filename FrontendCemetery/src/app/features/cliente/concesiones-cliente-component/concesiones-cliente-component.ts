import { Component, computed, ElementRef, inject, signal, ViewChild } from '@angular/core';
import * as bootstrap from 'bootstrap';
import { FormsModule } from '@angular/forms';
import { Concesion } from '../../../interfaces/concesion/concesion';
import { AuthService } from '../../../core/services/authService';
import { ConcesionService } from '../../../core/services/concesionService';
import { CementerioService } from '../../../core/services/cementerioService';
import { Cementerio } from '../../../interfaces/cementerio/cementerio';
import { DifuntoPost } from '../../../interfaces/difunto/difuntoPost';
import { DifuntoService } from '../../../core/services/difuntoService';
import { concesionPost } from '../../../interfaces/concesion/concesionPost';
import { ParcelaService } from '../../../core/services/parcelaService';

@Component({
  selector: 'app-concesiones-cliente-component',
  imports: [FormsModule],
  templateUrl: './concesiones-cliente-component.html',
  styleUrl: './concesiones-cliente-component.css',
})
export class ConcesionesClienteComponent {
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
  cementerioConcesionSelected = signal<Cementerio | undefined>(undefined);
  nombresCementerios = signal<Record<number, string>>({});
  parcelasDisponibles = signal<any[]>([]);
  parcelaSeleccionadaId = signal<number | null>(null);

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

  ngOnInit(): void {
    this.getId();
    this.getConcesiones();
  }

  abrirModal(concesion: Concesion) {
    this.resetForm();
    this.parcelasDisponibles.set([]);

    this.parcelaService.findAllByConcesionId(concesion.id).subscribe({
      next: (parcelas) => {
        this.parcelasDisponibles.set(parcelas);
      },
      error: (err) => console.error('Error al cargar parcelas', err),
    });

    if (this.addDifunto && this.addDifunto.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.addDifunto.nativeElement);
      this.modalBootstrap.show();
    }
  }

  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForm();
      this.modalBootstrap.hide();
    }
  }

  totalPaginas() {
    const totalRegistros = this.cantConcesiones();
    return Math.ceil(totalRegistros / this.elementosPorPagina) || 1;
  }

  paginas = computed(() => {
    const total = Math.ceil(this.cantConcesiones() / this.elementosPorPagina) || 1;
    return Array.from({ length: total }, (_, i) => i + 1);
  });

  get camposPaginados() {
    const inicio = (this.paginaActual() - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    return this.concesiones().slice(inicio, fin);
  }

  cambiarPagina(nuevaPagina: number) {
    if (nuevaPagina >= 1 && nuevaPagina <= this.totalPaginas()) {
      this.paginaActual.set(nuevaPagina);
    }
  }

  getConcesiones() {
    this.concesionService.findAllByCliente(this.id()).subscribe({
      next: (res) => {
        this.concesiones.set(res);
        this.cantConcesiones.set(res.length);

        res.forEach((concesion) => {
          this.cementerioService.findByConcesion(concesion.id).subscribe({
            next: (cem) => {
              this.nombresCementerios.update((mapa) => ({
                ...mapa,
                [concesion.id]: cem.nombre,
              }));
            },
          });
        });
      },
      error: (err) => console.error('Error al obtener los datos', err),
    });
  }

  getId() {
    this.id.set(this.authService.getUsuarioId());
  }

  getCementerio(idExterior: number) {
    this.cementerioService.findByConcesion(idExterior).subscribe({
      next: (res) => {
        this.cementerioConcesionSelected.set(res);
      },
      error: (err) => console.error('Error al obtener los datos', err),
    });
  }

  guardarDifunto() {
    if (this.archivoParaSubir) {
      // Subir la foto al servidor

      this.difuntoService.subirImagen(this.archivoParaSubir).subscribe({
        next: (res) => {
          console.log('Foto subida correctamente:', res.nombreArchivo);
          // Actualizar el nombre del archivo con el que devuelve el servidor
          this.difunto.foto = res.nombreArchivo;

          // Guardar el difunto con el nombre de la foto
          this.procederAGuardarDifunto();
        },
        error: (err) => console.error('Error al subir foto', err),
      });
    } else {
      // Si no hay foto, guardar directamente
      console.log('ℹNo hay foto para subir, guardando difunto sin foto...');
      this.procederAGuardarDifunto();
    }
  }

  private procederAGuardarDifunto() {
    this.difuntoService.save(this.difunto).subscribe({
      next: (res) => {
        this.cerrarModal();
        this.resetForm();
      },
      error: (err) => console.error('Error al guardar difunto', err),
    });
  }

  resetForm() {
    this.fotoPreview.set(null);
    this.archivoParaSubir = null;
    this.parcelaSeleccionadaId.set(null);
    this.parcelasDisponibles.set([]);
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

  onFotoSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.archivoParaSubir = file;
      this.difunto.foto = file.name;

      console.log('📷 Foto seleccionada:', file.name);
      console.log('   Tamaño:', (file.size / 1024).toFixed(2), 'KB');

      // Generar vista previa
      const reader = new FileReader();
      reader.onload = () => {
        this.fotoPreview.set(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  }

  seleccionarParcela(id: number) {
    this.difunto.parcelaId = id;
    this.parcelaSeleccionadaId.set(id);
  }
}
