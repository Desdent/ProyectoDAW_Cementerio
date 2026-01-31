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

  cementerios = signal<Cementerio[]>([]);
  amountCementerios = computed(() => this.cementerios().length);

  clientes = signal<Cliente[]>([]);
  amountClientes = computed(() => this.clientes().length);

  difuntos = signal<Difunto[]>([]);
  amountDifuntos = computed(() => this.difuntos().length);

  servicios = signal<Servicio[]>([]);
  amountServicios = computed(() => this.servicios().length);

  aytoId: number = 0;

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
    this.findAllZonasByCementerio(this.id);
    this.getAllTipo();

    if (this.modalZonasRef && this.modalZonasRef.nativeElement) {
      this.modalBootstrap = new bootstrap.Modal(this.modalZonasRef.nativeElement);
      this.modalBootstrap.show();
    }
  }

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

  cerrarModal() {
    if (this.modalBootstrap) {
      this.resetForm();
      this.modalBootstrap.hide();
    }
  }

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

  private procederActualizar(id: number) {
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

  getAllTipo() {
    this.tipos.set(this.zonaService.tipos());
  }

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

  onZonaChange(event: any) {
    const idSeleccionado = event.target.value;
    if (idSeleccionado) {
      this.obtainDatosZona(Number(idSeleccionado));
    }
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
        console.error('Error al decodificar el token:', error);
        userId = null;
      }
    }

    return userId;
  }

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
