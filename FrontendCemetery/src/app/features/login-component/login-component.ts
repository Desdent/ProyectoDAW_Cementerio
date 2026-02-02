import { Component, inject, signal, WritableSignal } from '@angular/core';
import { LoginService } from '../../core/services/loginService';
import { User } from '../../entity/user';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Validadores } from '../../validators/validadores';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-component',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent {
  public loginService = inject(LoginService);
  private router = inject(Router);
  user!: User;
  formUser!: FormGroup;
  error = signal(false);

  /**
   * Inicializo la entidad de usuario y configuro el formulario reactivo con sus respectivas validaciones.
   */
  constructor() {
    this.user = new User();
    this.formUser = new FormGroup({
      email: new FormControl('', [Validators.required, Validadores.emailValidator]),
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
}
