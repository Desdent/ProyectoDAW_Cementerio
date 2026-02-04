import { Component, ElementRef, inject, signal, ViewChild } from '@angular/core';
import { User } from '../../entity/user';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../core/services/loginService';
import { Router } from '@angular/router';
import { Validadores } from '../../validators/validadores';
import { CommonModule } from '@angular/common';
import { ProvinciaService } from '../../core/services/provinciaService';
import { CiudadService } from '../../core/services/ciudadService';
import { ClientePost } from '../../interfaces/cliente/clientePost';
import { Provincia } from '../../interfaces/provincia';
import { ClienteService } from '../../core/services/clienteService';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-registro-component',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './registro-component.html',
  styleUrl: './registro-component.css',
})
export class RegistroComponent {
  public loginService = inject(LoginService);
  public formCrearCliente!: FormGroup;
  public provinciaService = inject(ProvinciaService);
  public ciudadService = inject(CiudadService);
  public clienteService = inject(ClienteService);
  private router = inject(Router);

  @ViewChild('modalExito') modalExito!: ElementRef;
  private modalInstance: any;

  user!: User;
  formUser!: FormGroup;
  error = signal(false);
  provinciaSeleccionadaId = signal<number | null>(null);
  provincias = signal<Provincia[]>([]);

  nuevoCliente: ClientePost = {
    nombre: '',
    dni: '',
    apellido1: '',
    apellido2: '',
    telefono: '',
    direccion: '',
    email: '',
    password: 'admin',
    ciudadId: 0,
  };

  private inicializarFormularios() {
    this.formCrearCliente = new FormGroup({
      nombre: new FormControl('', [Validators.required]),
      dni: new FormControl('', [Validators.required, Validadores.dni()]),
      email: new FormControl('', [Validators.required, Validadores.emailValidator()]),
      apellido1: new FormControl('', [Validators.required]),
      apellido2: new FormControl(''), // opcional
      telefono: new FormControl('', [
        Validators.required,
        Validators.minLength(9),
        Validators.maxLength(9),
        Validadores.telefono(),
      ]),
      direccion: new FormControl('', [Validators.required]),
      provincia: new FormControl('', [Validators.required]),
      ciudadId: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit() {
    this.provinciaService.loadAll();
    this.inicializarFormularios();
  }

  /**
   * Inicializo la entidad de usuario y configuro el formulario reactivo con sus respectivas validaciones.
   */
  constructor() {
    this.user = new User();
    this.formUser = new FormGroup({
      email: new FormControl('', [Validators.required, Validadores.emailValidator()]),
      password: new FormControl('', [Validators.required, Validators.minLength(5)]),
    });
  }

  /**
   * Gestiono el proceso de autenticación validando el formulario y procesando la respuesta del servidor.
   */
  login(): void {
    // Verifico si el formulario es válido antes de proceder con la petición.
    if (this.formUser.invalid) {
      // Marco todos los campos como tocados para mostrar los errores de validación en la UI.
      this.formUser.markAllAsTouched();
      return;
    }

    // Invoco al servicio de login enviando las credenciales del formulario.
    this.loginService.login(this.formUser.value).subscribe({
      next: (response) => {
        console.log('Éxito:', response);
        // Almaceno la información de sesión en el almacenamiento local para persistir la autenticación.
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('email', response.email);

        // Redirijo al usuario a su perfil tras un inicio de sesión exitoso.
        this.router.navigate(['/perfil']);
        this.error.set(false);
      },
      error: (err) => {
        console.error('Error:', err);
        // Si el servidor devuelve un error de no autorizado (401), activo la señal de error.
        if (err.status === 401) {
          this.error.set(true);
        }
      },
    });
  }

  onProvinciaChange(value: any) {
    let id: number | undefined;

    if (isNaN(value)) {
      id = this.provinciaService.provincias().find((p) => p.nombre === value)?.id;
    } else {
      id = Number(value);
    }

    setTimeout(() => {
      console.log('Ciudades en la señal:', this.ciudadService.ciudades());
    }, 500);

    if (id) {
      this.provinciaSeleccionadaId.set(id);
      this.ciudadService.loadByProvinciaId(id);
    } else {
      this.provinciaSeleccionadaId.set(null);
    }
  }

  guardarCliente() {
    if (this.formCrearCliente.invalid) {
      this.formCrearCliente.markAllAsTouched();
      return;
    }

    // Construimos el objeto con los valores validados del formulario
    this.nuevoCliente = {
      ...this.nuevoCliente,
      ...this.formCrearCliente.value,
    };

    this.clienteService.save(this.nuevoCliente).subscribe({
      next: (res) => {
        console.log('Cliente creado', res);
        this.clienteService.loadAll();
        this.modalInstance = new bootstrap.Modal(this.modalExito.nativeElement);
        this.modalInstance.show();
        setTimeout(() => {
          this.modalInstance.hide();
          this.router.navigate(['/login']);
        }, 3000);
      },

      error: (err) => console.error('Error al guardar', err),
    });
  }
}
